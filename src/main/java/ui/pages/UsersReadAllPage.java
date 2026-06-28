package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class UsersReadAllPage extends BasePage {
    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersReadAllPage' start page");
        open(BASE_URL + "/#/read/users");
        return this;
    }

    @Step("Проверка наличия пользователя с ID = {userId} в таблице")
    public boolean isUserPresentById(String userId) {
        String xpath = String.format("//table//td[contains(text(), '%s')]", userId);
        SelenideElement cell = $x(xpath);
        try {
            cell.shouldBe(visible, Duration.ofSeconds(10));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}

