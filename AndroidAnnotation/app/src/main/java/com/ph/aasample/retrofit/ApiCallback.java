package com.ph.aasample.retrofit;

/**
 * Created by pyeonghokim on 2017. 2. 5..
 */

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import io.reactivex.observers.DisposableObserver;

public abstract class ApiCallback<M> extends DisposableObserver<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(Integer code, String msg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 504) {
                msg = "gateway timeout";
            }
            if (code == 502 || code == 404) {
                msg = "not found";
            }
            onFailure(code,e.getMessage());
        } else {
            onFailure(404,e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(M model) {
        onSuccess(model);

    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
