package ui.dto;

import lombok.*;

//Value Object для сущности User.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String firstName;
    private String lastName;
    private int age;
    private String sex;  // "MALE" или "FEMALE"
    private double money;

    // Поле для хранения сгенерированного ID (не используется в форме)
    @Builder.Default
    private String generatedId = null;
}
