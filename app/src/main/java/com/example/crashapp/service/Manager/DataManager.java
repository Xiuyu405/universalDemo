package com.example.crashapp.service.Manager;

import android.content.Context;

import com.example.crashapp.service.RetrofitHelper;
import com.example.crashapp.service.RetrofitService;
import com.example.crashapp.service.entity.BookBean;
import com.example.crashapp.service.entity.ReadBean;
import com.example.crashapp.service.entity.listBean;

import io.reactivex.Observable;


public class DataManager {
    private RetrofitService mRetrofitService;
    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance().getServer();
    }

    public Observable<BookBean> createUser(String name, String tag, int start, int count){
        return mRetrofitService.getSearchBook(name,tag,start,count);
    }

    public Observable<ReadBean> getSreaadBook(String name, String tag, int start, int count){
        return mRetrofitService.getSreaadBook(name,tag,start,count);
    }
    public Observable<listBean> getFeedArticleList(int num){
        return mRetrofitService.getFeedArticleList(num);
    }
}
