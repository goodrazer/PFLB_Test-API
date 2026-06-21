package ui.dto;

import lombok.*;

// Value Object для парковочного места в доме.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Parking {
    private int placesCount;
}