package tests.ui.users;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import tests.ui.BaseTest;
import ui.wrappers.SortButton;
import ui.wrappers.TableColumn;

@Log4j2
@Epic("Users")
public class UsersReadAllTest extends BaseTest {

    private final String NEW_USER_FIRST_NAME = "A";
    private final String NEW_USER_LAST_NAME = "B";
    private final String NEW_USER_AGE = "10";
    private final String NEW_USER_SEX = "MALE";
    private final String NEW_USER_MONEY = "59";

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
                        false)
                // создание нового пользователя через API
                .createUserWithApiAccess(validEmail, validPassword, NEW_USER_FIRST_NAME, NEW_USER_LAST_NAME, NEW_USER_AGE, NEW_USER_SEX, NEW_USER_MONEY)
                // нажатие на кнопку обновления таблицы
                .reloadButtonClick()
                // проверка, что новый пользователь есть в таблице
                .checkUserInTable(usersReadAllPage.newUserId,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_AGE,
                        NEW_USER_SEX,
                        NEW_USER_MONEY,
                        true);
    }
}