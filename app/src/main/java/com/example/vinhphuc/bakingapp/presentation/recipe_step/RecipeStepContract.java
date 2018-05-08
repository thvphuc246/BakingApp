package com.example.vinhphuc.bakingapp.presentation.recipe_step;

import com.example.vinhphuc.bakingapp.data.MvpPresenter;
import com.example.vinhphuc.bakingapp.data.MvpView;
import com.example.vinhphuc.bakingapp.data.model.Step;

import java.util.List;

interface RecipeStepContract {

    interface View extends MvpView {
        void showStepsInViewpager(List<Step> steps);
        void showRecipeNameInAppBar(String recipeName);
        void moveToCurrentStepPage();
    }

    interface Presenter extends MvpPresenter<View> {
        void loadStepsToAdapter();
        void loadRecipeName();
    }

}
