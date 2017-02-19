package com.ph.aasample;


import android.os.Bundle;


import com.ph.aasample.databinding.ActivityMainBinding;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

@EActivity
public class MainActivity extends BaseActivity<ActivityMainBinding> implements MyView{
    @Bean(PresenterImpl.class)
    Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBiding(R.layout.activity_main);

    }
    @Override
    public void onDestroy() {
        presenter.unRegisterView();
    }
    @AfterInject
    void initObject() {
        presenter.registerView(this);
    }

    @Click
    void myButton() {
        presenter.onInfoRequest(getBinding().myInput.getText().toString());

    }

    @UiThread
    @Override
    public void setResultText(String result) {
        getBinding().myTextView.setText(result);
    }


}
