package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.dto.User;
import ui.wrappers.ButtonWrapper;
import ui.wrappers.InputWrapper;
import ui.wrappers.RadioWrapper;

import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class UsersCreateNewPage extends BasePage {

    //Локатор первого поля 'ID will be generated' таблицы открытой по дропдауну Users --> Create new:
    private final SelenideElement TABLE_FIELD_CREATE_NEW_FIELD_ID_WILL_BE_GENERATED = $x("//td" +
            "[contains(text(), 'ID will be generated')]");
    private final InputWrapper firstNameInput = new InputWrapper("//input[@id='first_name_send']");
    private final InputWrapper lastNameInput = new InputWrapper("//input[@id='last_name_send']");
    private final InputWrapper ageInput = new InputWrapper("//input[@id='age_send']");
    private final InputWrapper moneyInput = new InputWrapper("//input[@id='money_send']");
    private final RadioWrapper maleRadio = new RadioWrapper("//input[@name='sex_send' and @value='MALE']");
    private final RadioWrapper femaleRadio = new RadioWrapper("//input[@name='sex_send' and @value='FEMALE']");
    private final ButtonWrapper pushToApiButton = new ButtonWrapper("//button[contains(@class, 'tableButton')]");
    private final SelenideElement statusCodeMessage =
            $x("//button[@disabled and contains(@class, 'status')]");
    private final SelenideElement newUserIdElement =
            $x("//button[@disabled and contains(@class, 'newId')]");

    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersCreateNewPage' start page");
        open(BASE_URL + "/#/create/user");
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

    @Step("Заполнение формы данными пользователя")
    public UsersCreateNewPage addNewUser(User user) {
        log.info("Filling user form with data: {}", user);
        firstNameInput.setValue(user.getFirstName());
        lastNameInput.setValue(user.getLastName());
        ageInput.setValue(String.valueOf(user.getAge()));

        if (user.getSex() != null) {
            if ("MALE".equalsIgnoreCase(user.getSex())) {
                maleRadio.select();
            } else if ("FEMALE".equalsIgnoreCase(user.getSex())) {
                femaleRadio.select();
            } else {
                log.warn("Unknown sex value: {}, skipping selection", user.getSex());
            }
        } else {
            log.info("Sex is null – skipping radio selection");
        }
        moneyInput.setValue(String.valueOf(user.getMoney()));
        // Эмуляция потери фокуса (как при ручном вводе)
        moneyInput.pressTab(); // или можно использовать Actions для клика вне поля
        return this;
    }

    @Step("Нажатие кнопки 'Push to Api'")
    public UsersCreateNewPage clickButtonPushToApi() {
        log.info("Clicking 'Push to Api' button");
        pushToApiButton.click();
        return this;
    }

    @Step("Получение текста статуса сохранения нового объекта (ожидание успешного статуса)")
    public String getTheTextOfTheConservationStatusOfANewProperty() {
        log.info("Waiting for status 'Successfully pushed'");
        return statusCodeMessage
                .shouldHave(Condition.text("Status: Successfully pushed"), Duration.ofSeconds(15))
                .getText();
    }

    @Step("Получение текста статуса (без ожидания конкретного значения)")
    public String getStatusText() {
        log.info("Getting status text");
        return statusCodeMessage.shouldBe(visible, Duration.ofSeconds(10)).getText();
    }

    @Step("Проверка отображения ID нового пользователя")
    public boolean isVisibleNewUserID() {
        log.info("Checking if new user ID is visible");
        return newUserIdElement.is(visible);
    }

    @Step("Получение текста сгенерированного ID пользователя")
    public String getUserIdText() {
        log.info("Getting generated user ID text");
        return newUserIdElement.shouldBe(visible, Duration.ofSeconds(10)).getText();
    }
}

