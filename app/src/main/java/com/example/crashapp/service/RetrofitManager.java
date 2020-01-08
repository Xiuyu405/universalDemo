package com.example.crashapp.service;

import com.example.crashapp.service.entity.BookBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
//    public void getRetrofittest(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.douban.com/v2/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RetrofitService service = retrofit.create(RetrofitService.class);
//        Call<BookBean> call =  service.getSearchBook("金瓶梅", null, 0, 1);
//        call.enqueue(new Callback<BookBean>() {
//            @Override
//            public void onResponse(Call<BookBean> call, Response<BookBean> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<BookBean> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void getRxjavatest(){
//        BookBean bookBean =new BookBean();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.douban.com/v2/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
//                .build();
//        RetrofitService service = retrofit.create(RetrofitService.class);
//        Observable<BookBean> observable =service.createUser(bookBean);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
//                .subscribe(new Observer<BookBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace(); //请求过程中发生错误
//                    }
//
//                    @Override
//                    public void onNext(BookBean bookBean) {
//
//                    }
//                });
//    }
}
