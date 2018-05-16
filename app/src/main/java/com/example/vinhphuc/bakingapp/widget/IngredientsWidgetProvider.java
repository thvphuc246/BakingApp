package com.example.vinhphuc.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.vinhphuc.bakingapp.BuildConfig;
import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.presentation.recipe_details.RecipeDetailsActivity;

public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);

        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.setAction("LAUNCH_ACTIVITY");
        PendingIntent pendingIntent =
                PendingIntent
                        .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        context.startActivity(intent);

        SharedPreferences sharedPreferences = context
                .getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        String recipeName = sharedPreferences
                .getString(context.getString(R.string.pref_recipe_name), "");
        String formattedIngredients = sharedPreferences
                .getString(context.getString(R.string.pref_recipe_ingredients), "");

        views.setTextViewText(R.id.widget_recipe_name, recipeName);
        views.setTextViewText(R.id.widget_recipe_ingredients, formattedIngredients);

        //Initiate the click event for the widget to go to the appropriate recipe activity
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        pushWidgetUpdate(context, views);

        //Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews views) {
        ComponentName mWidget = new ComponentName(context, IngredientsWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(mWidget, views);
    }

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        //There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context,appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
