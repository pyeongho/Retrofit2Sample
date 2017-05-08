package com.example.phkim.mycleanmvp.main.adapter;

import com.example.phkim.mycleanmvp.main.domain.model.SearchData;

public interface ImageAdapterDataModel {
    void add(SearchData imageItem);
    int getSize();
    SearchData getItem(int position);
    void clear();
}
