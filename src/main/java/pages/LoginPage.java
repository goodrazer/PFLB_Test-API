package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger; // Добавили импорт
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.selenide.AllureSelenide; // Добавили импорт
import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage {

    private final SelenideElement HEADLINE = $x("//div[contains(text(), ' Authorization ')]");
    private final SelenideElement INPUT_EMAIL = $x("//input[@name='email']");
    private final SelenideElement INPUT_PASSWORD = $x("//input[@name='password']");
    private final SelenideElement GO_BUTTON = $x("//button[@class='Nav-btn btn btn-primary']");

    @Override
    @Step("Открытие страницы 'Login'")
    public LoginPage openPage() {
        log.info("Opening the 'Login' start page");
        open (BASE_URL);
        return this;
    }

    @Override
    @Step("Проверка открытия страницы Login")
    public LoginPage isPageOpened() {
        log.info("Checking the display of the 'Login' page");
        HEADLINE.shouldBe(Condition.visible);
        return this;
    }

    @Step("Авторизация пользователя с валидными данными 'email' и 'password'")
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
}
