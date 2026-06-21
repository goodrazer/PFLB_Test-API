package ui.wrappers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

//Wrapper для radio button
public class RadioWrapper {
    private final SelenideElement radio;

    public RadioWrapper(String xpath) {
        this.radio = $x(xpath);
    }

    //Выбирает radio button
    @Step("Выбор radio button")
    public RadioWrapper select() {
        radio.shouldBe(Condition.interactable, Duration.ofSeconds(10));
        if (!radio.isSelected()) {
            radio.click();
        }
        return this;
    }

    // Проверяет, выбрана ли radio button
    public boolean isSelected() {
        return radio.isSelected();
    }
}
