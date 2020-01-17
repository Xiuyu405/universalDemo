package com.example.crashapp.service;

import android.app.backup.RestoreObserver;
import android.text.TextUtils;

import com.example.crashapp.service.View.BaseView;
import com.example.crashapp.service.View.MvpView;

import io.reactivex.observers.ResourceObserver;

public  class BaseObserver <T> extends ResourceObserver<T> {
    private MvpView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    protected BaseObserver(MvpView view){
        this.mView = view;
    }

    protected BaseObserver(MvpView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseObserver(MvpView view, boolean isShowError){
        this.mView = view;
        this.isShowError = isShowError;
    }

    protected BaseObserver(MvpView view, String errorMsg, boolean isShowError){
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowError = isShowError;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.onError(mErrorMsg);
        }
        if (isShowError) {
            mView.showErr();
        }
    }
}