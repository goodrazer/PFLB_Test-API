package ui.wrappers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$x;

// Wrapper для текстовых/числовых полей ввода (chainable API)
public class InputWrapper {

    private final SelenideElement field;

    public InputWrapper(String xpath) {
        this.field = $x(xpath);
    }

    // ввод значения с ожиданием и проверкой
    @Step("Ввод значения '{0}' в поле")
    public InputWrapper setValue(String value) {
        if (value == null) {
            value = "";
        }
        String trimmed = value.trim();
        field.shouldBe(Condition.interactable, Duration.ofSeconds(10));
        field.clear();
        if (!trimmed.isEmpty()) {
            field.sendKeys(value);
            field.shouldHave(Condition.value(value), Duration.ofSeconds(5));
        }
        return this;
    }

    // алиас для числового ввода
    @Step("Ввод числового значения '{0}' в поле")
    public InputWrapper setNumberValue(String value) {
        return setValue(value);
    }

    // получение текущего значения
    public String getValue() {
        return field.getValue();
    }

    // проверка значения
    public InputWrapper shouldHaveValue(String expectedValue) {
        field.shouldHave(Condition.value(expectedValue), Duration.ofSeconds(5));
        return this;
    }

    // Эмуляция нажатия клавиши Tab (потеря фокуса)
    public InputWrapper pressTab() {
        field.pressTab();
        return this;
    }

    public SelenideElement getElement() {
        return field;
    }
}