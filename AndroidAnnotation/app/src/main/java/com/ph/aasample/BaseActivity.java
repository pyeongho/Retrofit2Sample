package com.ph.aasample;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by phkim on 2017-02-14.
 */

public abstract class BaseActivity <T extends ViewDataBinding> extends AppCompatActivity {
    private T mVd;


    protected void setBiding(@LayoutRes int layId){
        if(mVd ==null){
            mVd = DataBindingUtil.setContentView(this, layId);
        }
    }

    protected T getBinding() {
        return mVd;
    }
}