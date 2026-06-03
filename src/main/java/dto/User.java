package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

//Value Object для сущности User.
@Builder
@Getter
@ToString
@AllArgsConstructor
public class User {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String sex;  // "MALE" или "FEMALE"
    private final double money;

    // Поле для хранения сгенерированного ID (не используется в форме)
    @Builder.Default
    private String generatedId = null;

    public User(String firstName, String lastName, int age, String sex, double money) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.money = money;
        this.generatedId = null;
    }
}
