package ui.wrappers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

//Wrapper для обычных кнопок
public class ButtonWrapper {

    private final SelenideElement button;

    public ButtonWrapper(String xpath) {
        this.button = $x(xpath);
    }

    // Кликает по кнопке с проверкой interactable
    @Step("Клик по кнопке")
    public ButtonWrapper click() {
        button.shouldBe(Condition.interactable, Duration.ofSeconds(10));
        button.click();
        return this;
    }

    // Проверяет, активна ли кнопка
    public boolean isEnabled() {
        return button.isEnabled();
    }

    // Получает текст кнопки
    public String getText() {
        return button.getText();
    }

    // Проверяет, что текст кнопки содержит ожидаемый текст
    public ButtonWrapper shouldHaveText(String expectedText) {
        button.shouldHave(Condition.text(expectedText), Duration.ofSeconds(10));
        return this;
    }
}
