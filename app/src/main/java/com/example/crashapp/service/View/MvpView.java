package com.example.crashapp.service.View;

import com.example.crashapp.service.entity.BookBean;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;

public interface MvpView<T> extends BaseView {
    void onSuccess(T bean);

    void onError(String result);


}
