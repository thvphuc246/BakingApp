package com.example.vinhphuc.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private Double quantity;

    @SerializedName("measure")
    @Expose
    private String measure;

    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public final static Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
//        @SuppressWarnings({"unchecked"})
        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            Ingredient instance = new Ingredient();
            instance.quantity = ((Double) parcel.readValue((Double.class.getClassLoader())));
            instance.measure = ((String) parcel.readValue((String.class.getClassLoader())));
            instance.ingredient = ((String) parcel.readValue((String.class.getClassLoader())));
            return instance;
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Ingredient() {}

    /**
     *
     * @param measure
     * @param ingredient
     * @param quantity
     */
    public Ingredient(Double quantity, String measure, String ingredient) {
        super();
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return this.measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.quantity);
        dest.writeValue(this.measure);
        dest.writeValue(this.ingredient);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }
}
