package ui.pages.users;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import ui.pages.BasePage;
import ui.wrappers.SortButton;
import java.util.List;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.*;

public class UsersReadUserWithCarsPage extends BasePage {

    public final SelenideElement input = $("#user_input");
    private final SelenideElement status = $("button.status");
    private final SelenideElement inputButton = $("#user_input").parent();

    @Override
    @Step("Открытие страницы 'Users Read User With Cars Page'.")
    public UsersReadUserWithCarsPage openPage() {
        log.info("Opening the 'UsersReadUserWithCarsPage' start page");
        open(BASE_URL + "/#/read/userInfo");
        return this;
    }

    @Step("Проверка открытия страницы 'Users Read User With Cars Page'.")
    public UsersReadUserWithCarsPage isPageOpened() {
        log.info("Checking 'Users Read User With Cars Page' page is loaded");
        new SortButton("Read").find();
        return this;
    }

    @Step("Проверка наличия инпута, кнопки чтения и статуса на странице.")
    public UsersReadUserWithCarsPage checkElements() {
        log.info("Check exist input on page");
        input.shouldBe(Condition.visible);
        new SortButton("Read").find();
        log.info("Check exist status on page");
        status.shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверка изменения цвета кнопки {label} с цвета {beforeColor} на цвет {afterColor}")
    public UsersReadUserWithCarsPage checkChangeColorButton(String label, String beforeColor, String afterColor) {
        SelenideElement button = new SortButton(label).find();
        log.info("Check change color button \""
                + label + "\" from color \"" + beforeColor + "\" to color \"" + afterColor + "\"");
        button.shouldHave(cssValue("background-color", beforeColor));
        button.hover();
        button.shouldHave(cssValue("background-color", afterColor));
        return this;
    }

    @Step("Проверка изменения цвета кнопки Input с цвета {beforeColor} на цвет {afterColor}")
    public UsersReadUserWithCarsPage checkChangeColorInputButton(String beforeColor, String afterColor) {
        log.info("Check change color Input button from color \"{}\" to color \"{}\"",
                beforeColor, afterColor);
        inputButton.shouldHave(cssValue("background-color", beforeColor));
        inputButton.hover();
        inputButton.shouldHave(cssValue("background-color", afterColor));
        return this;
    }

    @Step("Проверка появления тултипа у инпута")
    public UsersReadUserWithCarsPage checkVisibleTooltipInput() {
        input.click();
        input.setValue(",");
        $("body").click();
        // получаем текст тултипа
        String validationMessage = executeJavaScript("return arguments[0].validationMessage;", input);
        log.info("Visible tooltip with text \"" + validationMessage + "\"");
        // сравниваем полученное сообщение с ожидаемым
        Assert.assertEquals(validationMessage, "Please enter a number.");
        log.info("Check text tooltip success");
        return this;
    }

    @Step("Проверка ввода ай-ди в инпут напрямую и через стрелочки")
    public UsersReadUserWithCarsPage checkEnterInputId(String id) {
        log.info("Check input ID directly and with use arrows");
        log.info("Enter input ID =\"" + id + "\"");
        input.setValue(id);
        log.info("Click button 'Arrow UP'");
        input.sendKeys(Keys.ARROW_UP);
        log.info("Check that ID has increased by one");
        Assert.assertEquals(
                input.getValue(),
                String.valueOf(Integer.parseInt(id) + 1));
        log.info("ID increased by one and equals =\"" + input.getValue() + "\"");
        log.info("Click button 'Arrow DOWN'");
        input.sendKeys(Keys.ARROW_DOWN);
        log.info("Check that ID has decreased by one");
        Assert.assertEquals(
                input.getValue(),
                id);
        log.info("ID decreased by one and equals =\"" + input.getValue() + "\"");
        return this;
    }

    @Step("Нажатие на кнопку Read")
    public UsersReadUserWithCarsPage readButtonClick() {
        new SortButton("Read").find().click();
        log.info("Click Read button");
        return this;
    }

    @Step("Вызов данных по пользователю с id = {userId}")
    public UsersReadUserWithCarsPage callDataNewUser(String userId) {
        log.info("Call data for user with id = \"" + userId + "\"");
        input.setValue(userId);
        readButtonClick();
        sleep(400); // жду обновления таблицы
        return this;
    }

    @Step("Поиск пользователя в таблице по данным: Id = '{id}', First Name = '{firstName}'," +
            " Last Name = '{lastName}', Age = '{age}', Sex = '{sex}', Money = '{money}', Cars = '{numberCars}'")
    public UsersReadUserWithCarsPage checkUserInTable(
            String id,
            String firstName,
            String lastName,
            String age,
            String sex,
            String money,
            String numberCars,
            boolean shouldExist
    ) {
        log.info("Check that user exist in the table with data: "
                + (id != null ? "Id = \"" + id + "\", " : "")
                + "First Name = \"" + firstName
                + "\", Last Name = \"" + lastName
                + "\", Age = \"" + age +
                "\", Sex = \"" + sex +
                "\", Money = \"" + money
                + "\"" + (numberCars != null ? ", Cars = \"" + numberCars + "\"" : ""));
        // загружаем данные из таблицы
        List<String> ids = $$("table.tableUser tbody tr td:nth-child(1)").texts();
        List<String> firstNames = $$("table.tableUser tbody tr td:nth-child(2)").texts();
        List<String> lastNames = $$("table.tableUser tbody tr td:nth-child(3)").texts();
        List<String> ages = $$("table.tableUser tbody tr td:nth-child(4)").texts();
        List<String> sexes = $$("table.tableUser tbody tr td:nth-child(5)").texts();
        List<String> moneys = $$("table.tableUser tbody tr td:nth-child(6)").texts();
        List<String> cars = $$("table.tableUser tbody tr td:nth-child(7)").texts();
        // поиск пользователя соответствующего введенным данным в метод
        boolean found = false;
        for (int i = 0; i < ids.size(); i++) {
            boolean match = ids.get(i).equals(id) &&
                    firstNames.get(i).equals(firstName) &&
                    lastNames.get(i).equals(lastName) &&
                    ages.get(i).equals(age) &&
                    sexes.get(i).equals(sex) &&
                    moneys.get(i).equals(money);
            // если передан ай-ди, то ищем и по нему
            if (numberCars == null) {
                // если передан null, то в таблице ищем в столбце Cars пустое значение
                match = match && cars.get(i).isBlank();
            } else {
                // иначе сравниваем с ожидаемым значением
                match = match && cars.get(i).equals(numberCars);
            }
            // если все данные совпали присваем переменной found значение true
            if (match) {
                found = true;
                log.info("Found user in the table with data: "
                        + (id != null ? "Id = \"" + id + "\", " : "")
                        + "First Name = \"" + firstName
                        + "\", Last Name = \"" + lastName
                        + "\", Age = \"" + age +
                        "\", Sex = \"" + sex +
                        "\", Money = \"" + money
                        + "\"" + (numberCars != null ? ", Cars = \"" + numberCars + "\"" : ""));
                break;
            }
        }
        // если совпадений не найдено пишем об этом в лог
        if (!found) {
            log.info("User not exist in the table with data: "
                    + (id != null ? "Id = \"" + id + "\", " : "")
                    + "First Name = \"" + firstName
                    + "\", Last Name = \"" + lastName
                    + "\", Age = \"" + age +
                    "\", Sex = \"" + sex +
                    "\", Money = \"" + money
                    + "\"" + (numberCars != null ? ", Cars = \"" + numberCars + "\"" : ""));
        }
        // проверка результата поиска
        if (shouldExist) {
            // если пользователь должен быть найден
            Assert.assertTrue(found, "В таблице не найден пользователь с данными: "
                    + (id != null ? "Id = \"" + id + "\", " : "")
                    + "First Name = \"" + firstName
                    + "\", Last Name = \"" + lastName
                    + "\", Age = \"" + age +
                    "\", Sex = \"" + sex +
                    "\", Money = \"" + money
                    + "\"" + (numberCars != null ? ", Cars = \"" + numberCars + "\"" : ""));
        } else {
            // если пользователь не должен быть найден
            Assert.assertFalse(found, "В таблице найден пользователь с данными: "
                    + (id != null ? "Id = \"" + id + "\", " : "")
                    + "First Name = \"" + firstName
                    + "\", Last Name = \"" + lastName
                    + "\", Age = \"" + age +
                    "\", Sex = \"" + sex +
                    "\", Money = \"" + money
                    + "\"" + (numberCars != null ? ", Cars = \"" + numberCars + "\"" : ""));
        }
        return this;
    }

    @Step("Поиск автомобиля в таблице по данным: Id = '{id}', Engine Name = '{engineType}'," +
            " Mark = '{mark}', Model = '{model}', Price = '{price}'")
    public UsersReadUserWithCarsPage checkCarsInTable(
            String id,
            String engineType,
            String mark,
            String model,
            String price,
            boolean shouldExist
    ) {
        log.info("Check that car exist in the table with data: Id = \"" + id
                + "\", Engine Type = \"" + engineType + "\", Mark = \"" + mark + "\", Model = \"" + model
                + "\", Price = \"" + price + "\"");
        // загружаем данные из таблицы
        List<String> ids = $$("table.tableCars tbody tr td:nth-child(1)").texts();
        List<String> engineTypes = $$("table.tableCars tbody tr td:nth-child(2)").texts();
        List<String> marks = $$("table.tableCars tbody tr td:nth-child(3)").texts();
        List<String> models = $$("table.tableCars tbody tr td:nth-child(4)").texts();
        List<String> prices = $$("table.tableCars tbody tr td:nth-child(5)").texts();
        // поиск автомобиля соответствующего введенным данным в метод
        boolean found = false;
        for (int i = 0; i < ids.size(); i++) {
            boolean match = ids.get(i).equals(id) &&
                    engineTypes.get(i).equals(engineType) &&
                    marks.get(i).equals(mark) &&
                    models.get(i).equals(model) &&
                    prices.get(i).equals(price);
            // если все данные совпали присваем переменной found значение true
            if (match) {
                found = true;
                log.info("Found car in the table with data: Id = \"" + id
                        + "\", Engine Type = \"" + engineType + "\", Mark = \"" + mark + "\", Model = \"" + model
                        + "\", Price = \"" + price + "\"");
                break;
            }
        }
        // если совпадений не найдено пишем об этом в лог
        if (!found) {
            log.info("Car not exist in the table with data: Id = \"" + id
                    + "\", Engine Type = \"" + engineType + "\", Mark = \"" + mark + "\", Model = \"" + model
                    + "\", Price = \"" + price + "\"");
        }
        // проверка результата поиска
        if (shouldExist) {
            // если машина должна быть найдена
            Assert.assertTrue(found, "В таблице не найден автомобиль с данными: Id = \"" + id
                    + "\", Engine Type = \"" + engineType + "\", Mark = \"" + mark + "\", Model = \"" + model
                    + "\", Price = \"" + price + "\"");
        } else {
            // если машина не должна быть найдена
            Assert.assertFalse(found, "В таблице найден автомобиль с данными: Id = \"" + id
                    + "\", Engine Type = \"" + engineType + "\", Mark = \"" + mark + "\", Model = \"" + model
                    + "\", Price = \"" + price + "\"");
        }
        return this;
    }

    @Step("Проверка статуса \"{expectedStatus}\"")
    public UsersReadUserWithCarsPage checkStatus(String expectedStatus) {
        log.info("Check status is \"{}\"", expectedStatus);
        status.shouldHave(Condition.text(expectedStatus));
        log.info("Status =\"" + expectedStatus + "\"");
        return this;
    }

    @Step("Проверка, что таблица с {tableName} пуста")
    public UsersReadUserWithCarsPage checkTableIsEmpty(String tableClass, String tableName) {
        if (tableClass.equals("tableUser")) {
            log.info("Check that user table is empty");
        } else if (tableClass.equals("tableCars")) {
            log.info("Check that cars table is empty");
        }
        // получение данных из столбца айди
        List<String> ids = $$("table." + tableClass + " tbody tr td:nth-child(1)").texts();
        // проверка, что список ids пустой
        Assert.assertTrue(ids.isEmpty(), "Таблица " + tableName + " не пуста!");
        if (tableClass.equals("tableUser")) {
            log.info("User table is empty");
        } else if (tableClass.equals("tableCars")) {
            log.info("Cars table is empty");
        }
        return this;
    }
}