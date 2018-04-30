package com.example.vinhphuc.bakingapp.data.contract;

import com.example.vinhphuc.bakingapp.data.MvpView;
import com.example.vinhphuc.bakingapp.data.model.Ingredient;
import com.example.vinhphuc.bakingapp.data.model.Step;
import com.example.vinhphuc.bakingapp.data.presenter.MvpPresenter;

import java.util.List;

public interface RecipeDetailsContract {
    interface View extends MvpView {
        void showSteps(List<Step> steps);
        void showIngredients(List<Ingredient> ingredients);
        void showRecipeNameInAppBar(String recipeName);
        void showStepDetails(int stepId);
        void showStepsDetailContainer(int stepId);
        void setPresenter(Presenter presenter);
    }

    interface Presenter extends MvpPresenter<RecipeDetailsContract.View> {
        void loadRecipeName();
        void loadIngredients();
        void loadSteps();
        void loadStepData(int stepId);
        void navigateToStepDetails(int stepId);
    }
}
