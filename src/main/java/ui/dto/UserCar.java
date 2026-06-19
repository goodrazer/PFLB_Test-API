package ui.dto;

import lombok.*;

//Value Object для сущности Car
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserCar {
    private String userId;
    private String carId;
}
