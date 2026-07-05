package ui.helpers;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import ui.dto.cars.CarGenerated;

@Log4j2
public class CarFactory {

    public static CarGenerated getCarGenerated() {
        log.info("Generation data for new user.");
        Faker faker = new Faker();
        return new CarGenerated(faker.options().option("Diesel", "CNG", "Hydrogenic", "Electric", "PHEV",
                "Gasoline"), faker.lorem().word(), faker.lorem().word(),
                String.valueOf(faker.number().numberBetween(10, 100)));
    }
}
