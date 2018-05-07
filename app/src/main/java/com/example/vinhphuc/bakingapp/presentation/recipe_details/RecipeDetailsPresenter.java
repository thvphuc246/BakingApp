package com.example.vinhphuc.bakingapp.presentation.recipe_details;

import com.example.vinhphuc.bakingapp.presentation.recipe_details.RecipeDetailsContract;
import com.example.vinhphuc.bakingapp.data.model.Recipe;
import com.example.vinhphuc.bakingapp.presentation.base.BasePresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecipeDetailsPresenter
        extends BasePresenter<RecipeDetailsContract.View>
        implements RecipeDetailsContract.Presenter {
    private Recipe mCurrentRecipe;

    public RecipeDetailsPresenter(Recipe currentRecipe) {
        this.mCurrentRecipe = checkNotNull(currentRecipe);
    }

    @Override
    public void attachView(RecipeDetailsContract.View mvpView) {
        super.attachView(mvpView);
        loadRecipeName();
        loadIngredients();
        loadSteps();
    }

    @Override
    public void loadRecipeName() {
        getView().showRecipeNameInAppBar(mCurrentRecipe.getName());
    }

    @Override
    public void loadIngredients() {
        getView().showIngredients(mCurrentRecipe.getIngredients());
    }

    @Override
    public void loadSteps() {
        getView().showSteps(mCurrentRecipe.getSteps());
    }

    @Override
    public void loadStepData(int stepId) {
        getView().showStepsDetailContainer(stepId);
    }

    @Override
    public void navigateToStepDetails(int stepId) {
        checkViewAttached();
        getView().showStepDetails(stepId);
    }
}
