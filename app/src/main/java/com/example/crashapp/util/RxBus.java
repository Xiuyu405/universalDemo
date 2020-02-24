package com.example.crashapp.util;

import androidx.annotation.NonNull;

import com.example.crashapp.service.View.BaseView;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxBus {
    private static volatile RxBus mInstance;
    private final Relay<Object> mBus;
    private final Map<Class<?>, Object> mStickyEventMap;

    //    public static <T> ObservableTransformer<T, T> transform(final BaseView view) {
//        return observable -> observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(view.bindToLifecycle());
//    }
    public static <T> ObservableTransformer<T, T> transform() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private RxBus() {
        mBus = PublishRelay.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getDefault() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * post event
     */
    public void post(Object event) {
        mBus.accept(event);
    }

    /**
     * 接受
     * receive event
     */
    public <T> Observable<T> tObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * post sticky event
     */
    public void postSticky(Object event) {
        mStickyEventMap.put(event.getClass(), event);
        post(event);
    }

    /**
     * receive sticky event
     */
    public <T> Observable<T> tObservableSticky(final Class<T> eventType) {
        Observable<T> observable = mBus.ofType(eventType);
        final Object event = mStickyEventMap.get(eventType);

        if (event != null) {
            return observable.mergeWith(Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> e) throws Exception {
                    e.onNext(eventType.cast(event));
                }
            }));
        } else {
            return observable;
        }
    }

    //region sticky event method

    public <T> T getStickyEvent(Class<T> eventType) {
        return eventType.cast(mStickyEventMap.get(eventType));
    }

    public <T> T removeStickyEvent(Class<T> eventType) {
        return eventType.cast(mStickyEventMap.remove(eventType));
    }

    public void removeAllStickyEvents() {
        mStickyEventMap.clear();
    }

    //endregion

    //region application onTerminate control

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public void reset() {
        mInstance = null;
    }
}
