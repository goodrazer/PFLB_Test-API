package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.PropertyReader;
import utils.TestListener;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    protected String validEmail = System.getProperty("Email", PropertyReader.getProperty("email"));
    protected String validPassword = System.getProperty("Password", PropertyReader.getProperty("password"));
    protected LoginPage loginPage;

    @BeforeSuite(alwaysRun = true)
    public void initSuite() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
        );
        Configuration.timeout = 20000;
        Configuration.baseUrl = "http://82.142.167.37:4881";
        Configuration.clickViaJs = true;
        Configuration.headless = false;
        Configuration.browserSize = "1920x1080";
    }

    @BeforeMethod(alwaysRun = true, description = "Настройка браузера")
    @Parameters({"browser"})
    @Step("Настройка браузера: {browser}")
    public void setUp(@Optional("chrome") String browser) {
        WebDriver driver;
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private");
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("privacy.popups.showBrowserMessage", false);
            if (Configuration.headless) {
                options.addArguments("-headless");
            }
            driver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--window-size=1920,1080");
            if (Configuration.headless) {
                options.addArguments("--headless=new");
            }
            driver = new ChromeDriver(options);
        }
        WebDriverRunner.setWebDriver(driver);
        loginPage = new LoginPage();
    }

    @AfterMethod(alwaysRun = true, description = "Закрытие браузера")
    @io.qameta.allure.Description("Закрытие браузера")
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}