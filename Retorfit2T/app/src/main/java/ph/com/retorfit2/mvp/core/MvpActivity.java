package ph.com.retorfit2.mvp.core;

import android.os.Bundle;

import ph.com.retorfit2.BaseActivity;


public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    public void showLoading(String text) {
        showProgressDialog(text);
    }

    public void hideLoading() {
        dismissProgressDialog();
    }
}
