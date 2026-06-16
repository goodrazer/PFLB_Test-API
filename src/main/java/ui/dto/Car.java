package ui.dto;

import lombok.*;

//Value Object для сущности Car
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Car {
    private String engineType;
    private String mark;
    private String model;
    private String price;

    // Поле для хранения сгенерированного ID (не используется в форме)
    @Builder.Default
    private String generatedId = null;
}
