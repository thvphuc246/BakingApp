package com.example.vinhphuc.bakingapp.data;

import com.example.vinhphuc.bakingapp.data.model.Recipe;

import java.util.List;

import rx.Observable;

public interface RecipeRepo {
    Observable<List<Recipe>> getRecipes();
}
