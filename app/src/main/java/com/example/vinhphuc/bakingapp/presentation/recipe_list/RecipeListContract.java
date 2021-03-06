package com.example.vinhphuc.bakingapp.presentation.recipe_list;

import com.example.vinhphuc.bakingapp.data.MvpView;
import com.example.vinhphuc.bakingapp.data.model.Recipe;
import com.example.vinhphuc.bakingapp.data.MvpPresenter;
import com.example.vinhphuc.bakingapp.data.handling.RecipesIdlingResource;

import java.util.List;

public interface RecipeListContract {

    interface View extends MvpView {
        void showRecipes(List<Recipe> recipes);
        void showToRecipeDetails(Recipe recipe);
    }

    interface Presenter extends MvpPresenter<View> {
        void loadRecipes(RecipesIdlingResource idlingResource);
        void navigateToRecipeSteps(Recipe recipe);
    }

}
