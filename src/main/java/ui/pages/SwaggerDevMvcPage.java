package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class SwaggerDevMvcPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Swagger-dev MVC")
    public SwaggerDevMvcPage openPage() {
        log.info("Opening the 'Swagger-dev MVC' start page");
        open ("http://82.142.167.37:4879/swagger-ui/index.html");
        return this;
    }
}