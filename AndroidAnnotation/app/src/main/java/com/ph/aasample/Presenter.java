package com.ph.aasample;

/**
 * Created by phkim on 2017-02-14.
 */

public interface Presenter {
    void registerView(MyView view);
    void unRegisterView();
    void onInfoRequest(String text);
}
