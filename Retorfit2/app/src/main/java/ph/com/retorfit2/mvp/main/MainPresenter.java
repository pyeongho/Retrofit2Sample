package ph.com.retorfit2.mvp.main;

import android.util.Log;

import com.google.gson.JsonObject;

import ph.com.retorfit2.model.TranslateMessage;
import ph.com.retorfit2.mvp.other.BasePresenter;
import ph.com.retorfit2.retrofit.ApiCallback;


public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void loadDataByRetrofitRxjava(String text) {
        mvpView.showLoading("로딩중");
        addSubscription(apiStores.loadDataByRetrofitRx("en", "ko", text),
                new ApiCallback<TranslateMessage>() {
                    @Override
                    public void onSuccess(TranslateMessage model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(Integer code,String msg) {
                        Log.d("test",code+"   "+msg);
                        mvpView.getDataFail(code,msg);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }

}

