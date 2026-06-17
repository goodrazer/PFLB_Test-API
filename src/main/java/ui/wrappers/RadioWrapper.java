package ui.wrappers;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

//Wrapper для radio button
public class RadioWrapper {
    private final SelenideElement radio;

    public RadioWrapper(String xpath) {
        this.radio = $x(xpath);
    }

    //Выбирает radio button
    public void select() {
        if (!radio.isSelected()) {
            radio.click();
        }
    }

    // Проверяет, выбрана ли radio button
    public boolean isSelected() {
        return radio.isSelected();
    }
}
