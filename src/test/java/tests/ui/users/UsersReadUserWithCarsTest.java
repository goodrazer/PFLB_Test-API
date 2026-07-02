package tests.ui.users;

import io.qameta.allure.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.wrappers.TableColumn;

@Log4j2
@Epic("Users")
public class UsersReadUserWithCarsTest extends BaseTest {

    public static String NEW_CAR_ENGINE_TYPE = "PHEV";
    public static String NEW_CAR_MARK = "Известная";
    public static String NEW_CAR_MODEL = "Популярная";
    public static String NEW_CAR_PRICE = "6";
    private final String NEW_USER_ID = "9988775";
    private final String NEW_USER_FIRST_NAME = "A";
    private final String NEW_USER_LAST_NAME = "B";
    private final String NEW_USER_AGE = "10";
    private final String NEW_USER_SEX = "MALE";
    private final String NEW_USER_MONEY = "64";

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
                .checkChangeColorInputButton("rgb(108, 117, 125)", "rgb(92, 99, 106)")
                .checkVisibleTooltipInput();
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
                .isPageOpened()
                /* авторизация на прямую в API для создания нового пользователя не уходя со страницы по получению данных
                по пользователям с машинами
                */
                .createUserWithApiAccess(validEmail, validPassword, NEW_USER_FIRST_NAME, NEW_USER_LAST_NAME,
                        NEW_USER_AGE, NEW_USER_SEX, NEW_USER_MONEY)
                // вызов данных по новому пользователю без автомобилей
                .callDataNewUser(usersReadUserWithCarsPage.newUserId)
                // проверка появления сообщения "Status: 200 ok"
                .checkStatus("Status: 200 ok")
                // проверка, что все данные по новому пользователю есть в таблице
                .checkUserInTable(usersReadUserWithCarsPage.newUserId,
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
                .isPageOpened()
                /* создание нового пользователя на прямую в API не уходя со страницы по получению данных
                по пользователям с машинами
                */
                .createUserWithApiAccess(validEmail, validPassword, NEW_USER_FIRST_NAME, NEW_USER_LAST_NAME,
                        NEW_USER_AGE, NEW_USER_SEX, NEW_USER_MONEY)
                // создание нового автомобиля
                .createCarWithApiAccess(validEmail, validPassword, NEW_CAR_ENGINE_TYPE, NEW_CAR_MARK, NEW_CAR_MODEL,
                        NEW_CAR_PRICE)
                // покупка нового автомобиля пользователю
                .buyCarToNewUserWithApiAccess(validEmail, validPassword, usersReadUserWithCarsPage.newCarId,
                        usersReadUserWithCarsPage.newUserId, NEW_USER_FIRST_NAME, NEW_USER_LAST_NAME, NEW_USER_AGE,
                        NEW_USER_SEX, NEW_USER_MONEY)
                // вызов данных по новому пользователю c одним автомобилем
                .callDataNewUser(usersReadUserWithCarsPage.newUserId)
                // проверка появления сообщения "Status: 200 ok"
                .checkStatus("Status: 200 ok")
                // проверка, что все данные по новому пользователю есть в таблице
                .checkUserInTable(usersReadUserWithCarsPage.newUserId,
                        NEW_USER_FIRST_NAME,
                        NEW_USER_LAST_NAME,
                        NEW_USER_AGE,
                        NEW_USER_SEX,
                        String.valueOf((Integer.parseInt(NEW_USER_MONEY) - Integer.parseInt(NEW_CAR_PRICE))),
                        "1",
                        true)
                // проверка, что в таблице с машинами есть купленный автомобиль
                .checkCarsInTable(
                        usersReadUserWithCarsPage.newCarId,
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
