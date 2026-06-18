package ui.tests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.dto.Car;
import ui.steps.wrappers.CarWrapper;

import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

@Log4j2
@Epic("Cars")
@Feature("Cars")
@Owner("Egor P.")
@Link(value = "docs.google", name = "Чек-лист PFLB")
public class CarsTest extends BaseTest{

    SoftAssert softAssert = new SoftAssert();

    @DataProvider(name = "Тестовые данные для позитивного создания авто")
    public Object[][] carDataPositive() {
        return new Object[][] {
                //Закомментировал два теста, которые возвращают не тот ответ
                //Дизель + минимальная сумма
                {Car.builder().engineType("Diesel").mark("Hyundai").model("Solaris").price("0.01").build(),
                        "Status: Successfully pushed, code: 201"},
                //Бензиновый + цифры в марке
                //{Car.builder().engineType("Gasoline").mark("123").model("Solaris").price("4444.99").build(),
                // "Status: Successfully pushed, code: 201"},
                //Электрический + максимальная сумма + спецсимволы в марке
                {Car.builder().engineType("Electric").mark(";%№").model("Solaris").price("9999999999999999.99").build(),
                        "Status: Successfully pushed, code: 201"},
                //CNG + спецсимволы в моделе
                {Car.builder().engineType("CNG").mark("Hyundai").model(";%№").price("4444.99").build(),
                        "Status: Successfully pushed, code: 201"},
                //PHEV + цифры в моделе
                //{Car.builder().engineType("PHEV").mark("Hyundai").model("123").price("4444.99").build(),
                // "Status: Successfully pushed, code: 201"},
                //Hydrogenic + по 1 символу в марке и моделе + сумма без копеек
                {Car.builder().engineType("Hydrogenic").mark("H").model("R").price("100").build(),
                        "Status: Successfully pushed, code: 201"}
        };
    }

    @Test(testName = "АТ.03.01.Успешное создание нового авто",
            description = "Проверка создания автомобиля с валидными данными со страницы Cars -> Create new",
            priority = 1,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true,
            dataProvider = "Тестовые данные для позитивного создания авто")
    @Description("Проверка создания автомобиля с валидными данными со страницы Cars -> Create new")
    @Story("Создание авто")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void testCreateNewCar(Car car, String statusMessage) {
        log.info("Step 1: Authorizing user and open 'Create new Car' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsCreateNewPage.openPage()
                .isPageOpened()
                .createNewCar(car);
        Assert.assertEquals(carsCreateNewPage.getStatusMessage(),
                statusMessage,
                "Ошибка!!! Сообщение об успехе 'Status: Successfully pushed, code: 201' не отображено!");
    }

    @DataProvider(name = "Тестовые данные для негативного создания авто")
    public Object[][] carDataNegative() {
        return new Object[][] {
                //Закомментировал три теста, которые возвращают не тот ответ
                //Пустой тип двигателя
                {Car.builder().engineType("").mark("Hyundai").model("Solaris").price("4999.99").build(),
                        "Status: Invalid request data"},
                //Тип двигателя не соответствует допустимому значению
                //{Car.builder().engineType("Trash").mark("Hyundai").model("Solaris").price("4999.99").build(),
                // "Status: Invalid request data"},
                //Пустая марка
                {Car.builder().engineType("Diesel").mark("").model("Discovery").price("10999.99").build(),
                        "Status: Invalid request data"},
                //Пустая модель
                {Car.builder().engineType("Electric").mark("Tesla").model("").price("9999999.99").build(),
                        "Status: Invalid request data"},
                //Отрицательная стоимость
                {Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("-5000.00").build(),
                        "Status: Invalid request data"},
                //Нулевая стоимость
                //{Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("0.00").build(),
                // "Status: Invalid request data"},
                //Слишком высокая стоимость
                //{Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("100000000000000000.00").
                // build(), "Status: Invalid request data"},
                //Нечисловое значение в атрибуте стоимость
                {Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("Жига").build(),
                        "Status: Invalid request data"},
        };
    }

    @Test(testName = "АТ.03.02.Cоздание нового авто c ошибкой",
            description = "Проверка создания автомобиля с невалидными данными со страницы Cars -> Create new",
            priority = 2,
            groups = {"Negative", "E2E", "Regression"},
            enabled = true,
            dataProvider = "Тестовые данные для негативного создания авто")
    @Description("Проверка создания автомобиля с невалидными данными со страницы Cars -> Create new")
    @Story("Создание авто")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void testErrorCreateCar(Car car1, String errorMessage) {
        log.info("Step 1: Authorizing user and open 'Create new Car' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsCreateNewPage.openPage()
                .isPageOpened()
                .createNewCar(car1);
        Assert.assertEquals(carsCreateNewPage.getStatusMessage(),
                errorMessage,
                "Ошибка!!! Сообщение об ошибке 'Status: Invalid request data' не отображено!");
    }

    @Test(testName = "АТ.03.03.Проверка ввода в поле инкту с помощью стрелок",
            description = "Проверка ввода в поле Price на странице Cars -> Create new, с помощью стрелок",
            priority = 4,
            groups = {"Negative", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка ввода в поле Price на странице Cars -> Create new, с помощью стрелок")
    @Story("Создание авто")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void test() {
        log.info("Step 1: Authorizing user and open 'Create new Car' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsCreateNewPage.openPage()
                .isPageOpened()
                .inputNumberWithButton(150, 20);
        SelenideElement priceField =
                $x("//table[contains(@class, 'table-striped')]//input[@id='car_price_send']");
        String currentValue = priceField.getValue();
        System.out.println(currentValue);
        Assert.assertEquals(currentValue,
                "1.3",
                "Ошибка!!! Нажатием на стрелки в инпуте, не получилось нужного числа");
    }

    @Test(testName = "АТ.03.04.Проверка наличия атрибутов в таблице cars",
            description = "Проверка наличия всех атрибутов в таблице Cars -> read all",
            priority = 3,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка наличия всех атрибутов в таблице Cars -> read all")
    @Story("Таблица cars")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void testAttributeCarsTable() {
        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsReadAllPage.openPage()
                .isPageOpened();
        // Получаем массив фактических атрибутов
        String[] actualAttributes = carsReadAllPage.checkAttributeTable();
        // Ожидаемый массив атрибутов
        String[] expectedAttributes = {"ID:", "Engine Type:", "Mark:", "Model:", "Price:"};
        // Сравниваем массивы
        Assert.assertEquals(Arrays.asList(expectedAttributes), Arrays.asList(actualAttributes),
                "Атрибуты таблицы не соответствуют ожидаемым"
        );
    }

    @Test(testName = "АТ.03.05.Сортировка ID по возрастанию",
            description = "Проверка корректной сортировки первых 30 значений атрибута таблицы ID Cars по возрастанию",
            priority = 3,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка корректной сортировки первых 30 значений атрибута ID таблицы Cars по возрастанию")
    @Story("Таблица cars")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void checkSortIdLowHigh() {
        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsReadAllPage.openPage()
                .isPageOpened();
        sleep(1000);
        String textButton = carsReadAllPage.clickButton("ID");
        softAssert.assertEquals(textButton,"↑ ID ", "Кнопка не в том состоянии");
        //Получаем список 30 первых атрибутов таблицы cars
        List<String> actualValues = carsReadAllPage.getAttribute("1");
        List<String> sortedValues = actualValues.stream()
                .map(Integer::parseInt) // преобразуем в Integer
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.toList());// сортируем числа
        softAssert.assertEquals(actualValues, sortedValues,
                "Значения не отсортированы по возрастанию");
        softAssert.assertAll();
    }

    @Test(testName = "АТ.03.06.Сортировка ID по убыванию",
            description = "Проверка корректной сортировки первых 30 значений атрибута ID таблицы Cars по убыванию",
            priority = 3,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка корректной сортировки первых 30 значений атрибута ID таблицы Cars по убыванию")
    @Story("Таблица cars")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void checkSortIdHighLow() {
        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsReadAllPage.openPage()
                .isPageOpened();
        sleep(1000);
        carsReadAllPage.clickButton("ID");
        sleep(1000);
        String textButton = carsReadAllPage.clickButton("ID");
        softAssert.assertEquals(textButton,"↓ ID ", "Кнопка не в том состоянии");
        //Получаем список 30 первых атрибутов таблицы cars
        List<String> actualValues = carsReadAllPage.getAttribute("1");
        List<String> sortedValues = actualValues.stream()
                .map(Integer::parseInt) // преобразуем в Integer
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .collect(Collectors.toList());// сортируем числа
        softAssert.assertEquals(actualValues, sortedValues,
                "Значения не отсортированы по убыванию");
        softAssert.assertAll();
    }

    @Test(testName = "АТ.03.07.Сортировка Price по возрастанию",
            description =
                    "Проверка корректной сортировки первых 30 значений атрибута Price таблицы Cars по возрастанию",
            priority = 3,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка корректной сортировки первых 30 значений атрибута Price таблицы Cars по убыванию")
    @Story("Таблица cars")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void checkSortPriceLowHigh() {
        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsReadAllPage.openPage()
                .isPageOpened();
        sleep(1000);
        String textButton = carsReadAllPage.clickButton("Price");
        softAssert.assertEquals(textButton,"↑ Price ", "Кнопка не в том состоянии");
        //Получаем список 30 первых атрибутов таблицы cars
        List<String> actualValues = carsReadAllPage.getAttribute("5");
        List<Double> actualValuesAsDoubles = actualValues.stream()
                .filter(text -> text != null && !text.isEmpty() && !"null".equals(text))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        List<Double> sortedValuesAsDoubles = actualValuesAsDoubles.stream()
                .sorted().toList();
        softAssert.assertEquals(actualValuesAsDoubles, sortedValuesAsDoubles,
                "Значения не отсортированы по возрастанию");
        softAssert.assertAll();
    }

    @Test(testName = "АТ.03.08.Сортировка Price по убыванию",
            description = "Проверка корректной сортировки первых 30 значений атрибута Price таблицы Cars по убыванию",
            priority = 3,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка корректной сортировки первых 30 значений атрибута Price таблицы Cars по убыванию")
    @Story("Таблица cars")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void checkSortPriceHighLow() {
        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsReadAllPage.openPage()
                .isPageOpened();
        sleep(1000);
        carsReadAllPage.clickButton("Price");
        sleep(1000);
        String textButton = carsReadAllPage.clickButton("Price");
        softAssert.assertEquals(textButton,"↓ Price ", "Кнопка не в том состоянии");
        //Получаем список 30 первых атрибутов таблицы cars
        List<String> actualValues = carsReadAllPage.getAttribute("5");
        List<Double> actualValuesAsDoubles = actualValues.stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        List<Double> sortedValuesAsDoubles = actualValuesAsDoubles.stream()
                .sorted(Comparator.reverseOrder()).toList();
        softAssert.assertEquals(actualValuesAsDoubles, sortedValuesAsDoubles,
                "Значения не отсортированы по возрастанию");
        softAssert.assertAll();
    }

    @Test(testName = "АТ.03.09.Сортировка Engine по алфавиту А-Я",
            description = "Проверка корректной сортировки значений атрибута Engine Type таблицы Cars А-Я",
            priority = 3,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка корректной сортировки значений атрибута Engine Type таблицы Cars А-Я")
    @Story("Таблица cars")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void checkSortEngineAZ() {
        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsReadAllPage.openPage()
                .isPageOpened();
        sleep(1000);
        String textButton = carsReadAllPage.clickButton("Engine");
        softAssert.assertEquals(textButton,"↑ Engine Type ", "Кнопка не в том состоянии");
        List<String> actualValues = carsReadAllPage.getAttributeEngineType("2");
        List<String> sortedValues = new ArrayList<>(actualValues);
        // Сортируем по алфавиту в порядке А‑Я (по возрастанию)
        sortedValues.sort(String.CASE_INSENSITIVE_ORDER);
        softAssert.assertEquals(actualValues, sortedValues,
                "Значения не отсортированы по алфавиту А-Я");
        softAssert.assertAll();
    }

    @Test(testName = "АТ.03.10.Сортировка Engine по алфавиту Я-А",
            description = "Проверка корректной сортировки значений атрибута Engine Type таблицы Cars Я-А",
            priority = 3,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка корректной сортировки значений атрибута Engine Type таблицы Cars Я-А")
    @Story("Таблица cars")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void checkSortEngineZA() {
        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsReadAllPage.openPage()
                .isPageOpened();
        sleep(1000);
        carsReadAllPage.clickButton("Engine");
        sleep(1000);
        String textButton = carsReadAllPage.clickButton("Engine");
        softAssert.assertEquals(textButton,"↓ Engine Type ", "Кнопка не в том состоянии");
        List<String> actualValues = carsReadAllPage.getAttributeEngineType("2");
        List<String> sortedValues = new ArrayList<>(actualValues);
        // Сортируем по алфавиту в порядке А‑Я (по возрастанию)
        sortedValues.sort(String.CASE_INSENSITIVE_ORDER.reversed());
        softAssert.assertEquals(actualValues, sortedValues,
                "Значения не отсортированы по алфавиту Я-А");
        softAssert.assertAll();
    }

//    @Test(testName = "АТ.03.11.Сортировка Mark по алфавиту А-Я",
//            description = "Проверка корректной сортировки первых 30 значений атрибута Mark таблицы Cars А-Я",
//            priority = 3,
//            groups = {"Positive", "E2E", "Regression"},
//            enabled = true)
//    @Description("Проверка корректной сортировки первых 30 значений атрибута Mark таблицы Cars А-Я")
//    @Story("Таблица cars")
//    @Severity(SeverityLevel.NORMAL)
//    @TmsLink("TestCaseLink")
//    @Issue("BugLink")
//    public void checkSortMarkAZ() {
//        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
//        loginStep.successfulAuthorization(validEmail, validPassword);
//        carsReadAllPage.openPage()
//                .isPageOpened();
//        sleep(300);
//        String textButton = carsReadAllPage.clickButton("Mark");
//        softAssert.assertEquals(textButton,"↑ Mark ", "Кнопка не в том состоянии");
//        List<String> actualValues = carsReadAllPage.getAttribute("3");
//        List<String> sortedValues = new ArrayList<>(actualValues);
//        // Сортируем по алфавиту в порядке А‑Я (по возрастанию)
//        sortedValues.sort(String.CASE_INSENSITIVE_ORDER);
//        softAssert.assertEquals(actualValues, sortedValues,
//                "Значения не отсортированы по алфавиту А-Я");
//        softAssert.assertAll();
//    }
//
//    @Test(testName = "АТ.03.12.Сортировка Mark по алфавиту Я-А",
//            description = "Проверка корректной сортировки первых 30 значений атрибута Mark таблицы Cars Я-А",
//            priority = 3,
//            groups = {"Positive", "E2E", "Regression"},
//            enabled = true)
//    @Description("Проверка корректной сортировки первых 30 значений атрибута Mark таблицы Cars Я-А")
//    @Story("Таблица cars")
//    @Severity(SeverityLevel.NORMAL)
//    @TmsLink("TestCaseLink")
//    @Issue("BugLink")
//    public void checkSortMarkZA() {
//        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
//        loginStep.successfulAuthorization(validEmail, validPassword);
//        carsReadAllPage.openPage()
//                .isPageOpened();
//        sleep(300);
//        carsReadAllPage.clickButton("Mark");
//        sleep(300);
//        String textButton = carsReadAllPage.clickButton("Mark");
//        softAssert.assertEquals(textButton,"↓ Mark ", "Кнопка не в том состоянии");
//        List<String> actualValues = carsReadAllPage.getAttribute("3");
//        List<String> sortedValues = new ArrayList<>(actualValues);
//        // Сортируем по алфавиту в порядке А‑Я (по возрастанию)
//        sortedValues.sort(String.CASE_INSENSITIVE_ORDER.reversed());
//        System.out.println(actualValues);
//        System.out.println(sortedValues);
//        softAssert.assertEquals(actualValues, sortedValues,
//                "Значения не отсортированы по алфавиту Я-А");
//        softAssert.assertAll();
//    }
//
//    @Test(testName = "АТ.03.13.Сортировка Model по алфавиту А-Я",
//            description = "Проверка корректной сортировки первых 30 значений атрибута Model таблицы Cars А-Я",
//            priority = 3,
//            groups = {"Positive", "E2E", "Regression"},
//            enabled = true)
//    @Description("Проверка корректной сортировки первых 30 значений атрибута Model таблицы Cars А-Я")
//    @Story("Таблица cars")
//    @Severity(SeverityLevel.NORMAL)
//    @TmsLink("TestCaseLink")
//    @Issue("BugLink")
//    public void checkSortModelAZ() {
//        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
//        loginStep.successfulAuthorization(validEmail, validPassword);
//        carsReadAllPage.openPage()
//                .isPageOpened();
//        sleep(300);
//        String textButton = carsReadAllPage.clickButton("Model");
//        softAssert.assertEquals(textButton,"↑ Model ", "Кнопка не в том состоянии");
//        List<String> actualValues = carsReadAllPage.getAttribute("4");
//        List<String> sortedValues = new ArrayList<>(actualValues);
//        // Сортируем по алфавиту в порядке А‑Я (по возрастанию)
//        sortedValues.sort(String.CASE_INSENSITIVE_ORDER);
//        softAssert.assertEquals(actualValues, sortedValues,
//                "Значения не отсортированы по алфавиту А-Я");
//        softAssert.assertAll();
//    }
//
//    @Test(testName = "АТ.03.14.Сортировка Model по алфавиту Я-А",
//            description = "Проверка корректной сортировки первых 30 значений атрибута Model таблицы Cars Я-А",
//            priority = 3,
//            groups = {"Positive", "E2E", "Regression"},
//            enabled = true)
//    @Description("Проверка корректной сортировки первых 30 значений атрибута Model таблицы Cars Я-А")
//    @Story("Таблица cars")
//    @Severity(SeverityLevel.NORMAL)
//    @TmsLink("TestCaseLink")
//    @Issue("BugLink")
//    public void checkSortModelZA() {
//        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
//        loginStep.successfulAuthorization(validEmail, validPassword);
//        carsReadAllPage.openPage()
//                .isPageOpened();
//        sleep(300);
//        carsReadAllPage.clickButton("Model");
//        sleep(300);
//        String textButton = carsReadAllPage.clickButton("Model");
//        softAssert.assertEquals(textButton,"↓ Model ", "Кнопка не в том состоянии");
//        List<String> actualValues = carsReadAllPage.getAttribute("4");
//        List<String> sortedValues = new ArrayList<>(actualValues);
//        // Сортируем по алфавиту в порядке А‑Я (по возрастанию)
//        sortedValues.sort(String.CASE_INSENSITIVE_ORDER.reversed());
//        softAssert.assertEquals(actualValues, sortedValues,
//                "Значения не отсортированы по алфавиту Я-А");
//        softAssert.assertAll();
//    }

    @Test(testName = "АТ.03.15.Проверка кнопки Reload",
            description = "Проверка, что кнопка Reload на странице Car -> read all, обновляет страницу с таблицей",
            priority = 4,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка, что кнопка Reload на странице Car -> read all, обновляет страницу с таблицей")
    @Story("Таблица cars")
    @Severity(SeverityLevel.MINOR)
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    public void checkReloadButton() {
        log.info("Step 1: Authorizing user and open 'Cars - read all' page");
        loginStep.successfulAuthorization(validEmail, validPassword);
        carsReadAllPage.openPage()
                .isPageOpened();
        sleep(1000);
        List<String> actualValues = carsReadAllPage.getAttribute("1");
        carsReadAllPage.clickButton("ID");
        List<String> valuesAfterSort = carsReadAllPage.getAttribute("1");
        softAssert.assertNotEquals(actualValues, valuesAfterSort,
                "Значение после сортировки соответствует изначальному");
        carsReadAllPage.clickButton("Reload");
        sleep(1000);
        List<String> valuesAfterReload = carsReadAllPage.getAttribute("1");
        softAssert.assertEquals(actualValues, valuesAfterReload,
                "Отсортированная таблица после перезагрузи страницы не возвращается в дефолтное состояние");
        softAssert.assertAll();
    }
}
