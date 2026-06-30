package api.models.cars;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCarRs {
    // Jackson автоматически сопоставит поле "id" в JSON с полем id в классе
    private Integer id;
    private String engineType;
    private String mark;
    private String model;
    private Double price;
}
