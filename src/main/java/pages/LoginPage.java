package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage{

    private final SelenideElement HEADLINE = $x("//div[contains(text(), ' Authorization ')]");

    @Override
    @Step("Открытие страницы 'Login'")
    public LoginPage openPage() {
        log.info("Opening the 'Login' start page");
        open ("");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы Login")
    public LoginPage isPageOpened() {
        log.info("Checking the display of the 'Login' page");
        HEADLINE.shouldBe(Condition.visible);
        return this;
    }
}