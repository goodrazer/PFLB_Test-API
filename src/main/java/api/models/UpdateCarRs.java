package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

public class UpdateCarRs {

    @SerializedName("status")
    @Expose
    @Since(1.0)
    public Boolean status;
    @SerializedName("result")
    @Expose
    @Since(1.0)
    public Result result;
}
