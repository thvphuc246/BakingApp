package com.example.vinhphuc.bakingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.data.MvpView;
import com.example.vinhphuc.bakingapp.utils.CommonUtils;
import com.example.vinhphuc.bakingapp.utils.NetworkUtils;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {
    private Unbinder mUnbinder;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        this.mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.cancel();
        }
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    protected void setUnbinder(Unbinder unbinder) {
        this.mUnbinder = unbinder;
    }

    @Override
    protected void onDestroy() {
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
