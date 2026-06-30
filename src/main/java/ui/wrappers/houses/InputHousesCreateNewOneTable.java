package ui.wrappers.houses;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import static com.codeborne.selenide.Selenide.$x;

@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class InputHousesCreateNewOneTable {
    private String fieldName;
    private String id;
    private static final String INPUT_PATTERN_XPATH_ON_HOUSES_CREATE_NEW =
            "//*[contains(text(), '%s')]/ancestor::section[@class='workspace']//parent::div//input[@id='%s']";

    @Step("Заполнение полей 'Input' первой таблицы в зависимости параметров наименования поля и id на странице:" +
            " 'Houses --> CreateNew'")
    public void writeInputHousesCreateNewOneTable(String text) {
        log.info("Filling the 'Input' fields of the first table depending on the field name '{}' and id '{}' " +
                "parameters on the page: 'Houses --> CreateNew'", fieldName, id);
        String formattedXpath = String.format(INPUT_PATTERN_XPATH_ON_HOUSES_CREATE_NEW, fieldName, id);
        $x(formattedXpath).setValue(text);
    }
}