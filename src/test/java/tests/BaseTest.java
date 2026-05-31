package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.PropertyReader;
import utils.TestListener;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    protected String validUser = System.getProperty("User", PropertyReader.getProperty("user"));
    protected String validPassword = System.getProperty("Password", PropertyReader.getProperty("password"));
    protected LoginPage loginPage;


    @BeforeSuite(alwaysRun = true)
    public void initAllureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
        );
    }


    @BeforeMethod(alwaysRun = true, description = "Настройка браузера")
    @Parameters({"browser"})
    @Step("Настройка браузера: {browser}")
    public void setUp(@Optional("chrome") String browser) {
        Configuration.timeout = 20000;
        Configuration.baseUrl = "http://82.142.167.37:4881";
        Configuration.clickViaJs = true;
        Configuration.headless = false;
        Configuration.browserSize = "1920x1080";

        if (browser.equalsIgnoreCase("chrome")) {
            Configuration.browser = "chrome";
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            Configuration.browserCapabilities = options;
        } else if (browser.equalsIgnoreCase("firefox")) {
            Configuration.browser = "firefox";
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private");
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("privacy.popups.showBrowserMessage", false);
            Configuration.browserCapabilities = options;
        }

        loginPage = new LoginPage();
    }

    @AfterMethod(alwaysRun = true, description = "Закрытие браузера")
    @io.qameta.allure.Description("Закрытие браузера")
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}