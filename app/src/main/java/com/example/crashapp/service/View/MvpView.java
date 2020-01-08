package com.example.crashapp.service.View;

import com.example.crashapp.service.entity.BookBean;

public interface MvpView<T> extends BaseView {
    void onSuccess(T mBook);

    void onError(String result);
}
