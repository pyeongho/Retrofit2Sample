package ph.com.retorfit2.mvp.main;


import ph.com.retorfit2.model.TResponse;

public interface MainView extends BaseView{

    void getDataSuccess(TResponse model);

    void getDataFail(Integer code, String msg);

}
