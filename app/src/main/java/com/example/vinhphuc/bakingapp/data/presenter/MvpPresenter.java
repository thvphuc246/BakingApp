package com.example.vinhphuc.bakingapp.data.presenter;

import com.example.vinhphuc.bakingapp.data.MvpView;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MvpPresenter<V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
}
