package com.example.vinhphuc.bakingapp.data.model;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {
    @GET("android-baking-app-json")
    Observable<List<Recipe>> fetchRecipes();
}
