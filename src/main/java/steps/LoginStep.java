package steps;

import io.qameta.allure.Step;
import pages.LoginPage;
import static com.codeborne.selenide.Selenide.switchTo;

public class LoginStep {
    private final LoginPage loginPage;

    public LoginStep(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    @Step("Авторизация пользователя с валидными данными логина и пароля с кликом по кнопке 'Login'")
    public LoginStep successfulAuthorization(String validEmail, String validPassword) {
        loginPage.openPage()
                .positiveLogin(validEmail, validPassword);
        switchTo().alert().accept();
        return this;
    }
}