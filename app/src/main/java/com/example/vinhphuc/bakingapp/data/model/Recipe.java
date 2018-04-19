package com.example.vinhphuc.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = new ArrayList<>();

    @SerializedName("steps")
    @Expose
    private List<Step> steps = new ArrayList<>();

    @SerializedName("servings")
    @Expose
    private Integer servings;

    @SerializedName("image")
    @Expose
    private String image;

    public final static Creator<Recipe> CREATOR = new Creator<Recipe>() {
//        @SuppressWarnings({"unchecked"})
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            Recipe instance = new Recipe();
            instance.id = ((Integer) parcel.readValue((Integer.class.getClassLoader())));
            instance.name = ((String) parcel.readValue((String.class.getClassLoader())));
            parcel.readList(instance.ingredients, (Ingredient.class.getClassLoader()));
            parcel.readList(instance.steps, (Step.class.getClassLoader()));
            instance.servings = ((Integer) parcel.readValue((Integer.class.getClassLoader())));
            instance.image = ((String) parcel.readValue((String.class.getClassLoader())));
            return instance;
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public Recipe() {}

    /**
     *
     * @param ingredients
     * @param id
     * @param servings
     * @param name
     * @param image
     * @param steps
     */
    public Recipe(
            Integer id,
            String name,
            List<Ingredient> ingredients,
            List<Step> steps,
            Integer servings,
            String image) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return this.steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return this.servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeValue(this.servings);
        dest.writeValue(this.image);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                '}';
    }
}
