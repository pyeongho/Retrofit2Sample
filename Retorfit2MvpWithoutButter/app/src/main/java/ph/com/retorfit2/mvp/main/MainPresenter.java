package ph.com.retorfit2.mvp.main;

import android.util.Log;

import ph.com.retorfit2.model.TranslateMessage;
import ph.com.retorfit2.mvp.core.BasePresenter;
import ph.com.retorfit2.retrofit.ApiCallback;


public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void loadDataByRetrofitRxjava(String text) {


        mvpView.showLoading("로딩중");
        addSubscription(apiStores.loadDataByRetrofitRx("en", "ko", text),
                new ApiCallback<Object>() {
                    @Override
                    public void onSuccess(Object model) {
                        try {
                            Log.d("test",
                                    ((TranslateMessage)model).getMessage().getResult().getTranslatedText());
                        }catch (Exception e){
                            Log.e("error",e.getMessage());
                        }

                        //mvpView.getDataSuccess(model);
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

