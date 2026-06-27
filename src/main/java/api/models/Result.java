package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {

    @SerializedName("code")
    @Expose
    @Since(1.0)
    public String code;
}
