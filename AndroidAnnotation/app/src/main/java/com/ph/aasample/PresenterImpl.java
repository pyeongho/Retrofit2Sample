package com.ph.aasample;

import android.util.Log;

import com.ph.aasample.base.BasePresenter;
import com.ph.aasample.model.TranslateMessage;
import com.ph.aasample.retrofit.ApiCallback;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

/**
 * Created by phkim on 2017-02-14.
 */

@EBean
public class PresenterImpl extends BasePresenter<MyView>implements Presenter {

    @Override
    public void registerView(MyView view) {
        attachView(view);
    }


    @Override
    public void unRegisterView() {
        detachView();;
    }

    @Background
    @Override
    public void onInfoRequest(String  text) {
        addSubscription(apiStores.loadDataByRetrofitRx("en", "ko", text),
                new ApiCallback<TranslateMessage>() {
                    @Override
                    public void onSuccess(TranslateMessage model) {
                        mvpView.setResultText(model.getMessage().getResult().getTranslatedText());
                    }

                    @Override
                    public void onFailure(Integer code,String msg) {
                        Log.d("test",code+"   "+msg);
                    }


                    @Override
                    public void onFinish() {
                    }

                });

    }
}