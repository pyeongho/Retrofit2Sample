package ph.com.retorfit2.retrofit;


import io.reactivex.Observable;
import ph.com.retorfit2.model.TranslateMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiStores {
    String API_SERVER_URL = "https://openapi.naver.com";

    @FormUrlEncoded
    @POST("/v1/language/translate")
    Call<TranslateMessage> loadDataByRetrofit(
            @Field("source") String source,
            @Field("target") String target,
            @Field("text") String text);

    @FormUrlEncoded
    @POST("/v1/language/translate")
    Observable<TranslateMessage> loadDataByRetrofitRx(
            @Field("source") String source,
            @Field("target") String target,
            @Field("text") String text);
}
