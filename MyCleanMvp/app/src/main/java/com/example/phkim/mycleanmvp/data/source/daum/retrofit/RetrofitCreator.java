package com.example.phkim.mycleanmvp.data.source.daum.retrofit;

import com.example.phkim.mycleanmvp.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {

//    public static Retrofit createRetrofit() {
//
//        return new Retrofit.Builder()
//                .baseUrl("https://apis.daum.net/search/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//    }

    public static Retrofit createRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient okHttpClient = builder.build();
        return new Retrofit.Builder()
                .baseUrl("https://apis.daum.net/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();


    }
}
