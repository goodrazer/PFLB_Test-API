package ui.wrappers.cars;

import lombok.extern.log4j.Log4j2;
import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class InputCars {

    String label;

    public InputCars(String label) {
        this.label = label;
    }

    public void write(String text) {
        log.info("Writing '{}' in to '{}'", text, label);
        $x(String.format("//table[contains(@class, 'table-striped')]//input[@id='%s']", label)).sendKeys(text);
    }
}
