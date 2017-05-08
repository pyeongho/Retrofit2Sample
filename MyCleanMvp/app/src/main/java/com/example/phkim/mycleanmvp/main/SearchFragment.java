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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.phkim.mycleanmvp.R;
import com.example.phkim.mycleanmvp.main.adapter.ImageAdapter;
import com.example.phkim.mycleanmvp.main.adapter.OnRecyclerItemClickListener;
import com.example.phkim.mycleanmvp.main.domain.model.SearchData;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchContract.Presenter mPresenter;
    private EditText mEtSearch;
    private RecyclerView mRvSearchResult;
    private ImageAdapter mListAdapter;
    private Button mBtnUseRxCallBack;


    public SearchFragment() {
        // Requires empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListAdapter = new ImageAdapter(getContext(),new ArrayList<>(0), getOnRecyclerItemClickListener());
    }

    private OnRecyclerItemClickListener getOnRecyclerItemClickListener(){
        return (adapter, position) -> mPresenter.onItemClick(adapter,position);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_image, container, false);
        initUi(root);
        addTextWatcher(mEtSearch);
        addClickListener(mBtnUseRxCallBack);
        return root;
    }

    private void addClickListener(Button btnUseRxCallBack) {
        btnUseRxCallBack.setOnClickListener(v -> {
            String text = mEtSearch.getText().toString();
            if(!TextUtils.isEmpty(text)){
                mPresenter.clickedRxCallBack(text);
            }
        });
    }

    private void addTextWatcher(EditText et){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.inputTextSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void initUi(View v){
        mEtSearch =(EditText)v.findViewById(R.id.et_home_search);
        mRvSearchResult =(RecyclerView)v.findViewById(R.id.rv_home_search_result);
        mBtnUseRxCallBack =(Button)v.findViewById(R.id.btn_rx_callback);
        mRvSearchResult.setAdapter(mListAdapter);
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        Log.d("search",""+active);
    }

    @Override
    public void showSearch(List<SearchData> search) {
        mListAdapter.replaceData(search);
    }

    @Override
    public void showLoadingSearchError() {
        mListAdapter.clear();
    }

    @Override
    public void showNoSearch() {

    }



    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onMoveLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }


    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
