package ui.steps;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.selenide.AllureSelenide;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class LoginStep extends BaseStep{

    private final SelenideElement INPUT_EMAIL = $x("//input[@name='email']");
    private final SelenideElement INPUT_PASSWORD = $x("//input[@name='password']");
    private final SelenideElement GO_BUTTON = $x("//button[@class='Nav-btn btn btn-primary']");

    @Step("Авторизация пользователя с валидными данными логина: ****** и пароля: ****** с кликом по кнопке 'Login'")
    public LoginStep successfulAuthorization(
            @Param(mode = Parameter.Mode.MASKED) String email,
            @Param(mode = Parameter.Mode.MASKED) String password) {
        log.info("User authorization with valid data");
        SelenideLogger.removeListener("AllureSelenide");
        open(BASE_URL);
        INPUT_EMAIL.setValue(email);
        INPUT_PASSWORD.setValue(password);
        GO_BUTTON.click();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
        Selenide.sleep(2000);
        Selenide.confirm("Successful authorization");
        Selenide.sleep(1000);
        return this;
    }
}