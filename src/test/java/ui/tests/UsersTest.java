package ui.tests;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ui.wrappers.SortButton;
import ui.wrappers.TableColumn;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Log4j2
@Epic("Users")
public class UsersTest extends BaseTest {

    public static String NEW_CAR_ID = "774466";
    public static String NEW_CAR_ENGINE_TYPE = "PHEV";
    public static String NEW_CAR_MARK = "Известная";
    public static String NEW_CAR_MODEL = "Популярная";
    public static String NEW_CAR_PRICE = "6";
    private final String NEW_USER_ID = "9988775";
    private final String NEW_USER_FIRST_NAME = "A";
    private final String NEW_USER_LAST_NAME = "B";
    private final String NEW_USER_AGE = "10";
    private final String NEW_USER_SEX = "MALE";
    private final String NEW_USER_MONEY = "52";

    @Test(testName = "АТ.05.01. Проверка открытия страницы \"Users -> Read all\" со всеми атрибутами",
            description = "Проверка открытия страницы \"Users -> Read all\" со всеми атрибутами",
            priority = 1,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка открытия страницы \"Users -> Read all\" со всеми атрибутами")
    @Feature("Страница со всеми пользователями")
    @Story("Открытие страницы со всеми пользователями")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkOpenUsersReadAllPage() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened();
        // проверка наличия на странице кнопок сортировки
        new SortButton("Reload").find();
        new SortButton("Sort by:").find();
        new SortButton("ID").find();
        new SortButton("First Name").find();
        new SortButton("Last Name").find();
        new SortButton("Age").find();
        new SortButton("Sex").find();
        new SortButton("Money").find();
        // проверка наличия на странице таблицы со всеми столбцами
        new TableColumn("ID:").find();
        new TableColumn("First name:").find();
        new TableColumn("Last name:").find();
        new TableColumn("Age:").find();
        new TableColumn("Sex:").find();
        new TableColumn("Money:").find();
    }

    @Test(testName = "АТ.05.02. Проверка ховер эффекта кнопок",
            description = "Проверка ховер эффекта кнопок",
            priority = 1,
            groups = {"Positive", "Regression"},
            enabled = true)
    @Description("Проверка ховер эффекта кнопок")
    @Feature("Страница со всеми пользователями")
    @Story("Проверка ховер эффекта кнопок")
    @Severity(SeverityLevel.MINOR)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkHoverEffect() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .checkChangeColorButton("Reload", "rgb(13, 110, 253)", "rgb(11, 94, 215)")
                .checkChangeColorButton("Sort by:", "rgb(108, 117, 125)", "rgb(108, 117, 125)")
                .checkChangeColorButton("ID", "rgb(108, 117, 125)", "rgb(92, 99, 106)")
                .checkChangeColorButton("First Name", "rgb(108, 117, 125)", "rgb(92, 99, 106)")
                .checkChangeColorButton("Last Name", "rgb(108, 117, 125)", "rgb(92, 99, 106)")
                .checkChangeColorButton("Age", "rgb(108, 117, 125)", "rgb(92, 99, 106)")
                .checkChangeColorButton("Sex", "rgb(108, 117, 125)", "rgb(92, 99, 106)")
                .checkChangeColorButton("Money", "rgb(108, 117, 125)", "rgb(92, 99, 106)");
    }

    @Test(testName = "АТ.05.03. Проверка сортировки пользователей по возрастанию ID",
            description = "Проверка сортировки пользователей по возрастанию ID",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по возрастанию ID")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по возрастанию ID")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByIDIncrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("ID");
        // проверка появления стрелки вверх у кнопки сортировки по ID
        new SortButton("↑ ID").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByNumber(1, "возрастанию");
    }

    @Test(testName = "АТ.05.04. Проверка сортировки пользователей по убыванию ID",
            description = "Проверка сортировки пользователей по убыванию ID",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по убыванию ID")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по убыванию ID")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByIDDecrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("ID")
                .sortButtonClick("↑ ID");
        // проверка появления стрелки вниз у кнопки сортировки по ID
        new SortButton("↓ ID").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByNumber(1, "убыванию");
    }

    @Test(testName = "АТ.05.05. Проверка сортировки пользователей по имени от A-Z",
            description = "Проверка сортировки пользователей по имени от A-Z",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по имени от A-Z")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по имени от A-Z")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByFirstNameIncrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("First Name");
        // проверка появления стрелки вверх у кнопки сортировки по ID
        new SortButton("↑ First Name").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByString(2, "возрастанию");
    }

    @Test(testName = "АТ.05.06. Проверка сортировки пользователей по имени от Z-A",
            description = "Проверка сортировки пользователей по имени от Z-A",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по имени от Z-A")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по имени от Z-A")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByFirstNameDecrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("First Name")
                .sortButtonClick("↑ First Name");
        // проверка появления стрелки вниз у кнопки сортировки по ID
        new SortButton("↓ First Name").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByString(2, "убыванию");
    }

    @Test(testName = "АТ.05.07. Проверка сортировки пользователей по фамилии от A-Z",
            description = "Проверка сортировки пользователей по фамилии от A-Z",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по фамилии от A-Z")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по фамилии от A-Z")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByLastNameIncrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("Last Name");
        // проверка появления стрелки вверх у кнопки сортировки по ID
        new SortButton("↑ Last Name").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByString(3, "возрастанию");
    }

    @Test(testName = "АТ.05.08. Проверка сортировки пользователей по фамилии от Z-A",
            description = "Проверка сортировки пользователей по фамилии от Z-A",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по фамилии от Z-A")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по фамилии от Z-A")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByLastNameDecrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("Last Name")
                .sortButtonClick("↑ Last Name");
        // проверка появления стрелки вниз у кнопки сортировки по ID
        new SortButton("↓ Last Name").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByString(3, "убыванию");
    }

    @Test(testName = "АТ.05.09. Проверка сортировки пользователей по возрастанию возраста",
            description = "Проверка сортировки пользователей по возрастанию возраста",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по возрастанию возраста")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по возрастанию возраста")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByAgeIncrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("Age");
        // проверка появления стрелки вверх у кнопки сортировки по ID
        new SortButton("↑ Age").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByNumber(4, "возрастанию");
    }

    @Test(testName = "АТ.05.10. Проверка сортировки пользователей по убыванию возраста",
            description = "Проверка сортировки пользователей по убыванию возраста",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по убыванию возраста")
    @Epic("")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по убыванию возраста")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByAgeDecrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("Age")
                .sortButtonClick("↑ Age");
        // проверка появления стрелки вниз у кнопки сортировки по ID
        new SortButton("↓ Age").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByNumber(4, "убыванию");
    }

    @Test(testName = "АТ.05.11. Проверка сортировки пользователей по полу от A-Z",
            description = "Проверка сортировки пользователей по полу от A-Z",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по полу от A-Z")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по полу от A-Z")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortBySexIncrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("Sex");
        // проверка появления стрелки вверх у кнопки сортировки по ID
        new SortButton("↑ Sex").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByString(5, "возрастанию");
    }

    @Test(testName = "АТ.05.12. Проверка сортировки пользователей по полу от Z-A",
            description = "Проверка сортировки пользователей по полу от Z-A",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по полу от Z-A")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по полу от Z-A")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortBySexDecrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("Sex")
                .sortButtonClick("↑ Sex");
        // проверка появления стрелки вниз у кнопки сортировки по ID
        new SortButton("↓ Sex").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByString(5, "убыванию");
    }

    @Test(testName = "АТ.05.13. Проверка сортировки пользователей по возрастанию наличности",
            description = "Проверка сортировки пользователей по возрастанию наличности",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по возрастанию наличности")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по возрастанию наличности")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByMoneyIncrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("Money");
        // проверка появления стрелки вверх у кнопки сортировки по ID
        new SortButton("↑ Money").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByNumber(6, "возрастанию");
    }

    @Test(testName = "АТ.05.14. Проверка сортировки пользователей по убыванию наличности",
            description = "Проверка сортировки пользователей по убыванию наличности",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка сортировки пользователей по убыванию наличности")
    @Feature("Страница со всеми пользователями")
    @Story("Сортировка пользователей по убыванию наличности")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkSortByMoneyDecrease() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                .sortButtonClick("Money")
                .sortButtonClick("↑ Money");
        // проверка появления стрелки вниз у кнопки сортировки по ID
        new SortButton("↓ Money").find();
        // проверка сортировки
        usersReadAllPage.CheckSortByNumber(6, "убыванию");
    }

    @Test(testName = "АТ.05.15. Проверка обновления таблицы",
            description = "Проверка обновления таблицы",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка обновления таблицы")
    @Feature("Страница со всеми пользователями")
    @Story("Проверка обновления таблицы")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkReloadButton() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadAllPage.openPage()
                .isPageOpened()
                // проверка, что будущего пользователя нет в таблице
                .checkUserInTable(null,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_AGE,
                        NEW_USER_SEX,
                        NEW_USER_MONEY,
                        false);
        // авторизация на прямую в API для создания нового пользователя не уходя со страницы со всеми пользователями
        log.info("Authorization in API");
        Response loginResponse = given()
                .baseUri("http://82.142.167.37:4879")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", "admin@pflb.ru")
                .formParam("password", "admin")
                .when()
                .post("/login")
                .then()
                .statusCode(202)
                .extract()
                .response();
        String accessToken = loginResponse.jsonPath().getString("access_token");
        // создание нового пользователя
        log.info("Create new user with data: First Name = \"" + NEW_USER_FIRST_NAME + "\"," +
                " Last Name = \"" + NEW_USER_LAST_NAME + "\", Age = \"" + NEW_USER_AGE + "\", Sex = \"" + NEW_USER_SEX +
                "\", Money = \"" + NEW_USER_MONEY + "\"; in API");
        Map<String, Object> body = new HashMap<>();
        body.put("id", Integer.parseInt(NEW_USER_ID));
        body.put("firstName", NEW_USER_FIRST_NAME);
        body.put("secondName", NEW_USER_LAST_NAME);
        body.put("age", Integer.parseInt(NEW_USER_AGE));
        body.put("sex", NEW_USER_SEX);
        body.put("money", Integer.parseInt(NEW_USER_MONEY));
        Response newUserResponse = given()
                .baseUri("http://82.142.167.37:4879")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .post("/user")
                .then()
                .statusCode(201)
                .extract()
                .response();
        String newUserId = newUserResponse.jsonPath().getString("id");
        // нажатие на кнопку обновления таблицы
        usersReadAllPage.reloadButtonClick();
        // проверка, что новый пользователь есть в таблице
        usersReadAllPage.checkUserInTable(newUserId,
                NEW_USER_FIRST_NAME,
                NEW_USER_LAST_NAME,
                NEW_USER_AGE,
                NEW_USER_SEX,
                NEW_USER_MONEY,
                true);
    }

    @Test(testName = "АТ.06.01. Проверка открытия страницы \"Users -> Read user with cars\" со всеми атрибутами",
            description = "Проверка открытия страницы \"Users -> Read user with cars\" со всеми атрибутами",
            priority = 1,
            groups = {"Positive", "E2E", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка открытия страницы \"Users -> Read user with cars\" со всеми атрибутами")
    @Feature("Страница по получению данных по пользователям с машинами")
    @Story("Открытия страницы \"Users -> Read user with cars\" со всеми атрибутами")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkOpenUsersReadUserWithCarsPage() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadUserWithCarsPage.openPage()
                .isPageOpened()
                // проверка наличия на странице инпута, кнопки и поля статуса
                .checkElements();
        // проверка наличия на странице таблицы пользователя со всеми столбцами
        new TableColumn("ID:").find();
        new TableColumn("First name:").find();
        new TableColumn("Last name:").find();
        new TableColumn("Age:").find();
        new TableColumn("Sex:").find();
        new TableColumn("Money:").find();
        new TableColumn("Cars:").find();
        // проверка наличия на странице таблицы c машинами со всеми столбцами
        new TableColumn("ID:").find();
        new TableColumn("Engine Type:").find();
        new TableColumn("Mark:").find();
        new TableColumn("Model:").find();
        new TableColumn("Price:").find();
    }

    @Test(testName = "АТ.06.02. Проверка ввода в инпут айди напрямую и через стрелочки",
            description = "Проверка ввода в инпут айди напрямую и через стрелочки",
            priority = 1,
            groups = {"Positive", "Regression", "Smoke"},
            enabled = true)
    @Description("Проверка ввода в инпут айди напрямую и через стрелочки")
    @Feature("Страница по получению данных по пользователям с машинами")
    @Story("Ввод в инпут айди напрямую и через стрелочки")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkIdEnterInputAndWithArrow() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadUserWithCarsPage.openPage()
                .isPageOpened()
                .checkEnterInputId(NEW_USER_ID);
    }

    @Test(testName = "АТ.06.03. Проверка ховер-эффектов и тултипов",
            description = "Проверка ховер-эффектов и тултипов",
            priority = 1,
            groups = {"Positive", "Regression"},
            enabled = true)
    @Description("Проверка ховер-эффектов и тултипов")
    @Feature("Страница по получению данных по пользователям с машинами")
    @Story("Ховер-эффекты и тултипы на странице")
    @Severity(SeverityLevel.MINOR)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkHoverEffectAndTooltip() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadUserWithCarsPage.openPage()
                .isPageOpened()
                .checkChangeColorButton("Read", "rgb(13, 110, 253)", "rgb(11, 94, 215)")
                .checkChangeColorButton("Status", "rgb(108, 117, 125)", "rgb(108, 117, 125)")
                .checkChangeColorInputButton("rgb(108, 117, 125)", "rgb(92, 99, 106)");
        usersReadUserWithCarsPage.checkVisibleTooltipInput();
    }

    @Test(testName = "АТ.06.04. Проверка отображения данных по пользователю без машины",
            description = "Проверка отображения данных по пользователю без машины",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка отображения данных по пользователю без машины")
    @Feature("Страница по получению данных по пользователям с машинами")
    @Story("Отображение данных по пользователю без машины")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkDataInTableUserWithoutCar() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadUserWithCarsPage.openPage()
                .isPageOpened();
        /* авторизация на прямую в API для создания нового пользователя не уходя со страницы по получению данных
        по пользователям с машинами
        */
        log.info("Authorization in API");
        Response loginResponse = given()
                .baseUri("http://82.142.167.37:4879")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", "admin@pflb.ru")
                .formParam("password", "admin")
                .when()
                .post("/login")
                .then()
                .statusCode(202)
                .extract()
                .response();
        String accessToken = loginResponse.jsonPath().getString("access_token");
        // создание пользователя
        log.info("Create new user with data: First Name = \"" + NEW_USER_FIRST_NAME + "\"," +
                " Last Name = \"" + NEW_USER_LAST_NAME + "\", Age = \"" + NEW_USER_AGE + "\", Sex = \"" + NEW_USER_SEX +
                "\", Money = \"" + NEW_USER_MONEY + "\"; in API");
        Map<String, Object> body = new HashMap<>();
        body.put("id", Integer.parseInt(NEW_USER_ID));
        body.put("firstName", NEW_USER_FIRST_NAME);
        body.put("secondName", NEW_USER_LAST_NAME);
        body.put("age", Integer.parseInt(NEW_USER_AGE));
        body.put("sex", NEW_USER_SEX);
        body.put("money", Integer.parseInt(NEW_USER_MONEY));
        Response newUserResponse = given()
                .baseUri("http://82.142.167.37:4879")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .post("/user")
                .then()
                .statusCode(201)
                .extract()
                .response();
        String newUserId = newUserResponse.jsonPath().getString("id");
        log.info("New user has been created with id = \"" + newUserId + "\"");
        // вызов данных по новому пользователю без автомобилей
        usersReadUserWithCarsPage.callDataNewUser(newUserId)
                // проверка появления сообщения "Status: 200 ok"
                .checkStatus("Status: 200 ok")
                // проверка, что все данные по новому пользователю есть в таблице
                .checkUserInTable(newUserId,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_AGE,
                        NEW_USER_SEX,
                        NEW_USER_MONEY,
                        null,
                        true)
                // проверка, что таблица с машинами пустая
                .checkTableIsEmpty("tableCars", "машинами");
    }

    @Test(testName = "АТ.06.05. Проверка отображения данных по пользователю c одной машиной",
            description = "Проверка отображения данных по пользователю c одной машиной",
            priority = 1,
            groups = {"Positive", "E2E", "Regression"},
            enabled = true)
    @Description("Проверка отображения данных по пользователю c одной машиной")
    @Feature("Страница по получению данных по пользователям с машинами")
    @Story("Отображение данных по пользователю c одной машиной")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkDataInTableUserWithOneCar() {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadUserWithCarsPage.openPage()
                .isPageOpened();
        /* авторизация на прямую в API для создания нового пользователя не уходя со страницы по получению данных
        по пользователям с машинами
        */
        log.info("Authorization in API");
        Response loginResponse = given()
                .baseUri("http://82.142.167.37:4879")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", "admin@pflb.ru")
                .formParam("password", "admin")
                .when()
                .post("/login")
                .then()
                .statusCode(202)
                .extract()
                .response();
        String accessToken = loginResponse.jsonPath().getString("access_token");
        // создание пользователя
        log.info("Create new user with data: First Name = \"" + NEW_USER_FIRST_NAME + "\"," +
                " Last Name = \"" + NEW_USER_LAST_NAME + "\", Age = \"" + NEW_USER_AGE + "\", Sex = \"" + NEW_USER_SEX +
                "\", Money = \"" + NEW_USER_MONEY + "\"; in API");
        Map<String, Object> bodyUser = new HashMap<>();
        bodyUser.put("id", Integer.parseInt(NEW_USER_ID));
        bodyUser.put("firstName", NEW_USER_FIRST_NAME);
        bodyUser.put("secondName", NEW_USER_LAST_NAME);
        bodyUser.put("age", Integer.parseInt(NEW_USER_AGE));
        bodyUser.put("sex", NEW_USER_SEX);
        bodyUser.put("money", Integer.parseInt(NEW_USER_MONEY));
        Response newUserResponse = given()
                .baseUri("http://82.142.167.37:4879")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(bodyUser)
                .when()
                .post("/user")
                .then()
                .statusCode(201)
                .extract()
                .response();
        String newUserId = newUserResponse.jsonPath().getString("id");
        log.info("New user has been created with id = \"" + newUserId + "\"");
        // создание нового автомобиля
        log.info("Create new car with data: Engine Type = \"" + NEW_CAR_ENGINE_TYPE + "\"," +
                " Mark = \"" + NEW_CAR_MARK + "\", Model = \"" + NEW_CAR_MODEL +
                "\", Price = \"" + NEW_CAR_PRICE + "\"; in API");
        Map<String, Object> bodyCar = new HashMap<>();
        bodyCar.put("id", Integer.parseInt(NEW_CAR_ID));
        bodyCar.put("engineType", NEW_CAR_ENGINE_TYPE);
        bodyCar.put("mark", NEW_CAR_MARK);
        bodyCar.put("model", NEW_CAR_MODEL);
        bodyCar.put("price", Integer.parseInt(NEW_CAR_PRICE));
        Response newCarResponse = given()
                .baseUri("http://82.142.167.37:4879")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(bodyCar)
                .when()
                .post("/car")
                .then()
                .statusCode(201)
                .extract()
                .response();
        String newCarId = newCarResponse.jsonPath().getString("id");
        log.info("New car has been created with id = \"{}\"", newCarId);
        // покупка нового автомобиля пользователю
        log.info("Buy car with id = \"" + newCarId + "\" for user with id = \"" + newUserId + "\"; in API");
        Map<String, Object> bodyBuyCarForUser = new HashMap<>();
        bodyBuyCarForUser.put("id", Integer.parseInt(newUserId));
        bodyBuyCarForUser.put("firstName", NEW_USER_FIRST_NAME);
        bodyBuyCarForUser.put("secondName", NEW_USER_LAST_NAME);
        bodyBuyCarForUser.put("age", Integer.parseInt(NEW_USER_AGE));
        bodyBuyCarForUser.put("sex", NEW_USER_SEX);
        bodyBuyCarForUser.put("money", Integer.parseInt(NEW_USER_MONEY));
        Response buyCarResponse = given()
                .baseUri("http://82.142.167.37:4879")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(bodyBuyCarForUser)
                .when()
                .post("/user/" + newUserId + "/buyCar/" + newCarId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        log.info("Car with id = \"" + newCarId + "\" has been buy by user with id = \"" + newUserId + "\"");
        // вызов данных по новому пользователю c одним автомобилем
        usersReadUserWithCarsPage.callDataNewUser(newUserId)
                // проверка появления сообщения "Status: 200 ok"
                .checkStatus("Status: 200 ok")
                // проверка, что все данные по новому пользователю есть в таблице
                .checkUserInTable(newUserId,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_AGE,
                        NEW_USER_SEX,
                        String.valueOf((Integer.parseInt(NEW_USER_MONEY) - Integer.parseInt(NEW_CAR_PRICE))),
                        "1",
                        true)
                // проверка, что в таблица с машинами есть купленый автомобиль
                .checkCarsInTable(
                        newCarId,
                        NEW_CAR_ENGINE_TYPE,
                        NEW_CAR_MARK,
                        NEW_CAR_MODEL,
                        NEW_CAR_PRICE,
                        true);
    }

    @DataProvider(name = "Тестовые данные для негативного поиска по айди")
    public Object[][] userData() {
        return new Object[][]{
                {"999999", "Status: 204 user not found"},
                {"-999999", "Status:  Invalid input"},
                {",", "Status: 204 user not found"},
                {"", "Status: 204 user not found"}
        };
    }

    @Test(testName = "АТ.06.06. Проверка валидации полей с получением ошибок при вводе невалидных значений",
            description = "Проверка валидации полей с получением ошибок при вводе невалидных значений",
            priority = 1,
            groups = {"Negative", "Regression"},
            enabled = true,
            dataProvider = "Тестовые данные для негативного поиска по айди")
    @Description("Проверка валидации полей с получением ошибок при вводе невалидных значений")
    @Epic("")
    @Feature("Страница по получению данных по пользователю с невалидным айди")
    @Story("Отображение данных по пользователю с невалидным айди")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "docs.google", name = "Чек-лист PFLB")
    @TmsLink("TestCaseLink")
    @Issue("BugLink")
    @Owner("Anatoly Svetlov")
    public void checkDataInTableUserNegativeId(String id, String status) {
        loginStep.successfulAuthorization(validEmail, validPassword);
        usersReadUserWithCarsPage.openPage()
                .isPageOpened()
                // вызов данных по несуществующему пользователю
                .callDataNewUser(id)
                // проверка появления сообщения
                .checkStatus(status)
                // проверка, что таблица с пользователями пустая
                .checkTableIsEmpty("tableUser", "пользователями")
                // проверка, что таблица с машинами пустая
                .checkTableIsEmpty("tableCars", "машинами");
    }
}



