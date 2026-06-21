package ui.wrappers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

//Wrapper для текстовых/числовых полей ввода (возвращает this для цепочки)
public class InputWrapper {
    private final SelenideElement field;

    public InputWrapper(String xpath) {
        this.field = $x(xpath);
    }

    //вводит значение с очисткой поля и проверкой.
    @Step("Ввод значения '{0}' в поле")
    public InputWrapper setValue(String value) {
        field.shouldBe(Condition.interactable, Duration.ofSeconds(10));
        field.clear();
        field.sendKeys(value);
        field.shouldHave(Condition.value(value), Duration.ofSeconds(5));
        return this;
    }

    //вводит числовое значение (алиас для setValue)
    @Step("Ввод числового значения '{0}' в поле")
    public InputWrapper setNumberValue(String value) {
        return setValue(value);
    }

    //получает текущее значения поля
    public String getValue() {
        return field.getValue();
    }

    //Проверяет, что поле содержит ожидаемое значение.
    public InputWrapper shouldHaveValue(String expectedValue) {
        field.shouldHave(Condition.value(expectedValue), Duration.ofSeconds(5));
        return this;
    }
}
