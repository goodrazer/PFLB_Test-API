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

    // Поле для хранения сгенерированного ID (не используется в форме)
    @Builder.Default
    private String generatedId = null;
}
