package ui.pages.houses;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.*;

public class HousesReadOneByIdPage extends BasePage {
    private final SelenideElement HOUSE_INPUT = $("#house_input");
    private final SelenideElement READ = $x(" //button[@class='tableButton btn btn-primary']");
    private final SelenideElement ID_FOUND_HOUSE = $x("//table[1]//tbody//td[1]");

    @Override
    @Step("Открытие страницы 'Houses -> Read one by ID'")
    public HousesReadOneByIdPage openPage() {
        log.info("Opening the 'Houses -> Read one by ID' start page");
        open (BASE_URL + "/#/read/house");
        return this;
    }

    @Step("Заполнение поля поиска объекта недвижимости по ID на странице 'Houses -> Read one by ID'")
    public HousesReadOneByIdPage fillingInTheHouseInputSearchField(String houseInputSearch) {
        log.info("Filling in the {house_input} search field");
        HOUSE_INPUT.setValue(houseInputSearch);
        return this;
    }

    @Step("Клик по кнопке 'Read' на странице 'Houses -> Read one by ID'")
    public HousesReadOneByIdPage clickButtonRead(){
        log.info("Click button 'Read'");
        READ.click();
        return this;
    }

    @Step("Получение ID найденного объекта недвижимости'")
    public String getIdFoundHouse(){
        log.info("Get ID found house");
        return ID_FOUND_HOUSE.getText();
    }
}