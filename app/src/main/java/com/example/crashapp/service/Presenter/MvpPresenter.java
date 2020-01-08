package com.example.crashapp.service.Presenter;

import android.util.Log;

import com.example.crashapp.service.View.MvpView;
import com.example.crashapp.service.entity.BookBean;
import com.example.crashapp.service.Manager.DataManager;
import com.example.crashapp.service.entity.ReadBean;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MvpPresenter extends BasePresenter<MvpView> {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private BookBean mbookBean;
    private ReadBean readBean;

    public MvpPresenter() {
        Log.e("tag", 222222 + "");
        manager = new DataManager(getContext());
        mCompositeSubscription = new CompositeSubscription();
    }


    public void getSearchBooks(String name, String tag, int start, int count) {
        mCompositeSubscription.add(manager.createUser(name, tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onCompleted() {
                        if (isViewAttached() && mbookBean != null) {
                            getView().hideLoading();
                            getView().onSuccess(mbookBean);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        mbookBean = bookBean;
                    }
                })
        );
    }

    public void getReadBooks(String name, String tag, int start, int count) {
        mCompositeSubscription.add(manager.getSreaadBook(name, tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReadBean>() {
                    @Override
                    public void onCompleted() {
                        if (isViewAttached() && readBean != null) {
                            getView().hideLoading();
                            getView().onSuccess(readBean);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Http", "onError: " + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(ReadBean bookBean) {
                        readBean = bookBean;
                    }
                })
        );
    }

    @Override
    void onCreate() {
        Log.e("tag", 111111111 + "");
        manager = new DataManager(getView().getContext());
        mCompositeSubscription = new CompositeSubscription();
    }

    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
        super.onStop();
    }
}
