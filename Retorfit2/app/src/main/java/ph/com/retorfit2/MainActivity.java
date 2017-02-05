package ph.com.retorfit2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ph.com.retorfit2.model.TranslateMessage;
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
                tvTarget.setText(model.getMessage().getResult().getTranslatedText());
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("Test", "" + msg);
                tvTarget.setText("fail code " + code + "  " + msg);
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

    @OnClick(R.id.btn_translate)
    public void onClick() {
        if(etSource.getText()!=null){
            showProgressDialog("번역중");
            loadDataByRetrofit(etSource.getText().toString());
        }
    }
}
