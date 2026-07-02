package ui.pages.users;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.pages.base.BasePage;
import ui.wrappers.ButtonWrapper;
import ui.wrappers.InputWrapper;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class UsersAddMoneyPage extends BasePage {

    private final InputWrapper userIdInput =
            new InputWrapper("//input[@id='id_send']");

    private final InputWrapper moneyInput =
            new InputWrapper("//input[@id='money_send']");

    private final ButtonWrapper pushToApiButton =
            new ButtonWrapper("//button[contains(@class,'tableButton')]");

    private final SelenideElement statusMessage =
            $x("//button[contains(@class,'status')]");

    private final SelenideElement moneyDisplay =
            $x("//button[contains(@class,'money')]");

    @Override
    public BasePage openPage() {
        log.info("Opening Users Add Money page");
        open(BASE_URL + "/#/update/users/plusMoney");
        return this;
    }

    @Step("Заполнение формы пополнения баланса")
    public UsersAddMoneyPage fillForm(String userId, String money) {
        userIdInput.setValue(userId);
        if (money.matches("[\\d.,]+")) {
            moneyInput.setValue(money);
        } else {
            Selenide.executeJavaScript(
                    "arguments[0].value = arguments[1]",
                    moneyInput.getElement(),
                    money
            );
        }
        moneyInput.pressTab();
        return this;
    }

    @Step("Нажатие кнопки 'Push to Api'")
    public UsersAddMoneyPage clickPushToApi() {
        pushToApiButton.click();
        return this;
    }

    @Step("Получение статусного сообщения")
    public String getStatusMessage() {
        return statusMessage
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .getText();
    }

    @Step("Получение отображаемого баланса")
    public String getMoneyDisplayText() {
        return moneyDisplay
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .getText();
    }

    @Step("Ожидание изменения статуса")
    public String waitForStatusChange(String oldStatus) {
        statusMessage.shouldNotHave(
                Condition.text(oldStatus),
                Duration.ofSeconds(15)
        );
        return getStatusMessage();
    }
}
