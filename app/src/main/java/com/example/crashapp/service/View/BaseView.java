package com.example.crashapp.service.View;

import android.content.Context;

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;

public interface BaseView {
    /**
     * 显示正在加载进度框
     */
    void showLoading();

    /**
     * 隐藏正在加载进度框
     */
    void hideLoading();

    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(String msg);
    /**
     * 当数据请求失败后，调用此接口提示
     * @param msg 失败原因
     */
    /**
     * 显示请求错误提示
     */
    void showErr();

//    void onSuccess(T bean);
//
//    void onError(String result);

    //    <T> LifecycleTransformer<T> bindToLifecycle();
    //用于将一个RXJava的观察者回调绑定到主导器的一个事件上
    <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event);

    Context getContext();
}
