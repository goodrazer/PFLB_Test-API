package ui.steps.login;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import ui.pages.login.LoginPage;
import ui.steps.base.BaseStep;

public class LoginStep extends BaseStep {

    LoginPage loginPage = new LoginPage();

    @Step("Авторизация пользователя с валидными данными логина: ****** и пароля: ****** с кликом по кнопке 'GO'")
    public LoginStep successfulAuthorization(
            @Param(mode = Parameter.Mode.MASKED) String email,
            @Param(mode = Parameter.Mode.MASKED) String password){
        loginPage.successfulAuthorizationSwitchToAlert(email,password);
        loginPage = new LoginPage();
        return this;
    }
}
