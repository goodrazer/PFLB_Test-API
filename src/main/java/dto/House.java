package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

//Value Object для сущности House
@Builder
@Getter
@ToString
@AllArgsConstructor
public class House {
    private final int floors;
    private final double price;
    private final Parking parkingWarmCovered;
    private final Parking parkingWarmNotCovered;
    private final Parking parkingColdCovered;
    private final Parking parkingColdNotCovered;

    // Поле для хранения сгенерированного ID (не используется в форме)
    @Builder.Default
    private String generatedId = null;

    public House(int floors, double price, Parking parkingWarmCovered,
                 Parking parkingWarmNotCovered, Parking parkingColdCovered, Parking parkingColdNotCovered) {
        this.floors = floors;
        this.price = price;
        this.parkingWarmCovered = parkingWarmCovered;
        this.parkingWarmNotCovered = parkingWarmNotCovered;
        this.parkingColdCovered = parkingColdCovered;
        this.parkingColdNotCovered = parkingColdNotCovered;
        this.generatedId = null;
    }
}
