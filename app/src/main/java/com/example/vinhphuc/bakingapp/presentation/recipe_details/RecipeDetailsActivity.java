package com.example.vinhphuc.bakingapp.presentation.recipe_details;

import android.os.Bundle;

import com.example.vinhphuc.bakingapp.presentation.base.BaseActivity;
import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.data.model.Recipe;
import com.example.vinhphuc.bakingapp.utils.ActivityUtils;

public class RecipeDetailsActivity extends BaseActivity {
    public static final String RECIPE_KEY = "RECIPE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Recipe recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_KEY, recipe);

        RecipeDetailsContract.Presenter mPresenter = new RecipeDetailsPresenter(recipe);

        RecipeDetailsFragment fragment =
                (RecipeDetailsFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.content_frame_recipe_details);

        if (fragment == null) {
            fragment = RecipeDetailsFragment.newInstance(bundle);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    fragment,
                    R.id.content_frame_recipe_details);
            fragment.setPresenter(mPresenter);
        }
    }

    @Override
    public void showSnackBarMessage(String message) {
        onError(message);
    }
}
