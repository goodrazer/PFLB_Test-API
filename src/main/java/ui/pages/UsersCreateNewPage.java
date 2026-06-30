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

    // Поле "ID will be generated"
    private final SelenideElement TABLE_FIELD_CREATE_NEW_FIELD_ID_WILL_BE_GENERATED =
            $x("//td[contains(text(), 'ID will be generated')]");

    // Поля формы
    private final InputWrapper firstNameInput =
            new InputWrapper("//input[@id='first_name_send']");
    private final InputWrapper lastNameInput =
            new InputWrapper("//input[@id='last_name_send']");
    private final InputWrapper ageInput =
            new InputWrapper("//input[@id='age_send']");
    private final InputWrapper moneyInput =
            new InputWrapper("//input[@id='money_send']");

    // Радиокнопки
    private final RadioWrapper maleRadio =
            new RadioWrapper("//input[@name='sex_send' and @value='MALE']");
    private final RadioWrapper femaleRadio =
            new RadioWrapper("//input[@name='sex_send' and @value='FEMALE']");

    // Кнопка отправки и информационные сообщения
    private final ButtonWrapper pushToApiButton =
            new ButtonWrapper("//button[contains(@class, 'tableButton')]");

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
        return TABLE_FIELD_CREATE_NEW_FIELD_ID_WILL_BE_GENERATED.is(visible);
    }

    @Step("Заполнение формы данными пользователя")
    public UsersCreateNewPage addNewUser(User user) {
        firstNameInput.setValue(user.getFirstName());
        lastNameInput.setValue(user.getLastName());
        ageInput.setValue(String.valueOf(user.getAge()));

        selectSex(user.getSex());

        moneyInput
                .setValue(String.valueOf(user.getMoney()))
                .pressTab();

        return this;
    }

    private void selectSex(String sex) {
        if (sex == null) {
            return;
        }

        switch (sex.toUpperCase()) {
            case "MALE":
                maleRadio.select();
                break;

            case "FEMALE":
                femaleRadio.select();
                break;

            default:
                log.warn("Unknown sex value: {}", sex);
        }
    }

    @Step("Нажатие кнопки 'Push to Api' и ожидание изменения статуса")
    public UsersCreateNewPage clickButtonPushToApi() {
        log.info("Clicking 'Push to Api' button");

        String oldStatus = statusCodeMessage.getText();

        pushToApiButton.click();

        statusCodeMessage.shouldNotHave(
                Condition.text(oldStatus),
                Duration.ofSeconds(15)
        );

        return this;
    }

    @Step("Получение текста статуса сохранения нового объекта")
    public String getTheTextOfTheConservationStatusOfANewProperty() {
        log.info("Getting final status message");

        return statusCodeMessage
                .shouldBe(visible, Duration.ofSeconds(15))
                .getText();
    }

    @Step("Получение текста статуса")
    public String getStatusText() {
        return statusCodeMessage
                .shouldBe(visible, Duration.ofSeconds(10))
                .getText();
    }

    @Step("Получение текста сгенерированного ID пользователя")
    public String getUserIdText() {
        return newUserIdElement
                .shouldBe(visible, Duration.ofSeconds(10))
                .getText();
    }

    @Step("Получение текста нового ID пользователя")
    public String getUserIdTextSafely() {
        return newUserIdElement.exists()
                ? newUserIdElement.getText()
                : "";
    }

    @Step("Проверка генерации нового ID пользователя")
    public boolean isNewUserIdGenerated() {
        return getUserIdTextSafely()
                .matches("New user ID: \\d+");
    }
}