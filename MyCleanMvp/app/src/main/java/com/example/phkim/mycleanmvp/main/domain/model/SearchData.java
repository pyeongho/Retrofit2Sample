package com.example.phkim.mycleanmvp.main.domain.model;


/**
 * Created by pyeonghokim on 2017. 5. 7..
 */

public class SearchData {
    private String title;
    private String imgUrl;
    private String originalUrl;


    public SearchData(String _title, String _imgUrl, String _originalUrl){
        title=_title;
        imgUrl= _imgUrl;
        originalUrl = _originalUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
