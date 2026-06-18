package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class SwaggerMasterMvcPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Swagger-master MVC")
    public SwaggerMasterMvcPage openPage() {
        log.info("Opening the 'Swagger-master MVC' start page");
        open ("http://82.142.167.37:4880/swagger-ui/index.html");
        return this;
    }
}