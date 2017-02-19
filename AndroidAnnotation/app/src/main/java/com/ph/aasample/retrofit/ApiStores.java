package com.ph.aasample.retrofit;


import com.ph.aasample.model.TranslateMessage;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.POST;


public interface ApiStores {
    String API_SERVER_URL = "https://openapi.naver.com";


    @FormUrlEncoded
    @POST("/v1/language/translate")
    Observable<TranslateMessage> loadDataByRetrofitRx(
            @Field("source") String source,
            @Field("target") String target,
            @Field("text") String text);
}
