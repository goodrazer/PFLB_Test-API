package wrappers;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

//Wrapper для обычных кнопок
public class ButtonWrapper {

    private final SelenideElement button;

    public ButtonWrapper(String xpath) {
        this.button = $x(xpath);
    }

    // Кликает по кнопке
    public void click() {
        button.click();
    }

    // Проверяет, активна ли кнопка
    public boolean isEnabled() {
        return button.isEnabled();
    }

    // Получает текст кнопки
    public String getText() {
        return button.getText();
    }
}
