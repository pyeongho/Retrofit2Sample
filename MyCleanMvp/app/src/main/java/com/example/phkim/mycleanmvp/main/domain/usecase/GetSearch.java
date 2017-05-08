/*
 * Copyright 2016, The Android Open Source Project
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

package com.example.phkim.mycleanmvp.main.domain.usecase;

import android.support.annotation.NonNull;

import com.example.phkim.mycleanmvp.ApiCallback;
import com.example.phkim.mycleanmvp.UseCase;
import com.example.phkim.mycleanmvp.data.source.SearchDataSource;
import com.example.phkim.mycleanmvp.data.source.SearchRepositoryDaum;
import com.example.phkim.mycleanmvp.main.domain.model.SearchData;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fetches the list of tasks.
 */
public class GetSearch extends UseCase<GetSearch.RequestValues, GetSearch.ResponseValue> {

    private final SearchRepositoryDaum mRepository;

    public GetSearch(@NonNull SearchRepositoryDaum searchRepository) {
        mRepository = checkNotNull(searchRepository, "SearchRepositoryDaum cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        mRepository.getSearch(values.getQuery(), new SearchDataSource.LoadSearchCallback() {
            @Override
            public void onSearchLoaded(List<SearchData> searchDataList) {
                ResponseValue responseValue = new ResponseValue(searchDataList);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });

    }

    public void executeUseCaseRx(final RequestValues values,ApiCallback<List<SearchData>> call) {
        mRepository.getSearch(values.getQuery(), call);
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String mQuery;

        public RequestValues(@NonNull String query) {
            mQuery =query;
        }

        private String getQuery() {
            return mQuery;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<SearchData> mListSearchData;

        private ResponseValue(@NonNull List<SearchData> tasks) {
            mListSearchData = checkNotNull(tasks, "SearchData cannot be null!");
        }

        public List<SearchData> getSearch() {
            return mListSearchData;
        }
    }
}
