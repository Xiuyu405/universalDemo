package com.example.crashapp.service;

import com.example.crashapp.service.entity.BookBean;
import com.example.crashapp.service.entity.ReadBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitService {
    @GET("book/search")
    Observable<BookBean> getSearchBook(@Query("q") String name,
                                       @Query("tag") String tag,
                                       @Query("start") int start,
                                       @Query("count") int count);

    @GET("book/search")
    Observable<ReadBean> getSreaadBook(@Query("q") String name,
                                       @Query("tag") String tag,
                                       @Query("start") int start,
                                       @Query("count") int count);

    @POST("users/new")
    Observable<BookBean> createUser(@Body BookBean user);
}
