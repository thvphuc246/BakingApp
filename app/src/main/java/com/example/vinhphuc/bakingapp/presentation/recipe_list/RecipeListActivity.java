package com.example.vinhphuc.bakingapp.presentation.recipe_list;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;

import com.example.vinhphuc.bakingapp.data.provider.IngredientContract.Entry;
import com.example.vinhphuc.bakingapp.presentation.base.BaseActivity;
import com.example.vinhphuc.bakingapp.BuildConfig;
import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.presentation.recipe_details.RecipeDetailsActivity;
import com.example.vinhphuc.bakingapp.data.handling.Injection;
import com.example.vinhphuc.bakingapp.data.model.Ingredient;
import com.example.vinhphuc.bakingapp.data.model.Recipe;
import com.example.vinhphuc.bakingapp.data.handling.RecipesIdlingResource;
import com.example.vinhphuc.bakingapp.utils.CommonUtils;
import com.example.vinhphuc.bakingapp.widget.IngredientsWidgetProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecipeListActivity
        extends BaseActivity
        implements RecipeListContract.View {

    public static final String RECIPE_KEY = "RECIPE_KEY";

    @Nullable
    private RecipesIdlingResource idlingResource;

    @BindView(R.id.recipe_list)
    RecyclerView mRecipeCards;
    @BindInt(R.integer.grid_column_count)
    int columnCount;

    private SharedPreferences sharedPreferences;
    private RecipeListAdapter mRecipeListAdapter;
    private RecipeListContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        setUnbinder(ButterKnife.bind(this));

        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        mPresenter = new RecipeListPresenter(
                Injection.provideRecipeRepo(),
                Injection.provideSchedulerProvider()
        );

        mRecipeListAdapter = new RecipeListAdapter(new ArrayList<Recipe>(0),
                new RecipeListAdapter.OnItemClickListener() {
                    @Override
                    public void onRecipeClicked(Recipe recipe) {
                        mPresenter.navigateToRecipeSteps(recipe);
                    }

                    @Override
                    public boolean onRecipeLongClicked(View view, final int adapterPosition) {
                        final PopupMenu popupMenu = new PopupMenu(RecipeListActivity.this, view);
                        popupMenu.inflate(R.menu.widget);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.menu_recipe_ingredient:
                                        Timber.d("Add Widget at " + adapterPosition);
                                        showIngredientsInWidget(adapterPosition);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        popupMenu.show();
                        return true;
                    }
                });

        setUpRecipeRecycler();

        mPresenter.loadRecipes(idlingResource);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null)
            idlingResource = new RecipesIdlingResource();
        return idlingResource;
    }

    @Override
    protected void onStart() {
        super.onStart();
        showSnackBarMessage(getString(R.string.add_widget_message));
    }

    private void setUpRecipeRecycler() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, columnCount);
        mRecipeCards.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecipeCards.getContext(), layoutManager.getOrientation());

        mRecipeCards.addItemDecoration(dividerItemDecoration);

        mRecipeCards.setHasFixedSize(true);
        mRecipeCards.setItemAnimator(new DefaultItemAnimator());
        mRecipeCards.setAdapter(mRecipeListAdapter);
    }

    @Override
    public void showToRecipeDetails(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        startActivity(intent);
    }

    @Override
    public void showSnackBarMessage(String message) {
        onError(message);
    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        mRecipeListAdapter.replaceData(recipes);
        mRecipeCards.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void saveIngredients(List<Ingredient> ingredients) {
        String noOfIngredient = ingredients.size() + " ingredients";

        StringBuilder sb = new StringBuilder();
        sb.append(noOfIngredient);

        for (Ingredient ingredient : ingredients) {
            String name = ingredient.getIngredient();
            double quantity = ingredient.getQuantity();
            String measure = ingredient.getMeasure();

            sb.append("\n");
            sb.append(CommonUtils.formatIngredient(this, name, quantity, measure));
        }

        String formattedIngredients = String
                .valueOf(CommonUtils.setTextWithSpan(
                sb.toString(),
                noOfIngredient,
                new StyleSpan(Typeface.BOLD)));

        sharedPreferences.edit()
                .putString(getString(R.string.pref_recipe_ingredients), formattedIngredients)
                .apply();
    }

    private void showIngredientsInWidget(int adapterPosition) {
        Recipe recipe = mRecipeListAdapter.getRecipes().get(adapterPosition);
        List<Ingredient> ingredients = recipe.getIngredients();
        String recipeName = recipe.getName();

        sharedPreferences
                .edit()
                .putString(getString(R.string.pref_recipe_name), recipeName)
                .apply();
        saveIngredients(ingredients);

        Uri uri = Entry.CONTENT_URI;
        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);

        if (cursor != null) {
            //First delete the existing data
            while (cursor.moveToNext()) {
                getContentResolver()
                        .delete(
                                uri,
                                Entry._ID + "=?",
                                new String[] {
                                    cursor.getString(cursor.getColumnIndex(Entry._ID))
                                });
            }

            cursor.close();

            //Do Bulk Insert
            ContentValues[] values = new ContentValues[ingredients.size()];

            for (int i = 0; i < ingredients.size(); i++) {
                values[i] = new ContentValues();
                values[i].put(Entry.COLUMN_NAME_QUANTITY, ingredients.get(i).getQuantity());
                values[i].put(Entry.COLUMN_NAME_MEASURE, ingredients.get(i).getMeasure());
                values[i].put(Entry.COLUMN_NAME_INGREDIENTS, ingredients.get(i).getIngredient());
            }
            getContentResolver().bulkInsert(uri, values);
        }

        //Update recipe's name and ingredients
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(),
                        IngredientsWidgetProvider.class)
                );
        IngredientsWidgetProvider ingredientsWidget = new IngredientsWidgetProvider();
        ingredientsWidget.onUpdate(this, AppWidgetManager.getInstance(this), ids);
    }
}