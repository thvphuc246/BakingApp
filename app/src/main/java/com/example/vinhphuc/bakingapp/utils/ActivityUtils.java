package com.example.vinhphuc.bakingapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.vinhphuc.bakingapp.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class ActivityUtils {
    /**
     * The {@code fragment} is added to the container view with id {@code frameId}.
     * The operation is performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity(
            @NonNull FragmentManager fragmentManager,
            @NonNull Fragment fragment,
            int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }
}
