package com.zachtib.simplechat.presenter;

import com.zachtib.simplechat.view.BaseView;

public interface BasePresenter<T extends BaseView> {
    void onCreate();
    void onStart();
    void onStop();
    void onPause();
    void attachView(T view);
}
