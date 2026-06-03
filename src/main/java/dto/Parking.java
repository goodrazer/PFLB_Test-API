package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// Value Object для парковочного места в доме.
@Getter
@ToString
@AllArgsConstructor
public class Parking {
    private final int placesCount;
}
