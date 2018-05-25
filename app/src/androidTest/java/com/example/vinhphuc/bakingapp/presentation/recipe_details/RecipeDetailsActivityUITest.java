package com.example.vinhphuc.bakingapp.presentation.recipe_details;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.presentation.recipe_list.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeDetailsActivityUITest {
    @Rule
    public ActivityTestRule<RecipeListActivity> activityActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class, false, true);

    @Test
    public void clickOnRecyclerViewItem_opensRecipeStepActivity() {

        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recipe_details_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.step_viewpager))
                .check(matches(isDisplayed()));
    }
}
