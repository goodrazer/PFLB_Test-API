package ui.dto;

import lombok.*;

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

    // Поле для хранения сгенерированного ID (не используется в форме)
    @Builder.Default
    private String generatedId = null;
}
