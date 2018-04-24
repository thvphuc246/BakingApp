package com.example.vinhphuc.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vinhphuc.bakingapp.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private final OnItemClickListener mItemClickListener;
    private List<Recipe> recipes;

    RecipeListAdapter(List<Recipe> recipes, OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        setRecipes(recipes);
    }

    private void setRecipes(List<Recipe> recipes) {
        this.recipes = checkNotNull(recipes);
    }

    void replaceData(List<Recipe> recipes) {
        setRecipes(recipes);
        notifyDataSetChanged();
    }

    List<Recipe> getRecipes() {
        if (!recipes.isEmpty())
            return this.recipes;
        return new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (this.recipes == null) {
            return;
        }
        holder.bind(recipes.get(position), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (this.recipes == null)
            return 0;
        return this.recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_recipe_name)
        TextView nameTextView;
        @BindView(R.id.list_recipe_no_of_ingredients)
        TextView noOfIngredientTextView;
        @BindView(R.id.recipe_thumbnail)
        ImageView recipeThumbnailImageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Recipe recipe, final OnItemClickListener itemClickListener) {
            String noOfIngredients = itemView
                    .getContext()
                    .getString(R.string.recipe_list_ingredients_text, recipe.getIngredients().size());

            nameTextView.setText(recipe.getName());
            noOfIngredientTextView.setText(noOfIngredients);

            if(!recipe.getImage().isEmpty()) {
                recipeThumbnailImageView.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                        .load(recipe.getImage())
                        .centerCrop()
                        .into(recipeThumbnailImageView);
            }

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return itemClickListener.onRecipeLongClicked(itemView, ViewHolder.this.getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onRecipeClicked(recipe);
                }
            });
        }
    }

    interface OnItemClickListener {
        void onRecipeClicked(Recipe recipe);
        boolean onRecipeLongClicked(View addWidget, int adapterPosition);
    }
}
