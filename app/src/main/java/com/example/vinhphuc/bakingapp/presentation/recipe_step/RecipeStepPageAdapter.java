package com.example.vinhphuc.bakingapp.presentation.recipe_step;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.data.model.Step;
import com.example.vinhphuc.bakingapp.presentation.recipe_details.SingleStepFragment;

import java.util.List;
import java.util.Locale;

import timber.log.Timber;

class RecipeStepPageAdapter extends FragmentPagerAdapter {
    private List<Step> steps;
    private final String tabTitle;

    RecipeStepPageAdapter(FragmentManager fm, List<Step> steps, Context context) {
        super(fm);
        setSteps(steps);
        tabTitle = context.getResources().getString(R.string.recipe_step_tab_label);
    }

    public void setSteps(@NonNull List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        SingleStepFragment fragment = SingleStepFragment.newInstance(steps.get(position));
        Timber.d("RIP ICHIGO, position: " + position);
        Timber.d("RIP CHELSEA, playerPosition: " + fragment.getPlayerPosition());
        return fragment;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(Locale.US, tabTitle, position);
    }
}
