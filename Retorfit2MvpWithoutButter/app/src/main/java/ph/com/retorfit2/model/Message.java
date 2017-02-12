package ph.com.retorfit2.model;

/**
 * Created by phkim on 2017-02-03.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("@service")
    @Expose
    private String service;
    @SerializedName("@version")
    @Expose
    private String version;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}