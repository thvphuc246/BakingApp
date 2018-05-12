package com.example.vinhphuc.bakingapp.presentation.recipe_step;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.data.model.Recipe;
import com.example.vinhphuc.bakingapp.data.model.Step;
import com.example.vinhphuc.bakingapp.presentation.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepFragment
        extends BaseFragment
        implements RecipeStepContract.View {

    private static String RECIPE_KEY = "RECIPE_KEY";
    @BindView(R.id.step_viewpager)
    ViewPager stepViewPager;
    @BindView(R.id.step_tab_layout)
    TabLayout recipeStepTabLayout;

    @BindString(R.string.loading_data_error)
    String errorMessage;

    @BindBool(R.bool.isTablet)
    boolean isTwoPane;

    private Recipe mCurrentRecipe;
    private int stepId;

    private RecipeStepPageAdapter viewPagerAdapter;

    public static final String STEP_KEY = "STEP_KEY";

    private RecipeStepContract.Presenter mPresenter;

    public RecipeStepFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            stepId = getArguments().getInt(STEP_KEY);
        } else {
            stepId = savedInstanceState.getInt(STEP_KEY);
        }
        mCurrentRecipe = getArguments().getParcelable(RECIPE_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STEP_KEY, stepId);
        super.onSaveInstanceState(outState);
    }

    public static RecipeStepFragment newInstance(Recipe recipe, int stepId) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(RECIPE_KEY, recipe);
        bundle.putInt(STEP_KEY, stepId);
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        setUnbinder(ButterKnife.bind(this, view));

        mPresenter = new RecipeStepPresenter(mCurrentRecipe);

        viewPagerAdapter = new RecipeStepPageAdapter(
                getActivity().getSupportFragmentManager(),
                new ArrayList<Step>(0),
                getContext()
        );

        stepViewPager.setAdapter(viewPagerAdapter);
        setUpViewPagerListener();

        recipeStepTabLayout.setupWithViewPager(stepViewPager);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            recipeStepTabLayout.setVisibility(View.GONE);
        }

        return view;
    }

    private void setUpViewPagerListener() {
        stepViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                stepId = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.attachView(this);
        stepViewPager.setCurrentItem(stepId);
    }

    @Override
    public void showStepsInViewpager(List<Step> steps) {
        viewPagerAdapter.setSteps(steps);
    }

    @Override
    public void showRecipeNameInAppBar(String recipeName) {
        getActivity().setTitle(recipeName+" Steps");
    }

    @Override
    public void moveToCurrentStepPage() {
        stepViewPager.setCurrentItem(stepId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }
}
