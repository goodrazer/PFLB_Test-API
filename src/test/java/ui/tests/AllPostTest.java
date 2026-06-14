package ui.tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import ui.dto.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.AllDeletePage;
import ui.pages.AllPostPage;
import lombok.extern.log4j.Log4j2;
import ui.steps.LoginStep;

/*
Создать пользователя -> получить ID
Пополнить пользователю баланс
Создать автомобиль -> получить ID
Купить авто пользователем
Продать авто пользователем
Создать дом -> получить ID
Заселить пользователя в дом
Проверить: пользователь отображается в жильцах дома
Выселить пользователя из дома
Удалить пользователя
Удалить дом
Удалить автомобиль
 */
@Log4j2
@Epic("Epic02_CRUD операции")
@Feature("Полный цикл CRUD")
@Owner("Oleg P.")
@Link(value = "docs.google", name = "Чек-лист PFLB")
public class AllPostTest extends BaseTest{

    @Test(testName = "АТ.02.01.Полный цикл: создание, операции и удаление сущностей",
            description = "E2E-сценарий: создание пользователя -> пополнение баланса -> создание авто -> " +
                    "покупка/продажа авто -> создание дома -> заселение/выселение -> удаление всех сущностей",
            priority = 1,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("Полный цикл CRUD: создание, операции и удаление пользователя, автомобиля и дома")
    @Story("Полный цикл создания, операций и удаления сущностей")
    @Severity(SeverityLevel.BLOCKER)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void testFullUserFlow() {
        // Авторизация
        log.info("Step 1: Authorizing user\n");
        LoginStep loginStep = new LoginStep();
        loginStep.successfulAuthorization(validEmail, validPassword);

        // Подготовка тестовых данных
        log.info("Step 2: Preparing test data\n");
        // Данные для создания пользователя
        User user = User.builder()
                .firstName("AQA_User")
                .lastName("Testov")
                .age(30)
                .sex("MALE")
                .money(100000)
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
        log.info("Step 3: Opening All POST page\n");
        AllPostPage allPostPage = new AllPostPage();
        allPostPage.openPage()
                .isPageOpened();

        // Создание пользователя
        log.info("Step 4: Creating user");
        allPostPage.createUser(user);
        String userId = allPostPage.getGeneratedUserId();
        Assert.assertNotNull(userId, "User ID должен быть сгенерирован");
        Assert.assertTrue(userId.matches("\\d+"), "User ID должен быть числовой");
        log.info("Created user with ID: {}\n", userId);

        // Пополнение баланса
        log.info("Step 5: Adding money to user");
        allPostPage.addMoneyToUser(userId, moneyToAdd);
        String balance = allPostPage.getCurrentBalance();
        Assert.assertNotNull(balance, "Баланс должен отображаться");
        log.info("Balance after adding money: {}\n", balance);

        // Создание автомобиля
        log.info("Step 6: Creating car");
        allPostPage.createCar(car);
        String carId = allPostPage.getGeneratedCarId();
        Assert.assertNotNull(carId, "Car ID должен быть сгенерирован");
        Assert.assertTrue(carId.matches("\\d+"), "Car ID должен быть числовой");
        log.info("Created car with ID: {}\n", carId);

        // Покупка автомобиля пользователем
        log.info("Step 7: Buying car by user");
        allPostPage.buyCar(userId, carId);
        log.info("User {} bought car {}\n", userId, carId);

        // Продажа автомобиля пользователем
        log.info("Step 8: Selling car by user");
        allPostPage.sellCar(userId, carId);
        log.info("User {} sold car {}\n", userId, carId);

        // Создание дома
        log.info("Step 9: Creating house");
        allPostPage.createHouse(house);
        String houseId = allPostPage.getGeneratedHouseId();
        Assert.assertNotNull(houseId, "House ID должен быть сгенерирован");
        Assert.assertTrue(houseId.matches("\\d+"), "House ID должен быть числовой");
        log.info("Created house with ID: {}\n", houseId);

        // Заселение пользователя в дом
        log.info("Step 10: Settling user in house");
        allPostPage.settleUserInHouse(userId, houseId);
        log.info("User {} settled in house {}\n", userId, houseId);

        // Выселение пользователя из дома
        log.info("Step 11: Evicting user from house");
        allPostPage.evictUserFromHouse(userId, houseId);
        log.info("User {} evicted from house {}\n", userId, houseId);

        // Удаление сущностей через All DELETE
        log.info("Step 12: Opening All DELETE page\n");
        AllDeletePage allDeletePage = new AllDeletePage();
        allDeletePage.openPage().isPageOpened();

        // Удаляем автомобиль
        log.info("Step 13: Deleting car");
        allDeletePage.deleteCar(carId);
        log.info("Car {} deleted\n", carId);

        // Удаляем дом
        log.info("Step 14: Deleting house");
        allDeletePage.deleteHouse(houseId);
        log.info("House {} deleted\n", houseId);

        // Удаляем пользователя
        log.info("Step 15: Deleting user");
        allDeletePage.deleteUser(userId);
        log.info("User {} deleted\n", userId);

        // Финальные логи
        log.info(" Full user flow with deleting completed successfully!");
        log.info("User ID: {}, Car ID: {}, House ID: {}\n", userId, carId, houseId);
    }
}