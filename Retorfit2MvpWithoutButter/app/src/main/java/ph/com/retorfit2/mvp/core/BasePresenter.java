package ph.com.retorfit2.mvp.core;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;
import ph.com.retorfit2.model.TranslateMessage;
import ph.com.retorfit2.retrofit.ApiCallback;
import ph.com.retorfit2.retrofit.ApiStores;
import ph.com.retorfit2.retrofit.AppClient;


public class BasePresenter<V> {
    public V mvpView;
    protected ApiStores apiStores;
    private CompositeDisposable mCompositeSubscription;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = AppClient.retrofit().create(ApiStores.class);
    }


    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }


    public void onUnsubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }


    public void addSubscription(Observable<TranslateMessage> observable , ApiCallback<TranslateMessage> callback) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(callback));
    }


}
