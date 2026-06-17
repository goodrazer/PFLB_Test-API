package ui.tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.dto.House;

public class HousesTest extends BaseTest{

    @Test(testName = "АТ.04/3.12.Проверка создания дома с заполнением обязательных полей:" +
            " {floors} и {price} валидными кредами",
            description = "Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами " +
                    "и получением успешного кода выполнения операции",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами и " +
            "получением успешного кода выполнения операции")
    @Epic("Epic04_Houses")
    @Feature("Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами")
    @Story("Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами и " +
            "получением успешного кода выполнения операции")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Flaky
    @Owner("Malevaniy Anton")
    public void checkingTheCreationOfAHouseByFillingInTheRequiredFieldsWithValidCredits() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        housesCreateNewPage.openPage();
        House house = House.builder()
                .floors(2)
                .price(3000000.00)
                .build();
        housesCreateNewPage.addNewHouse(house)
                .clickButtonPushToApi();
        Selenide.sleep(2000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                housesCreateNewPage.getTheTextOfTheConservationStatusOfANewProperty(),
                "Status: Successfully pushed, code: 201",
                "Ошибка!!! Статус выполнения операции отличается от 'Status: Successfully pushed, code: 201'");
        softAssert.assertTrue(housesCreateNewPage.isVisibleNewHouseID(),
                "Ошибка!!! ID нового объекта недвижимости не отображен на странице!");
        String actualIdText = housesCreateNewPage.getHouseIdText();
        String expectedRegex = "New house ID: \\d+";
        softAssert.assertTrue(actualIdText.matches(expectedRegex),
                String.format("Ошибка: Текст на странице '%s' не соответствует шаблону '%s'!",
                        actualIdText, expectedRegex));
        softAssert.assertAll();
    }
}