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

package com.example.phkim.mycleanmvp.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.phkim.mycleanmvp.BasePresenter;
import com.example.phkim.mycleanmvp.BaseView;
import com.example.phkim.mycleanmvp.main.adapter.ImageAdapter;
import com.example.phkim.mycleanmvp.main.domain.model.SearchData;

import java.util.List;


/**
 * This specifies the contract between the view and the presenter.
 */
public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showSearch(List<SearchData> search);

        void showLoadingSearchError();

        void showNoSearch();

        boolean isActive();


        void onMoveLink(String link);
    }

    interface Presenter extends BasePresenter {

        void inputTextSearch(String s);

        void onItemClick(ImageAdapter adapter, int position);

        void clickedRxCallBack(@NonNull String query);
    }
}
