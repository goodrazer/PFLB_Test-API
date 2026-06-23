package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCarRq {


    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("engineType")
    @Expose
    public String engineType;
    @SerializedName("mark")
    @Expose
    public String mark;
    @SerializedName("model")
    @Expose
    public String model;
    @SerializedName("price")
    @Expose
    public Double price;
}
