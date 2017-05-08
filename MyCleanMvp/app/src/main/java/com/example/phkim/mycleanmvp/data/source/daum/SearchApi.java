package com.example.phkim.mycleanmvp.data.source.daum;


import com.example.phkim.mycleanmvp.BuildConfig;
import com.example.phkim.mycleanmvp.data.source.daum.search.SearchChannel;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class SearchApi {

    private Retrofit retrofit;


    public SearchApi(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public Observable<SearchChannel> searchText(String text, int pageCount) {
        return retrofit.create(Api.class)
                .getSearch(text, pageCount);
    }

    interface Api {
        @GET("image?output=json&apikey=" + BuildConfig.DAUM_API_KEY)
        Observable<SearchChannel> getSearch(@Query("q") String seachText, @Query("pageno") int pageNo);
    }
}
