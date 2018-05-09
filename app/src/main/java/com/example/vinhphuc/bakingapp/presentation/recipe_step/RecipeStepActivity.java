package com.example.vinhphuc.bakingapp.presentation.recipe_step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.data.model.Recipe;
import com.example.vinhphuc.bakingapp.utils.ActivityUtils;

public class RecipeStepActivity extends AppCompatActivity {
    public static final String RECIPE_KEY = "RECIPE_KEY";
    public static String STEP_KEY = "STEP_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Recipe recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        int stepId = getIntent().getIntExtra(STEP_KEY, 0);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_KEY, recipe);

        RecipeStepFragment stepFragment =
                (RecipeStepFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.content_frame_recipe_details);

        if (stepFragment == null) {
            stepFragment = RecipeStepFragment.newInstance(recipe, stepId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    stepFragment,
                    R.id.content_frame_recipe_steps);
        }
    }

    public static Intent buildIntent(Context context, Recipe recipe, int stepId) {
        Intent intent = new Intent(context, RecipeStepActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        intent.putExtra(STEP_KEY, stepId);
        return intent;
    }
}
