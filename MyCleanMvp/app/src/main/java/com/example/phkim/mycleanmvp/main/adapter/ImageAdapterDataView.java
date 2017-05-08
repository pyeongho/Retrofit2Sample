package com.example.phkim.mycleanmvp.main.adapter;


import com.example.phkim.mycleanmvp.main.domain.model.SearchData;

import java.util.List;

public interface ImageAdapterDataView {
    void refresh();
    void replaceData(List<SearchData> searchData);

}
