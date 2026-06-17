package ui.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

public class HousesReadOneByIdPage extends BasePage{
    private final SelenideElement HOUSE_INPUT = $("#house_input");
    private final SelenideElement READ = $x(" //button[@class='tableButton btn btn-primary']");
    private final SelenideElement ID_FOUND_HOUSE = $x("//table[1]//tbody//td[1]");

    @Override
    public HousesReadOneByIdPage openPage() {
        log.info("Opening the 'HousesReadOneByIdPage' start page");
        open (BASE_URL + "/#/read/house");
        return this;
    }

    public HousesReadOneByIdPage fillingInTheHouseInputSearchField(String houseInputSearch) {
        log.info("Filling in the {house_input} search field");
        HOUSE_INPUT.setValue(houseInputSearch);
        return this;
    }

    public HousesReadOneByIdPage clickButtonRead(){
        log.info("Click button 'Read'");
        READ.click();
        return this;
    }

    public String getIdFoundHouse(){
        log.info("Get ID found house");
        return ID_FOUND_HOUSE.getText();
    }
}