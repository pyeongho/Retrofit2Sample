package ph.com.retorfit2.mvp.main;

import android.util.Log;

import ph.com.retorfit2.model.TResponse;
import ph.com.retorfit2.model.TranslateMessage;
import ph.com.retorfit2.mvp.core.BasePresenter;
import ph.com.retorfit2.retrofit.ApiCallback;
import retrofit2.Response;


public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void loadDataByRetrofitRxjava(String text) {


        mvpView.showLoading("로딩중");
        addSubscription(
                apiStores.loadDataByRetrofitRx("en", "ko", text),
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

