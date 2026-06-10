package ui.tests;

import ui.dto.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.AllPostPage;
import ui.pages.LoginPage;
import lombok.extern.log4j.Log4j2;
import ui.steps.LoginStep;

@Log4j2
public class AllPostTest extends BaseTest{

    @Test
    public void testFullUserFlow() {
        // Авторизация
        log.info("Step 1: Authorizing user");
        LoginStep loginStep = new LoginStep();
        loginStep.successfulAuthorization(validEmail, validPassword);
//        //new LoginPage()
//                .openPage()
//                .positiveLogin(validEmail, validPassword);

        // Подготовка тестовых данных
        log.info("Step 2: Preparing test data");
        // Данные для создания пользователя
        User user = User.builder()
                .firstName("AQA_User")
                .lastName("Testov")
                .age(30)
                .sex("MALE")
                .money(10000)
                .build();
        // Данные для создания автомобиля
        Car car = Car.builder()
                .engineType("Electric")
                .mark("Tesla")
                .model("Model Z")
                .price(5000.00)
                .build();
        // Данные для создания дома (0 - нет такой парковки)
        House house = House.builder()
                .floors(3)
                .price(50000.00)
                .parkingWarmCovered(new Parking(2))
                .parkingWarmNotCovered(new Parking(1))
                .parkingColdCovered(new Parking(0))
                .parkingColdNotCovered(new Parking(0))
                .build();
        // Данные для добавления денег пользователю
        double moneyToAdd = 10000.00;

        // Открытие страницы All POST
        log.info("Step 3: Opening All POST page");
        AllPostPage allPostPage = new AllPostPage();
        allPostPage.openPage()
                .isPageOpened();

        // Создание пользователя
        log.info("Step 4: Creating user");
        allPostPage.createUser(user);
        String userId = allPostPage.getGeneratedUserId();
        Assert.assertNotNull(userId, "User ID должен быть сгенерирован");
        Assert.assertTrue(userId.matches("\\d+"), "User ID должен быть числовой");
        log.info("Created user with ID: {}", userId);
    }
}