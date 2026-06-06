package ui.tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.switchTo;

    public class LoginTest extends BaseTest {

        @Test(testName = "АТ.01.01.Проверка валидации страницы 'Authorization' на наличие всех элементов",
                description = "Проверка валидации страницы 'Authorization' на наличие всех элементов",
                priority = 1,
                groups = {"Positive", "Regression"},
                enabled = true)
        @Description("Проверка валидации страницы 'Authorization' на наличие всех элементов.")
        @Epic("Epic01_Авторизация")
        @Feature("Проверка валидации страницы 'Authorization'")
        @Story("Проверка валидации страницы 'Authorization' на наличие всех элементов.")
        @Severity(SeverityLevel.CRITICAL)
        @Link(value = "docs.google", name = "Чек-лист PFLB")
        @TmsLink("TestCaseLink")
        @Issue("BugLink")
        @Flaky
        @Owner("Malevaniy Anton")
        public void checkingTheValidityOfTheAuthorizationPage() {
            loginPage.openPage();
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(loginPage.isTitleVisible(), "Заголовок не отображается!");
            softAssert.assertTrue(loginPage.isEmailFieldVisible(), "Поле Email не отображается!");
            softAssert.assertTrue(loginPage.isPasswordFieldVisible(), "Поле Password не отображается!");
            softAssert.assertTrue(loginPage.isGoButtonVisible(), "Кнопка GO не отображается!");
            softAssert.assertTrue(loginPage.isLogoutButtonVisible(), "Кнопка LOGOUT не отображается!");
            softAssert.assertAll();
        }

        @Test(testName = "АТ.01.02.Авторизация с валидными кредами",
                description = "Проверка авторизации с валидными данными (валидный email + пароль) и" +
                        "отображения сообщения об успешном входе.",
                priority = 1,
                groups = {"Positive", "Smoke", "Regression"},
                enabled = true)
        @Description("Авторизация пользователя с валидными данными 'Email' и 'Password'.")
        @Epic("Epic01_Авторизация")
        @Feature("User authorization")
        @Story("Авторизация пользователя с валидными данными 'Email' и 'Password'")
        @Severity(SeverityLevel.BLOCKER)
        @Link(value = "docs.google", name = "Чек-лист PFLB")
        @TmsLink("TestCaseLink")
        @Issue("BugLink")
        @Flaky
        @Owner("Malevaniy Anton")
        public void positiveAuthorization() {
            loginPage.openPage()
                    .positiveLogin(validEmail, validPassword);
            String alertText = switchTo().alert().getText();
            Assert.assertEquals(alertText, "Successful authorization");
            switchTo().alert().accept();
        }

        @Test(testName = "АТ.01.03.Проверка валидации поля 'Email' значением не являющимся адресом электронной почты." +
                "(Без домена @)",
                description = "Проверка валидации поля 'Email' значением не являющимся адресом электронной почты",
                priority = 3,
                groups = {"Negative", "Regression"},
                enabled = true)
        @Description("Проверка валидации поля 'Email' значением не являющимся адресом электронной почты.")
        @Epic("Epic01_Авторизация")
        @Feature("Проверка валидации поля 'Email'")
        @Story("Проверка валидации поля 'Email' значением не являющимся адресом электронной почты.")
        @Severity(SeverityLevel.CRITICAL)
        @Link(value = "docs.google", name = "Чек-лист PFLB")
        @TmsLink("TestCaseLink")
        @Issue("BugLink")
        @Flaky
        @Owner("Malevaniy Anton")
        public void checkForTheEmailField() {
            loginPage.openPage()
                    .enterEmail("notemail.com")
                    .enterPassword("12345");
            Assert.assertEquals(loginPage.getErrorMessageEmail(), "incorrect Email");
        }

        @Test(testName = "АТ.01.04.Проверка валидации поля 'Password' при вводе значений < 3 символов",
                description = "Проверка валидации поля 'Password' при вводе значений < 3 символов",
                priority = 3,
                groups = {"Negative", "Regression"},
                enabled = true)
        @Description("Проверка валидации поля 'Password' при вводе значений < 3 символов")
        @Epic("Epic01_Авторизация")
        @Feature("Проверка валидации поля 'Password'")
        @Story("Проверка валидации поля 'Password' при вводе значений < 3 символов")
        @Severity(SeverityLevel.CRITICAL)
        @Link(value = "docs.google", name = "Чек-лист PFLB")
        @TmsLink("TestCaseLink")
        @Issue("BugLink")
        @Flaky
        @Owner("Malevaniy Anton")
        public void checkValidationForPasswordFieldWithValueLessThanThree() {
            loginPage.openPage()
                    .enterPassword("1!")
                    .enterEmail("ayay@yandex.ru");
            Assert.assertEquals(loginPage.getErrorMessagePassword(),
                    "password length must be more than 3 symbols and less than 8 symbols");
        }

        @Test(testName = "АТ.01.05.Проверка валидации поля 'Password' при вводе значений > 8 символов",
                description = "Проверка валидации поля 'Password' при вводе значений > 8 символов",
                priority = 3,
                groups = {"Negative", "Regression"},
                enabled = true)
        @Description("Проверка валидации поля 'Password' при вводе значений > 8 символов.")
        @Epic("Epic01_Авторизация")
        @Feature("Проверка валидации поля 'Password'")
        @Story("Проверка валидации поля 'Password' при вводе значений > 8 символов.")
        @Severity(SeverityLevel.CRITICAL)
        @Link("https://docs.google.com/document/d/1B4ltZQEx8C6b-EhSGyezA49Jc2IlCrqgGhXlRugrYdY" +
                "/edit?tab=t.1g932z60l1u6#heading=h.kry4ansadogj")
        @TmsLink("TestCaseLink")
        @Issue("BugLink")
        @Flaky
        @Owner("Malevaniy Anton")
        public void checkThePasswordFieldIfTheValueIsGreaterThanEight() {
            loginPage.openPage()
                    .enterPassword("12TY5!7O9")
                    .enterEmail("ayay@yandex.ru");
            Assert.assertEquals(loginPage.getErrorMessagePassword(),
                    "password length must be more than 3 symbols and less than 8 symbols");
        }

        @Test(testName = "АТ.01.06.Проверка доступности выполнений действий для авторизованного пользователя",
                description = "Проверка доступности выполнений действий для авторизованного пользователя " +
                        "(создать пользователя)",
                priority = 1,
                groups = {"Positive", "Regression", "Smoke"},
                enabled = true)
        @Description("Проверка доступности выполнений действий для авторизованного пользователя (создать пользователя)")
        @Epic("Epic01_Авторизация")
        @Feature("Проверка доступности выполнений действий для авторизованного пользователя")
        @Story("Проверка доступности выполнений действий для авторизованного пользователя (создать пользователя)")
        @Severity(SeverityLevel.CRITICAL)
        @Link("https://docs.google.com/document/d/1B4ltZQEx8C6b-EhSGyezA49Jc2IlCrqgGhXlRugrYdY" +
                "/edit?tab=t.1g932z60l1u6#heading=h.kry4ansadogj")
        @TmsLink("TestCaseLink")
        @Issue("BugLink")
        @Flaky
        @Owner("Malevaniy Anton")
        public void checkingTheAvailabilityOfActionsForAnAuthorizedUser() {
            loginStep.successfulAuthorization(validEmail, validPassword);
            Selenide.sleep(3000);
            allPostPage.clickUsersButton()
                            .clickCreateNewButton();
            Assert.assertEquals(allPostPage.getTextElementIDWillBeGenerated(),"ID will be generated" ,
                    "Ошибка!!! Создание пользователя недоступно для неавторизованного пользователя!");
        }
    }