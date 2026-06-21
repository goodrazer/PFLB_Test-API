package ui.wrappers;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class CarWrapper {

    String label;

    public CarWrapper(String label) {
        this.label = label;
    }

    //Input
    public void write(String text) {
        log.info("Writing '{}' in to '{}'", text, label);
        $x(String.format("//table[contains(@class, 'table-striped')]//input[@id='%s']", label)).sendKeys(text);
    }

    //Input with button
    public void inputStep(Integer up, Integer down) {
        log.info("Button Up '{}' and down '{}', in input {}", up, down, label);
        SelenideElement element = $x(String.format("//table[contains(@class, 'table-striped')]//input[@id='%s']",
                label));
        for (int i = 0; i <= up; i++) {
            element.sendKeys(Keys.ARROW_UP);
        }
        for (int i = 0; i <= down; i++) {
            element.sendKeys(Keys.ARROW_DOWN);
        }
    }

    //Получаем названия атрибутов из таблицы
    public String getAttribute() {
        log.info("Get attribute {}", label);
        String nameAttribute = $x(String.format("//th[contains(text(), '%s')]", label)).getText();
        return nameAttribute;
    }

    //Получаем значения атрибутов в таблице Cars read
    public String getValue(Integer number) {
        String valueAttribute = $x(String.format("//*[@id='root']/div/section/div/table/tbody/tr[%s]/td[%s]",
                number, label)).getText();
        return valueAttribute;
    }
}