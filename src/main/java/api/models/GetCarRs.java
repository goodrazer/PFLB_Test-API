package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import lombok.Data;

@Data
public class GetCarRs {
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
