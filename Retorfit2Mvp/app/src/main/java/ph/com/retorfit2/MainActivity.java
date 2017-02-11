package ph.com.retorfit2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ph.com.retorfit2.model.TranslateMessage;
import ph.com.retorfit2.mvp.main.MainPresenter;
import ph.com.retorfit2.mvp.main.MainView;
import ph.com.retorfit2.mvp.other.MvpActivity;
import ph.com.retorfit2.retrofit.ApiCallback;
import ph.com.retorfit2.retrofit.ApiStores;
import ph.com.retorfit2.retrofit.AppClient;
import ph.com.retorfit2.retrofit.RetrofitCallback;
import retrofit2.Call;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @BindView(R.id.et_source)
    EditText etSource;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.btn_translate_with_rx_mvp)
    Button btnTranslateWithRxMvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    private void dataFail(int code, String msg) {
        Toast.makeText(getApplicationContext(), "code:" + code + "  " + msg, Toast.LENGTH_SHORT).show();
    }

    private void dataSuccess(TranslateMessage tm) {
        tvTarget.setText(tm.getMessage().getResult().getTranslatedText());
    }

    @OnClick({R.id.btn_translate_with_rx_mvp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translate_with_rx_mvp:
                if (etSource.getText() != null) {
                    mvpPresenter.loadDataByRetrofitRxjava(etSource.getText().toString());
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getDataSuccess(TranslateMessage model) {
        dataSuccess(model);

    }

    @Override
    public void getDataFail(Integer code, String msg) {
        dataFail(code, msg);
    }

}