package api.adapters.houses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingAdapter {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("isCovered")
    @Expose
    private Boolean isCovered;

    @SerializedName("isWarm")
    @Expose
    private Boolean isWarm;

    @SerializedName("placesCount")
    @Expose
    private Integer placesCount;
}
