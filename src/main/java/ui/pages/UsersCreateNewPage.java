package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class UsersCreateNewPage extends BasePage{

    //Локатор первого поля 'ID will be generated' таблицы открытой по дропдауну Users --> Create new:
    private final SelenideElement TABLE_FIELD_CREATE_NEW_FIELD_ID_WILL_BE_GENERATED = $x("//td" +
            "[contains(text(), 'ID will be generated')]");

    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersCreateNewPage' start page");
        open (BASE_URL + "/#/create/user");
        return this;
    }

    @Step("Получение текста элемента 'ID will be generated' в таблице 'Create new' из выпадающего списка 'Users'")
    public String getTextElementIDWillBeGenerated() {
        log.info("Get text element the 'ID will be generated' item in the 'Create new' table from the 'Users' drop-down list");
        return TABLE_FIELD_CREATE_NEW_FIELD_ID_WILL_BE_GENERATED
                .shouldHave(Condition.text("ID will be generated"), Duration.ofSeconds(15))
                .getText();
    }

    @Step("Отображение элемента 'ID will be generated' в таблице 'Create new' из выпадающего списка 'Users'")
    public boolean isVisibleElementIDWillBeGenerated() {
        log.info("Checking if 'ID will be generated' item is visible");
        return !TABLE_FIELD_CREATE_NEW_FIELD_ID_WILL_BE_GENERATED.is(visible);
    }
}