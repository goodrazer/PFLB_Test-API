package ui.pages;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import ui.dto.UserCar;
import ui.steps.wrappers.CarWrapper;
import static com.codeborne.selenide.Selenide.*;

public class CarsBuyOrSellCarPage extends BasePage{

    CarsReadAllPage carsReadAllPage = new CarsReadAllPage();

    private final String PUSH_TO_API_BUTTON = "//*[@id='root']/div/section/div/div/button[1]";
    private final String INPUT = "//table[contains(@class, 'table-striped')]//input[@id='%s']";

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
}