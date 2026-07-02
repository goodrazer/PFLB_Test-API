package ui.wrappers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import static com.codeborne.selenide.Selenide.$$;

@Log4j2
public class TableColumn {

    String label;

    public TableColumn(String label) {
        this.label = label;
    }

    public SelenideElement find() {
        log.info("Search table column by {}", label);
        return $$("thead th")
                .findBy(Condition.text(label))
                .shouldBe(Condition.visible);
    }
}
