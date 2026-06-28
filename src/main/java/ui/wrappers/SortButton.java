package ui.wrappers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.$$;

@Log4j2
public class SortButton {

    String label;

    public SortButton(String label) {
        this.label = label;
    }

    public SelenideElement find() {
        log.info("Search sort button by \"{}\"", label);
        return $$("section[class='workspace'] button")
                .findBy(Condition.text(label))
                .shouldBe(Condition.visible);
    }
}
