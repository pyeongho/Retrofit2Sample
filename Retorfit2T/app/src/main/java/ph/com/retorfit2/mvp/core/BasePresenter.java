package ph.com.retorfit2.mvp.core;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ph.com.retorfit2.model.TResponse;
import ph.com.retorfit2.model.TranslateMessage;
import ph.com.retorfit2.retrofit.ApiCallback;
import ph.com.retorfit2.retrofit.ApiStores;
import ph.com.retorfit2.retrofit.AppClient;
import retrofit2.Response;


public class BasePresenter<V> {
    public V mvpView;
    protected ApiStores apiStores;
    private CompositeDisposable mCompositeSubscription;

    protected void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = AppClient.retrofit().create(ApiStores.class);
    }


     void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }


    private void onUnsubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }


    protected <T> void addSubscription(Observable<T> observable,ApiCallback<T> callback) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }

        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(callback));
    }


}
