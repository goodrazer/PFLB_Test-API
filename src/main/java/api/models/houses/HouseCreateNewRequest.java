package api.models.houses;

import api.models.parking.ParkingRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseCreateNewRequest {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("floorCount")
    @Expose
    private Integer floorCount;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("parkingPlaces")
    @Expose
    private List<ParkingRequest> parkingPlaces;
}
