package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class SwaggerDevJsDeprecatedPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Swagger-dev JS (Deprecated)'")
    public SwaggerDevJsDeprecatedPage openPage() {
        log.info("Opening the 'Swagger-dev JS (Deprecated)' start page");
        open (BASE_URL + "/#/swagger");
        return this;
    }
}