package com.example.vinhphuc.bakingapp.data.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.data.MvpView;
import com.example.vinhphuc.bakingapp.utils.CommonUtils;
import com.example.vinhphuc.bakingapp.utils.NetworkUtils;

import butterknife.Unbinder;

public class BaseFragment extends Fragment implements MvpView {
    private Unbinder mUnbinder;
    private ProgressDialog mProgressDialog;

    @Override
    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this.getActivity());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
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
        Snackbar snackbar = Snackbar.make(getActivity()
                .findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView =
                (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this.getActivity(), android.R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showSnackBarMessage(String message) {
        showSnackBar(message);
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getActivity());
    }

    protected void setUnbinder(Unbinder unbinder) {
        mUnbinder = unbinder;
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}
