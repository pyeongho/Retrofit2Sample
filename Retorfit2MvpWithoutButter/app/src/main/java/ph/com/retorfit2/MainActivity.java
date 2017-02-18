package ph.com.retorfit2;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ph.com.retorfit2.databinding.ActivityMainBinding;
import ph.com.retorfit2.model.TResponse;
import ph.com.retorfit2.model.TranslateMessage;
import ph.com.retorfit2.mvp.core.MvpActivity;
import ph.com.retorfit2.mvp.main.MainEventHandler;
import ph.com.retorfit2.mvp.main.MainPresenter;
import ph.com.retorfit2.mvp.main.MainView;


public class MainActivity extends MvpActivity<MainPresenter> implements MainView,MainEventHandler {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setHandler(this);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    private void dataFail(int code, String msg) {
        Toast.makeText(getApplicationContext(), "code:" + code + "  " + msg, Toast.LENGTH_SHORT).show();
    }

    private void dataSuccess(TResponse<TranslateMessage> tr) {
        mBinding.tvTarget.setText(tr.data.getMessage().getResult().getTranslatedText());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getDataSuccess(TResponse model) {
        dataSuccess(model);
    }

    @Override
    public void getDataFail(Integer code, String msg) {
        dataFail(code, msg);
    }

    @Override
    public void onClickTranslate(View view) {
        if (mBinding.etSource.getText() != null) {
            mvpPresenter.loadDataByRetrofitRxjava(mBinding.etSource.getText().toString());
        }
    }

}

