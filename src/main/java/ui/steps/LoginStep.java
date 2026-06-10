package ui.steps;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import ui.pages.LoginPage;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;

public class LoginStep {
    LoginPage loginPage;

    public LoginStep() {
        loginPage = new LoginPage();
    }

    @Step("Авторизация пользователя с валидными данными логина и пароля с кликом по кнопке 'Login'")
    public LoginStep successfulAuthorization(String validEmail, String validPassword) {
        loginPage.openPage()
                .positiveLogin(validEmail, validPassword);
        //switchTo().alert().accept();
        sleep(2000);
        Selenide.confirm("Successful authorization");
        sleep(1000);
        return this;
    }
}