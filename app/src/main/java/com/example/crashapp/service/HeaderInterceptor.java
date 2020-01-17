package com.example.crashapp.service;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = addRequestHeader(request);
        Response response = chain.proceed(newRequest);

        return response;
    }
    private Request addRequestHeader(Request request) {
        Request result = request.newBuilder()
                .addHeader("device_id", "")
                .build();

        return result;
    }
}
