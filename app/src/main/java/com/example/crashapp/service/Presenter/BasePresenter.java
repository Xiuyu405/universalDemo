package com.example.crashapp.service.Presenter;

import android.content.Context;

import androidx.annotation.CheckResult;

import com.example.crashapp.service.View.BaseView;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends BaseView> {

    Context pcontext;
    private CompositeDisposable compositeDisposable;

    ;
    /**
     * 绑定的view
     */
    private WeakReference<V> mvpView;

    public BasePresenter() {
    }

//    @NonNull
//    @CheckResult
//    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
//        return lifeProvider.bindUntilEvent(event);
//    }

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    public void attachView(V mvpView, Context context) {
        this.mvpView = new WeakReference<>(mvpView);
        this.pcontext = context;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        this.mvpView = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return mvpView != null;
    }

    /**
     * 获取连接的view
     */
    public V getView() {
        if (mvpView == null) {
            return null;
        } else {
            return mvpView.get();
        }
    }

    public Context getContext() {
        return pcontext;
    }

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
