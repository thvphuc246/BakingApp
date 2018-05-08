package com.example.vinhphuc.bakingapp.presentation.recipe_step;

import com.example.vinhphuc.bakingapp.data.model.Recipe;
import com.example.vinhphuc.bakingapp.presentation.base.BasePresenter;

import static com.google.common.base.Preconditions.checkNotNull;

class RecipeStepPresenter
        extends BasePresenter<RecipeStepContract.View>
        implements RecipeStepContract.Presenter {
    private final Recipe mRecipe;

    public RecipeStepPresenter(Recipe mCurrentRecipe) {
        this.mRecipe = checkNotNull(mCurrentRecipe);
    }

    @Override
    public void attachView(RecipeStepContract.View mvpView) {
        super.attachView(mvpView);
        loadStepsToAdapter();
        loadRecipeName();
    }

    @Override
    public void loadStepsToAdapter() {
        getView().showStepsInViewpager(mRecipe.getSteps());
    }

    @Override
    public void loadRecipeName() {
        getView().showRecipeNameInAppBar(mRecipe.getName());
    }
}
