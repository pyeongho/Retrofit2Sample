/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.phkim.mycleanmvp;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.phkim.mycleanmvp.data.source.SearchRepositoryDaum;
import com.example.phkim.mycleanmvp.data.source.daum.SearchApi;
import com.example.phkim.mycleanmvp.data.source.daum.retrofit.RetrofitCreator;
import com.example.phkim.mycleanmvp.main.domain.usecase.GetSearch;

import retrofit2.Retrofit;

/**
 * Enables injection of production implementations for
 * {@link com.example.phkim.mycleanmvp.data.source.SearchDataSource} at compile time.
 */
public class Injection {

    public static SearchRepositoryDaum provideSearchRepository(@NonNull Context context) {
        checkNotNull(context);
        return SearchRepositoryDaum.getInstance(provideSearchApi(provideRetrofit()));
    }

    public static GetSearch provideGetSearch(@NonNull Context context) {
        return new GetSearch(provideSearchRepository(context));
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }



    public static Retrofit provideRetrofit() {
        return RetrofitCreator.createRetrofit();
    }


    public static SearchApi provideSearchApi(Retrofit retrofit) {
        return new SearchApi(retrofit);
    }

}
