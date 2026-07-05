package ui.dto.cars;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Builder
public class CarGenerated {
    private final String engineType;
    private final String mark;
    private final String model;
    private final String price;
}
