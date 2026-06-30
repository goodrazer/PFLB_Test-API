package ui.pages.cars;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import ui.dto.users.UserCar;
import ui.pages.BasePage;
import ui.wrappers.cars.CarWrapper;
import static com.codeborne.selenide.Selenide.*;

public class CarsBuyOrSellCarPage extends BasePage {

    CarsReadAllPage carsReadAllPage = new CarsReadAllPage();

    private final String PUSH_TO_API_BUTTON = "//*[@id='root']/div/section/div/div/button[1]";
    private final String INPUT = "//table[contains(@class, 'table-striped')]//input[@id='%s']";
    private static final String RADIO_BUY = "//input[@name='settleOrEvict' and @value='buyCar']";
    private static final String RADIO_SELL = "//input[@name='settleOrEvict' and @value='sellCar']";

    @Override
    public CarsBuyOrSellCarPage openPage() {
        log.info("Opening the 'CarsBuyOrSellCarPage' start page");
        open (BASE_URL + "/#/update/users/buyCar");
        return this;
    }

    @Step("Проверка открытия страницы 'Create new car'.")
    public CarsBuyOrSellCarPage isPageOpened() {
        log.info("Checking 'Cars - buy or sell car' page is loaded");
        // Если openPage() уже отработал - элемент уже виден, логируем URL для отладки
        String currentUrl = WebDriverRunner.driver().url();
        log.info("Current URL after load: {}", currentUrl);
        // Дополнительная проверка: если элемент не виден - открываем заново
        if (!currentUrl.equals("http://82.142.167.37:4881/#/update/users/buyCar")) {
            log.info("Form not visible, reopening page...");
            openPage();
        }
        return this;
    }

    @Step("Ввод значений в поля UserId и CarId")
    public String [] inputUserId(UserCar userCar) {
        String [] values = new String[2];
        new CarWrapper("id_send").write(userCar.getUserId());
        new CarWrapper("car_send").write(userCar.getCarId());
        $x(PUSH_TO_API_BUTTON).click();
        sleep(500);
        String idUser = $x(String.format(INPUT, "id_send")).getValue();
        values[0] = idUser;
        String idCar = $x(String.format(INPUT, "car_send")).getValue();
        values[1] = idCar;
        return values;
    }

    @Step("Нажатие на стрелку в полях, где доступен ввод числового значения")
    public CarsBuyOrSellCarPage inputNumberWithArrow(int upUserId, int downUserId, int upCarId, int downCarId) {
        log.info("input UserId with arrowUp {}, arrowDown {}.", upUserId, downUserId);
        log.info("input CarId with arrowUp {}, arrowDown {}.", upCarId, downCarId);
        // Дополнительная проверка: если элемент не виден - открываем заново
        new CarWrapper("id_send").inputStep(upUserId, downUserId);
        new CarWrapper("car_send").inputStep(upCarId, downCarId);
        return this;
    }

    @Step("Проверка радиокнопки Buy")
    public boolean checkRadioButtonBuy() {
        log.info("Check work radioButton Buy ");
        $x(RADIO_SELL).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();
        $x(RADIO_BUY).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();
        boolean isSelected = $x(RADIO_BUY).isSelected();
        return isSelected;
    }

    @Step("Проверка радиокнопки Sell")
    public boolean checkRadioButtonSell() {
        log.info("Check work radioButton Buy ");
        $x(RADIO_BUY).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();
        $x(RADIO_SELL).shouldBe(Condition.interactable, java.time.Duration.ofSeconds(10)).click();
        boolean isSelected = $x(RADIO_SELL).isSelected();
        return isSelected;
    }

    @Step("Проверка активности радиокнопки Sell")
    public boolean checkRadioButtonSellIsNotSelected() {
        log.info("Check work radioButton isNotSelected");
        boolean isSelected = true;
        isSelected = $x(RADIO_SELL).isSelected();
        return isSelected;
    }

    @Step("Проверка активности радиокнопки Buy")
    public boolean checkRadioButtonBuyIsNotSelected() {
        log.info("Check work radioButton isNotSelected");
        boolean isSelected = true;
        isSelected = $x(RADIO_BUY).isSelected();
        return isSelected;
    }
}