package com.example.crashapp.service.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.crashapp.service.BaseObserver;
import com.example.crashapp.service.View.BaseView;
import com.example.crashapp.service.View.MvpView;
import com.example.crashapp.service.entity.BookBean;
import com.example.crashapp.service.Manager.DataManager;
import com.example.crashapp.service.entity.ReadBean;
import com.example.crashapp.service.entity.listBean;
import com.example.crashapp.ui.activity.BaseActivity;
import com.example.crashapp.util.RxBus;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


public class MvpPresenter extends BasePresenter<MvpView> {

    private DataManager manager;
    private static CompositeDisposable mCompositeSubscription;
    private ReadBean readBean;
    public MvpPresenter() {
        Log.e("tag", 222222 + "");
        manager = new DataManager(getContext());
        mCompositeSubscription = new CompositeDisposable();
    }

    public void getSearchBooks(String name, String tag, int start, int count) {
        addSubscribe(manager.createUser(name, tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<BookBean>( getView(),"error1"){
                    @Override
                    public void onNext(BookBean bookBean) {
                        super.onNext(bookBean);
                    }
                }));

    }

    public void getSearchList(int num) {
        addSubscribe(manager.getFeedArticleList(num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(RxBus.getDefault().transform(getView()))
                .compose(this.getView().<listBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeWith(new BaseObserver<listBean>(getView(),"error1"){
                    @Override
                    public void onNext(listBean bookBean) {
                        super.onNext(bookBean);
                       getView().onSuccess(bookBean);
                    }
                }));

    }

    public void getReadBooks(String name, String tag, int start, int count) {
        addSubscribe(manager.getSreaadBook(name, tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ReadBean>(getView(),"error"){
                    @Override
                    public void onNext(ReadBean readBean) {
                        if (isViewAttached() && readBean != null) {
                            getView().hideLoading();
                            getView().onSuccess(readBean);
                        }
                        super.onNext(readBean);
                    }
                }));

    }


    public static void onStop() {
        if (!mCompositeSubscription.isDisposed()&&mCompositeSubscription !=null) {
            Log.e("tag", "onStop: =============================mCompositeSubscription解除");
            mCompositeSubscription.dispose();
        }
    }
}
