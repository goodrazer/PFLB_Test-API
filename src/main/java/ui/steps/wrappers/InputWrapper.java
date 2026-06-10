package ui.steps.wrappers;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

//Wrapper для текстовых/числовых полей ввода (возвращает this для цепочки)
public class InputWrapper {
    private final SelenideElement field;

    public InputWrapper(String xpath) {
        this.field = $x(xpath);
    }

    //вводит значение
    public InputWrapper setValue(String value) {
        //field.setValue(value);
        field.clear();
        field.sendKeys(value);
        sleep(1000);
        return this;
    }

    //вводит числовое значение
    public InputWrapper setNumberValue(String value) {
        //field.setValue(value);
        field.clear();
        field.sendKeys(value);
        sleep(1000);
        return this;
    }

    public String getValue() {
        return field.getValue();
    }
}
