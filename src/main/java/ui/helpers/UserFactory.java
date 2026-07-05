package ui.helpers;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import ui.dto.users.UserGenerated;

@Log4j2
public class UserFactory {

    public static UserGenerated getUserGenerated() {
        log.info("Generation data for new user.");
        Faker faker = new Faker();
        return new UserGenerated(faker.name().firstName(), faker.name().lastName(),
                String.valueOf(faker.number().numberBetween(18, 101)), faker.options().option("MALE", "FEMALE"),
                String.valueOf(faker.number().numberBetween(100, 1000)));
    }
}
