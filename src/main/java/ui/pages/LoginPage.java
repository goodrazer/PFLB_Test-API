package ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.selenide.AllureSelenide;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage {

    private final SelenideElement TITLE = $x("//div[contains(text(), ' Authorization ')]");
    private final SelenideElement INPUT_EMAIL = $x("//input[@name='email']");
    private final SelenideElement INPUT_PASSWORD = $x("//input[@name='password']");
    private final SelenideElement GO_BUTTON = $x("//button[@class='Nav-btn btn btn-primary']");
    private final SelenideElement LOGOUT_BUTTON = $x("//button[@class='Nav-btn btn btn-danger']");
    private final SelenideElement ERROR_MASSAGE_INVALID_EMAIL = $x("//div[text()='incorrect Email']");
    private final SelenideElement ERROR_MASSAGE_INVALID_PASSWORD = $x("//div[text()='password length " +
            "must be more than 3 symbols and less than 8 symbols']");
    private final SelenideElement ERROR_MASSAGE_EMAIL_CANNOT_BE_EMPTY = $x("//div[text()=" +
            "'email cannot be empty']");
    private final SelenideElement ERROR_MASSAGE_PASSWORD_CANNOT_BE_EMPTY = $x("//div[text()=" +
            "'password cannot be empty']");

    @Override
    @Step("Открытие страницы авторизации.")
    public LoginPage openPage() {
        log.info("Opening the 'Login' start page");
        open (BASE_URL);
        return this;
    }

    @Step("Авторизация пользователя с валидными данными: 'Email' и 'Password'.")
    public LoginPage positiveLogin(
            @Param(mode = Parameter.Mode.MASKED) String email,
            @Param(mode = Parameter.Mode.MASKED) String password) {
        log.info("User authorization with valid data");
        SelenideLogger.removeListener("AllureSelenide");
        INPUT_EMAIL.setValue(email);
        INPUT_PASSWORD.setValue(password);
        GO_BUTTON.click();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
        return this;
    }

    @Step("Заполнение поля 'Email' значением: ******")
    public LoginPage enterEmail(@Param(mode = Parameter.Mode.MASKED) String email) {
        log.info("Filling in the 'Email' field");
        SelenideLogger.removeListener("AllureSelenide");
        INPUT_EMAIL.setValue(email);
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
        return this;
    }

    @Step("Заполнение поля 'Password' значением: ******")
    public LoginPage enterPassword(@Param(mode = Parameter.Mode.MASKED) String password) {
        log.info("Filling in the 'Password' field");
        SelenideLogger.removeListener("AllureSelenide");
        INPUT_PASSWORD.setValue(password);
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
        return this;
    }

    @Step("Нажатие кнопки 'GO'")
    public LoginPage clickGoButton() {
        log.info("Click the 'GO' button");
        GO_BUTTON.click();
        return this;
    }

    @Step("Нажатие кнопки 'LOGOUT'")
    public LoginPage clickLogoutButton() {
        log.info("Click the 'LOGOUT' button");
        LOGOUT_BUTTON.click();
        return this;
    }

    @Step("Получение текста ошибки при вводе невалидного 'Email'.")
    public String getErrorMessageEmail() {
        log.info("Receiving error text when entering an invalid 'Email'.");
        return ERROR_MASSAGE_INVALID_EMAIL.getText();
    }

    @Step("Получение текста ошибки при вводе невалидного 'Password'.")
    public String getErrorMessagePassword() {
        log.info("Receiving error text when entering an invalid 'Password'.");
        return ERROR_MASSAGE_INVALID_PASSWORD.getText();
    }

    @Step("Проверка отображения заголовка на странице 'Authorization'.")
    public boolean isTitleVisible() {
        log.info("Checking the display title on the 'Authorization' page.");
        return TITLE.is(visible);
    }

    @Step("Проверка отображения поля для ввода 'Email' на странице 'Authorization'.")
    public boolean isEmailFieldVisible() {
        log.info("Checking the display of the 'Email' input field on the 'Authorization' page.");
        return INPUT_EMAIL.is(visible);
    }

    @Step("Проверка отображения поля для ввода 'Password' на странице 'Authorization'.")
    public boolean isPasswordFieldVisible() {
        log.info("Checking the display of the 'Password' input field on the 'Authorization' page.");
        return INPUT_PASSWORD.is(visible);
    }

    @Step("Проверка отображения кнопки 'GO' на странице 'Authorization'.")
    public boolean isGoButtonVisible() {
        log.info("Checking whether the 'GO' button is displayed on the 'Authorization' page.");
        return GO_BUTTON.is(visible);
    }

    @Step("Проверка отображения кнопки 'LOGOUT' на странице 'Authorization'.")
    public boolean isLogoutButtonVisible() {
        log.info("Checking whether the 'LOGOUT' button is displayed on the 'Authorization' page.");
        return LOGOUT_BUTTON.is(visible);
    }

    @Step("Проверка отображения ошибки при вводе пустого 'Email' на странице 'Authorization'.")
    public String getTextMessageEmailCannotBeEmpty() {
        log.info("Check if an error is displayed when entering an empty 'Email' on the 'Authorization' page.");
        return ERROR_MASSAGE_EMAIL_CANNOT_BE_EMPTY.shouldBe(visible).getText();
    }

    @Step("Проверка отображения ошибки при вводе пустого 'Password' на странице 'Authorization'.")
    public String getTextMessagePasswordCannotBeEmpty() {
        log.info("Check if an error is displayed when entering an empty 'Password' on the 'Authorization' page.");
        return ERROR_MASSAGE_PASSWORD_CANNOT_BE_EMPTY.shouldBe(visible).getText();
    }

    @Step("Клик клавиши 'Tab' на поле ввода 'Email'")
    public LoginPage clickTabKeyEmailField() {
        log.info("Click the 'Tab' key on the 'Email' input field.");
        INPUT_EMAIL.pressTab();
        return this;
    }

    @Step("Клик клавиши 'Tab' на поле ввода 'Password'")
    public LoginPage clickTabKeyPasswordField() {
        log.info("Click the 'Tab' key on the 'Password' input field.");
        INPUT_PASSWORD.pressTab();
        return this;
    }
}