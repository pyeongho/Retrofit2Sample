package ph.com.retorfit2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import ph.com.retorfit2.retrofit.ApiCallback;
import ph.com.retorfit2.retrofit.ApiStores;
import ph.com.retorfit2.retrofit.AppClient;
import ph.com.retorfit2.retrofit.RetrofitCallback;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_source)
    EditText etSource;
    @BindView(R.id.btn_translate)
    Button btnTranslate;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.btn_translate_with_rx)
    Button btnTranslateWithRx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    private void loadDataByRetrofit(String Text) {

        ApiStores apiStores = AppClient.retrofit().create(ApiStores.class);
        Call<TranslateMessage> call = apiStores.loadDataByRetrofit("en", "ko", Text);
        call.enqueue(new RetrofitCallback<TranslateMessage>() {
            @Override
            public void onSuccess(TranslateMessage model) {
                // 결과 문구 출력
                dataSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("Test", "" + msg);
                dataFail(code,msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                Log.d("Test", "" + t.getMessage());
            }

            @Override
            public void onFinish() {
                // 로딩 팝업 제거
                dismissProgressDialog();
            }
        });

    }

    private void dataFail(int code, String msg){
        Toast.makeText(getApplicationContext(),"code:"+code+"  "+msg, Toast.LENGTH_SHORT).show();
    }
    private void dataSuccess(TranslateMessage tm){
        tvTarget.setText(tm.getMessage().getResult().getTranslatedText());
    }

    private ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @OnClick({R.id.btn_translate, R.id.btn_translate_with_rx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translate:
                if (etSource.getText() != null) {
                    showProgressDialog("번역중");
                    loadDataByRetrofit(etSource.getText().toString());
                }
                break;
            case R.id.btn_translate_with_rx:
                if (etSource.getText() != null) {
                    showProgressDialog("번역중");
                    loadDataWithRx(etSource.getText().toString());

                }
                break;
        }

    }

    private void loadDataWithRx(String text){
        ApiStores apiStores = AppClient.retrofit().create(ApiStores.class);
        addSubscription(apiStores.loadDataByRetrofitRx("en", "ko", text),
                new ApiCallback<TranslateMessage>() {
                    @Override
                    public void onSuccess(TranslateMessage model) {
                        dataSuccess(model);
                    }

                    @Override
                    public void onFailure(Integer code, String msg) {
                        dataFail(code,msg);
                    }

                    @Override
                    public void onFinish() {
                        dismissProgressDialog();
                    }
                });
    }
    private CompositeDisposable mCompositeSubscription;

    public void addSubscription(Observable<TranslateMessage> observable, ApiCallback<TranslateMessage> callback) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(callback));
    }

    public void onUnsubscribe() {
        Log.d("test", "onUnsubscribe");
        if (mCompositeSubscription != null)
            mCompositeSubscription.clear();
    }

    @Override
    protected void onDestroy() {
       onUnsubscribe();
        super.onDestroy();

    }
}