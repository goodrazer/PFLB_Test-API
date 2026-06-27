package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import lombok.Data;

@Data
public class GetUserRs {
    @SerializedName("id")
    @Expose
    @Since(1.0)
    public Integer id;
    @SerializedName("firstName")
    @Expose
    @Since(1.0)
    public String firstName;
    @SerializedName("secondName")
    @Expose
    @Since(1.0)
    public String secondName;
    @SerializedName("age")
    @Expose
    @Since(1.0)
    public Integer age;
    @SerializedName("sex")
    @Expose
    @Since(1.0)
    public String sex;
    @SerializedName("money")
    @Expose
    @Since(1.0)
    public Double money;
}
