package ph.com.retorfit2.model;

/**
 * Created by pyeonghokim on 2017. 2. 18..
 */

public class TResponse<T> {
    public T data;
    public TResponse(){

    }
    public TResponse(T data) {
        this.data = data;
    }

    public static <T> TResponse<T> create(T data) {
        return new TResponse<T>(data);
    }
}
