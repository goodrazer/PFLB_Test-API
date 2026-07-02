package ui.pages.users;

import api.adapters.users.UsersApiAdapter;
import api.models.users.PersonDto;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;
import ui.pages.base.BasePage;
import ui.wrappers.SortButton;
import utils.SortUtils;
import java.time.Duration;
import java.util.List;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class UsersReadAllPage extends BasePage {

    public String newUserId;
    private UsersApiAdapter usersApi;

    @Override
    @Step("Открытие страницы 'Users Read All Page'.")
    public UsersReadAllPage openPage() {
        log.info("Opening the 'UsersReadAllPage' start page");
        open(BASE_URL + "/#/read/users");
        return this;
    }

    @Step("Проверка наличия пользователя с ID = {userId} в таблице")
    public boolean isUserPresentById(String userId) {
        String xpath = String.format("//table//td[contains(text(), '%s')]", userId);
        SelenideElement cell = $x(xpath);
        try {
            cell.shouldBe(visible, Duration.ofSeconds(10));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Step("Подсчет количества пользователей в таблице")
    public UsersReadAllPage countNumberUsersInTable() {
        $$("tbody tr").shouldBe(CollectionCondition.sizeGreaterThan(0));
        int count = $$("tbody tr").size();
        log.info("Table download with {} users", count);
        return this;
    }

    @Step("Проверка открытия страницы 'Users Read All Page'.")
    public UsersReadAllPage isPageOpened() {
        log.info("Checking 'Users Read All Page' page is loaded");
        new SortButton("Reload").find();
        countNumberUsersInTable();
        return this;
    }

    @Step("Нажатие на кнопку сортировки {label}")
    public UsersReadAllPage sortButtonClick(String label) {
        new SortButton(label).find().click();
        log.info("Click sort button \"" + label + "\"");
        return this;
    }

    @Step("Нажатие на кнопку Reload")
    public UsersReadAllPage reloadButtonClick() {
        new SortButton("Reload").find().click();
        log.info("Click Reload button");
        sleep(500); // жду обновления таблицы
        countNumberUsersInTable();
        return this;
    }

    @Step("Проверка изменения цвета кнопки {label} с цвета {beforeColor} на цвет {afterColor}")
    public UsersReadAllPage checkChangeColorButton(String label, String beforeColor, String afterColor) {
        SelenideElement button = new SortButton(label).find();
        log.info("Check change color button \""
                + label + "\" from color \"" + beforeColor + "\" to color \"" + afterColor + "\"");
        button.shouldHave(cssValue("background-color", beforeColor));
        button.hover();
        button.shouldHave(cssValue("background-color", afterColor));
        return this;
    }

    // метод для сортировки по числовым данным
    @Step("Проверка сортировки числовых значений по {typeSort}")
    public UsersReadAllPage CheckSortByNumber(int ColumnNumber, String typeSort) {
        log.info("Check sort column №" + ColumnNumber);
        List<Double> listUsers = $$("tbody tr td:nth-child(" + ColumnNumber + ")")
                .texts()
                .stream()
                .map(s -> s.replace("\u00A0", " "))
                .map(String::trim)
                .map(Double::parseDouble)
                .toList();
        if (typeSort.equals("возрастанию")) {
            for (int i = 1; i < listUsers.size(); i++) {
                Assert.assertTrue(listUsers.get(i - 1) <= listUsers.get(i));
            }
        } else if (typeSort.equals("убыванию")) {
            for (int i = 1; i < listUsers.size(); i++) {
                Assert.assertTrue(listUsers.get(i - 1) >= listUsers.get(i));
            }
        }
        return this;
    }

    // метод для сортировки по строковым данным
    @Step("Проверка сортировки строковых данных по {typeSort}")
    public UsersReadAllPage CheckSortByString(int ColumnNumber, String typeSort) {
        log.info("Check sort column №" + ColumnNumber);
        // получаем значения из указанного столбца таблицы
        List<String> listUsers = $$("tbody tr td:nth-child(" + ColumnNumber + ")")
                .texts()
                .stream()
                // заменяем неразрывные пробелы
                .map(s -> s.replace("\u00A0", " "))
                // убираем лишние пробелы по краям
                .map(String::trim)
                .toList();
        if (typeSort.equals("возрастанию")) {
            for (int i = 1; i < listUsers.size(); i++) {
                String previousElement = listUsers.get(i - 1);
                String currentElement = listUsers.get(i);
                Assert.assertTrue(
                        SortUtils.customStringComparator.compare(previousElement, currentElement) <= 0,
                        "Сортировка по возрастанию упала на элементе № " + i +
                                " | prev='" + previousElement + "'" +
                                " | current='" + currentElement + "'");
            }
        } else if (typeSort.equals("убыванию")) {
            for (int i = 1; i < listUsers.size(); i++) {
                String previousElement = listUsers.get(i - 1);
                String currentElement = listUsers.get(i);
                Assert.assertTrue(
                        SortUtils.customStringComparator.compare(previousElement, currentElement) >= 0,
                        "Сортировка по убыванию упала на элементе № " + i +
                                " | prev='" + previousElement + "'" +
                                " | current='" + currentElement + "'");
            }
        }
        return this;
    }

    @Step("Поиск пользователя в таблице по данным: Id = '{id}', First Name = '{firstName}', Last Name = '{lastName}'," +
            " Age = '{age}', Sex = '{sex}', Money = '{money}'")
    public UsersReadAllPage checkUserInTable(
            String id,
            String firstName,
            String lastName,
            String age,
            String sex,
            String money,
            boolean shouldExist
    ) {
        log.info("Check that user exist in the table with data: " + (id != null ? "Id = \"" + id + "\", " : "")
                + "First Name = \"" + firstName + "\"," + " Last Name = \"" + lastName + "\", Age = \"" + age
                + "\", Sex = \"" + sex + "\", Money = \"" + money + "\"");
        // загружаем данные из таблицы
        List<String> ids = $$("tbody tr td:nth-child(1)").texts();
        List<String> firstNames = $$("tbody tr td:nth-child(2)").texts();
        List<String> lastNames = $$("tbody tr td:nth-child(3)").texts();
        List<String> ages = $$("tbody tr td:nth-child(4)").texts();
        List<String> sexes = $$("tbody tr td:nth-child(5)").texts();
        List<String> moneys = $$("tbody tr td:nth-child(6)").texts();
        // поиск пользователями соответствующего введенным данным в метод
        boolean found = false;
        for (int i = 0; i < firstNames.size(); i++) {
            boolean match = firstNames.get(i).equals(firstName) &&
                    lastNames.get(i).equals(lastName) &&
                    ages.get(i).equals(age) &&
                    sexes.get(i).equals(sex) &&
                    moneys.get(i).equals(money);
            // если передан ай-ди, то ищем и по нему
            if (id != null) {
                match = match && ids.get(i).equals(id);
            }
            // если все данные совпали присваем переменной found значение true
            if (match) {
                found = true;
                log.info("Found user in the table with data: " + (id != null ? "Id = \"" + id + "\", " : "")
                        + "First Name = \"" + firstName + "\"," + " Last Name = \"" + lastName + "\", Age = \"" + age
                        + "\", Sex = \"" + sex + "\", Money = \"" + money + "\"");
                break;
            }
        }
        // если совпадений не найдено пишем об этом в лог
        if (!found) {
            log.info("User not exist in the table with data: " + (id != null ? "Id = \"" + id + "\", " : "")
                    + "First Name = \"" + firstName + "\"," + " Last Name = \"" + lastName + "\", Age = \"" + age
                    + "\", Sex = \"" + sex + "\", Money = \"" + money + "\"");
        }
        // проверка результата поиска
        if (shouldExist) {
            // если пользователь должен быть найден
            Assert.assertTrue(found, "В таблице не найден пользователь с данными: " + (id != null ? "Id = \""
                    + id + "\", " : "") + "First Name = \"" + firstName + "\"," + " Last Name = \"" + lastName
                    + "\", Age = \"" + age + "\", Sex = \"" + sex + "\", Money = \"" + money + "\"");
        } else {
            // если пользователь не должен быть найден
            Assert.assertFalse(found, "В таблице найден пользователь с данными: " + (id != null ? "Id = \""
                    + id + "\", " : "") + "First Name = \"" + firstName + "\"," + " Last Name = \"" + lastName
                    + "\", Age = \"" + age + "\", Sex = \"" + sex + "\", Money = \"" + money + "\"");
        }
        return this;
    }

    @Step("Создание нового пользователя через API с данными: First Name = '{firstName}', Last Name = '{lastName}'," +
            " Age = '{age}', Sex = '{sex}', Money = '{money}'")
    public UsersReadAllPage createUserWithApiAccess(
            String login,
            String password,
            String firstName,
            String lastName,
            String age,
            String sex,
            String money
    ) {
        // Создаём адаптер
        usersApi = new UsersApiAdapter();
        // Авторизуемся
        usersApi.loginAsJson(login, password);
        // Создаём DTO пользователя
        PersonDto newUser = PersonDto.builder()
                .firstName(firstName)
                .secondName(lastName)
                .age(Integer.parseInt(age))
                .sex(sex)
                .money(Double.parseDouble(money))
                .build();
        // создание нового пользователя
        Response newUserResponse = usersApi.createUser(newUser);
        newUserId = newUserResponse.jsonPath().getString("id");
        log.info("New user has been created with id = \"" + newUserId + "\"");
        return this;
    }
}
