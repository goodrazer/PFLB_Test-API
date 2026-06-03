package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.selenide.AllureSelenide;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage {

    private final SelenideElement TITLE = $x("//div[contains(text(), ' Authorization ')]");
    private final SelenideElement INPUT_EMAIL = $x("//input[@name='email']");
    private final SelenideElement INPUT_PASSWORD = $x("//input[@name='password']");
    private final SelenideElement GO_BUTTON = $x("//button[@class='Nav-btn btn btn-primary']");
    private final SelenideElement ERROR_MASSAGE_INVALID_EMAIL = $x("//div[text()='incorrect Email']");
    private final SelenideElement ERROR_MASSAGE_INVALID_PASSWORD = $x("//div[text()='password length must be more than 3 symbols and less than 8 symbols']");

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

    @Step("Заполнение поля 'Email'")
    public LoginPage enterEmail(String email) {
        log.info("Filling in the 'Email' field");
        INPUT_EMAIL.setValue(email);
        return this;
    }

    @Step("Заполнение поля 'Password'")
    public LoginPage enterPassword(String password) {
        log.info("Filling in the 'Password' field");
        INPUT_PASSWORD.setValue(password);
        return this;
    }

    @Step("Нажатие кнопки 'GO'")
    public LoginPage clickGoButton() {
        log.info("Click the 'GO' button");
        GO_BUTTON.click();
        return this;
    }

    @Step("Получение текста ошибки при вводе невалидного 'Email'.")
    public String getErrorMessageEmail() {
        return ERROR_MASSAGE_INVALID_EMAIL.getText();
    }

    @Step("Получение текста ошибки при вводе невалидного 'Password'.")
    public String getErrorMessagePassword() {
        return ERROR_MASSAGE_INVALID_PASSWORD.getText();
    }
}