package api.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO пользователя для API - PersonDto из Swagger:
 * Структура из Swagger:
 * {
 *   "id": 0,
 *   "firstName": "string",
 *   "secondName": "string",
 *   "age": 0,
 *   "sex": "MALE" | "FEMALE",
 *   "money": 0
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("secondName")
    @Expose
    private String secondName;

    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("sex")
    @Expose
    private String sex;  // "MALE" или "FEMALE"

    @SerializedName("money")
    @Expose
    private Double money;
}
