package ui.pages;

import io.qameta.allure.Step;
import ui.wrappers.CarWrapper;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import static com.codeborne.selenide.Selenide.*;

public class CarsReadAllPage extends BasePage{

    @Step("открытие страницы просмотра списка авто.")
    @Override
    public CarsReadAllPage openPage() {
        log.info("Opening the 'CarsReadAllPage' start page");
        open (BASE_URL + "/#/read/cars");
        return this;
    }

    @Step("Проверка открытия страницы 'Create new car'.")
    public CarsReadAllPage isPageOpened() {
        log.info("Checking 'Cars - read all' page is loaded");
        // Если openPage() уже отработал - элемент уже виден, логируем URL для отладки
        String currentUrl = com.codeborne.selenide.WebDriverRunner.driver().url();
        log.info("Current URL after load: {}", currentUrl);
        // Дополнительная проверка: если элемент не виден - открываем заново
        if (!currentUrl.equals("http://82.142.167.37:4881/#/read/cars")) {
            log.info("Form not visible, reopening page...");
            openPage();
        }
        return this;
    }

    @Step("Проверка наличия всех полей таблицы cars")
    public String[] checkAttributeTable() {
        String at1 = new CarWrapper("ID").getAttribute();
        String at2 = new CarWrapper("Engine").getAttribute();
        String at3 = new CarWrapper("Mark").getAttribute();
        String at4 = new CarWrapper("Model").getAttribute();
        String at5 = new CarWrapper("Price").getAttribute();
        return new String[]{at1, at2, at3, at4, at5};
    }

    @Step("Находим кнопку и кликаем")
    public String clickButton(String nameButton) {
        log.info("Find and click button {}", nameButton);
        $x(String.format("//button[contains(normalize-space(), '%s')]", nameButton)).click();
        sleep(500);
        String getTextButton = $x(String.format("//button[contains(normalize-space(), '%s')]", nameButton)).getText();
        return getTextButton;
    }

    @Step("Получение значений атрибутов поля для сортировки")
    public List<String> getAttribute(String numAttribute) {
        int x = 0;
        CarWrapper wrapper = new CarWrapper(numAttribute);
        Set<String> uniqueValues = new LinkedHashSet<>(); // LinkedHashSet сохраняет порядок
        for (int i = 0; x < 31; i++) {
            String valueAttribute = wrapper.getValue(i + 1);
            //проверка уникальности значение (проблемы с Price)
            if (uniqueValues.add(valueAttribute)) {
                log.info("Значение '{}' добавлено (уникальное)", valueAttribute);
                x += 1;
            } else {
                log.info("Значение '{}' уже существует, пропускаем", valueAttribute);
            }
            uniqueValues.add(valueAttribute);
        }
        return new ArrayList<>(uniqueValues); // преобразуем обратно в List
    }

    @Step("Получение значений атрибутов поля для сортировки Engine Type без проверки уникальности")
    public List<String> getAttributeEngineType(String numAttribute) {
        CarWrapper wrapper = new CarWrapper(numAttribute);
        List<String> uniqueValues = new ArrayList<String>(); //Обычный лист, так как с уникальностью проблемы
        for (int i = 0; i < 31; i++) {
            String valueAttribute = wrapper.getValue(i + 1);
            uniqueValues.add(valueAttribute);
        }
        return new ArrayList<>(uniqueValues); // преобразуем обратно в List
    }
}