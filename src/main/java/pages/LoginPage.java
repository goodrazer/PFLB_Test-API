package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage{

    private final SelenideElement HEADLINE = $x("//div[contains(text(), ' Authorization ')]");
    private final SelenideElement INPUT_EMAIL = $x("//input[@name='email']");
    private final SelenideElement INPUT_PASSWORD = $x("//input[@name='password']");
    private final SelenideElement GO_BUTTON = $x("//button[@class='Nav-btn btn btn-primary']");


    @Override
    @Step("Открытие страницы 'Login'")
    public LoginPage openPage() {
        log.info("Opening the 'Login' start page");
        open ("http://82.142.167.37:4881");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы Login")
    public LoginPage isPageOpened() {
        log.info("Checking the display of the 'Login' page");
        HEADLINE.shouldBe(Condition.visible);
        return this;
    }

    @Step("Авторизация пользователя с валидными данными логина и пароля")
    public LoginPage positiveLogin(String user, String password) {
        log.info("User authorization with valid data");
        INPUT_EMAIL.setValue(user);
        INPUT_PASSWORD.setValue(password);
        GO_BUTTON.click();
        return this;
    }
}