package com.example.vinhphuc.bakingapp.data.handling;

import com.example.vinhphuc.bakingapp.data.RecipeRepo;
import com.example.vinhphuc.bakingapp.data.model.ApiService;
import com.example.vinhphuc.bakingapp.data.model.Recipe;

import java.util.List;

import rx.Observable;
import rx.functions.Func0;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecipeRepoImplementor implements RecipeRepo {
    private final ApiService apiService;

    public RecipeRepoImplementor(ApiService apiService) {
        this.apiService = checkNotNull(apiService, "Baking App Api Service cannot be null");
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return Observable.defer(new Func0<Observable<List<Recipe>>>() {
            @Override
            public Observable<List<Recipe>> call() {
                return apiService.fetchRecipes();
            }
        });
    }
}
