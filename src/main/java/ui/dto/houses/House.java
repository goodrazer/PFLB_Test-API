package ui.dto.houses;

import lombok.*;
import ui.dto.cars.Parking;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House {
    private int floors;
    private double price;
    private Parking parkingWarmCovered;
    private Parking parkingWarmNotCovered;
    private Parking parkingColdCovered;
    private Parking parkingColdNotCovered;
    private int hasWarmAndCoveredParkingPlaces;
    private int hasWarmNotCoveredParkingPlaces;
    private int hasColdButCoveredParkingPlaces;
    private int hasColdNotCoveredParkingPlaces;

    @Builder.Default
    private String generatedId = null;
}