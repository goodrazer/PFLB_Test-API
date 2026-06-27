package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
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
