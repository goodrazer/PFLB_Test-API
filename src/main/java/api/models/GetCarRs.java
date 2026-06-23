package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

public class GetCarRs {
    @SerializedName("status")
    @Expose
    @Since(1.0)
    public Boolean status;
    @SerializedName("result")
    @Expose
    @Since(1.0)
    public Result result;
    @SerializedName("id")
    @Expose
    @Since(1.0)
    public Integer id;
    @SerializedName("engineType")
    @Expose
    @Since(1.0)
    public String engineType;
    @SerializedName("mark")
    @Expose
    @Since(1.0)
    public String mark;
    @SerializedName("model")
    @Expose
    @Since(1.0)
    public String model;
    @SerializedName("price")
    @Expose
    @Since(1.0)
    public Double price;
}
