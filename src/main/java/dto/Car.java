package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

//Value Object для сущности Car
@Builder
@Getter
@ToString
@AllArgsConstructor
public class Car {
    private final String engineType;
    private final String mark;
    private final String model;
    private final double price;

    // Поле для хранения сгенерированного ID (не используется в форме)
    @Builder.Default
    private String generatedId = null;

    public Car(String engineType, String mark, String model, double price) {
        this.engineType = engineType;
        this.mark = mark;
        this.model = model;
        this.price = price;
        this.generatedId = null;
    }
}
