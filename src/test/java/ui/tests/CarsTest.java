package ui.tests;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ui.dto.Car;
import ui.dto.House;
import ui.dto.Parking;
import ui.dto.User;
import ui.pages.AllDeletePage;
import ui.pages.AllPostPage;
import ui.pages.CarsCreateNewPage;
import ui.steps.LoginStep;
import ui.steps.wrappers.InputCars;

import static com.codeborne.selenide.Selenide.sleep;

@Log4j2
@Epic("Cars")
@Feature("Cars")
@Owner("Egor P.")
@Link(value = "docs.google", name = "Чек-лист PFLB")
public class CarsTest extends BaseTest{

    @DataProvider(name = "Тестовые данные для позитивного создания авто")
    public Object[][] carDataPositive() {
        return new Object[][] {
                //Дизель + минимальная сумма
                {Car.builder().engineType("Diesel").mark("Hyundai").model("Solaris").price("0.01").build(), "Status: Successfully pushed, code: 201"},
                //Бензиновый + цифры в марке
                //{Car.builder().engineType("Gasoline").mark("123").model("Solaris").price("4444.99").build(), "Status: Successfully pushed, code: 201"},
                //Электрический + максимальная сумма + спецсимволы в марке
                {Car.builder().engineType("Electric").mark(";%№").model("Solaris").price("9999999999999999.99").build(), "Status: Successfully pushed, code: 201"},
                //CNG + спецсимволы в моделе
                {Car.builder().engineType("CNG").mark("Hyundai").model(";%№").price("4444.99").build(), "Status: Successfully pushed, code: 201"},
                //PHEV + цифры в моделе
                //{Car.builder().engineType("PHEV").mark("Hyundai").model("123").price("4444.99").build(), "Status: Successfully pushed, code: 201"},
                //Hydrogenic + по 1 символу в марке и моделе + сумма без копеек
                {Car.builder().engineType("Hydrogenic").mark("H").model("R").price("100").build(), "Status: Successfully pushed, code: 201"}
        };
    }

    @Test(testName = "АТ.03.01.Успешное создание нового авто",
            description = "Проверка создания автомобиля с валидными данными со страницы Cars -> Create new",
            priority = 1,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true,
            dataProvider = "Тестовые данные для позитивного создания авто")
    @Description("Проверка создания автомобиля с валидными данными со страницы Cars -> Create new")
    @Story("Создание авто")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void testCreateNewCar(Car car, String statusMessage) {
        log.info("Step 1: Authorizing user and open 'Create new Car' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsCreateNewPage.openPage()
                .isPageOpened()
                .createNewCar(car);
        Assert.assertEquals(carsCreateNewPage.getStatusMessage(),
                statusMessage,
                "Ошибка!!! Сообщение об успехе 'Status: Successfully pushed, code: 201' не отображено!");
    }

    @DataProvider(name = "Тестовые данные для негативного создания авто")
    public Object[][] carData() {
        return new Object[][] {
                //Пустой тип двигателя
                {Car.builder().engineType("").mark("Hyundai").model("Solaris").price("4999.99").build(), "Status: Invalid request data"},
                //Тип двигателя не соответствует допустимому значению
                //{Car.builder().engineType("Trash").mark("Hyundai").model("Solaris").price("4999.99").build(), "Status: Invalid request data"},
                //Пустая марка
                {Car.builder().engineType("Diesel").mark("").model("Discovery").price("10999.99").build(), "Status: Invalid request data"},
                //Пустая модель
                {Car.builder().engineType("Electric").mark("Tesla").model("").price("9999999.99").build(), "Status: Invalid request data"},
                //Отрицательная стоимость
                {Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("-5000").build(), "Status: Invalid request data"},
                //Нулевая стоимость
                //{Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("0.00").build(), "Status: Invalid request data"},
                //Слишком высокая стоимость
                //{Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("100000000000000000.00").build(), "Status: Invalid request data"},
                //Нечисловое значение в атрибуте стоимость
                {Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("Жига").build(), "Status: Invalid request data"},
        };
    }

    @Test(testName = "АТ.03.02.Cоздание нового авто c ошибкой",
            description = "Проверка создания автомобиля с невалидными данными со страницы Cars -> Create new",
            priority = 2,
            groups = {"Negative", "E2E", "Regression", "Smoke"},
            enabled = true,
            dataProvider = "Тестовые данные для негативного создания авто")
    @Description("Проверка создания автомобиля с невалидными данными со страницы Cars -> Create new")
    @Story("Создание авто")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void testErrorCreateCar(Car car1, String errorMessage) {
        log.info("Step 1: Authorizing user and open 'Create new Car' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsCreateNewPage.openPage()
                .isPageOpened()
                .createNewCar(car1);
        Assert.assertEquals(carsCreateNewPage.getStatusMessage(),
                errorMessage,
                "Ошибка!!! Сообщение об ошибке 'Status: Invalid request data' не отображено!");
    }
}
