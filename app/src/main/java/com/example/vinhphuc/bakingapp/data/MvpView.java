package com.example.vinhphuc.bakingapp.data;

import android.support.annotation.StringRes;

public interface MvpView {
    void showLoading();
    void hideLoading();
    void onError(@StringRes int resId);
    void showSnackBarMessage(String message);
    void onError(String message);
    boolean isNetworkConnected();
    void hideKeyboard();
}
