package ui.pages.swaggers;

import ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.open;

public class SwaggerDevJsDeprecatedPage extends BasePage {
    @Override
    public SwaggerDevJsDeprecatedPage openPage() {
        log.info("Opening the 'SwaggerDevJsDeprecatedPage' start page");
        open (BASE_URL + "/#/swagger");
        return this;
    }
}