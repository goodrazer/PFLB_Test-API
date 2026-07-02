package tests.ui.base;

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
import ui.pages.allDelete.AllDeletePage;
import ui.pages.allPost.AllPostPage;
import ui.pages.cars.CarsBuyOrSellCarPage;
import ui.pages.cars.CarsCreateNewPage;
import ui.pages.cars.CarsReadAllPage;
import ui.pages.houses.HousesCreateNewPage;
import ui.pages.houses.HousesReadAllPage;
import ui.pages.houses.HousesReadOneByIdPage;
import ui.pages.houses.HousesSettleOrEvictUser;
import ui.pages.login.LoginPage;
import ui.pages.swaggers.SwaggerDevJsDeprecatedPage;
import ui.pages.swaggers.SwaggerDevMvcPage;
import ui.pages.swaggers.SwaggerMasterMvcPage;
import ui.pages.users.*;
import ui.steps.login.LoginStep;
import utils.PropertyReader;
import listeners.TestListener;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    protected String validEmail = System.getProperty("Email", PropertyReader.getProperty("email"));
    protected String validPassword = System.getProperty("Password", PropertyReader.getProperty("password"));
    protected LoginPage loginPage;
    protected LoginStep loginStep;
    protected AllPostPage allPostPage;
    protected HousesCreateNewPage housesCreateNewPage;
    protected UsersCreateNewPage usersCreateNewPage;
    protected UsersReadAllPage usersReadAllPage;
    protected UsersReadUserWithCarsPage usersReadUserWithCarsPage;
    protected UsersAddMoneyPage usersAddMoneyPage;
    protected UsersBuyOrSellCarPage usersBuyOrSellCarPage;
    protected UsersSettleToHousePage usersSettleToHousePage;
    protected UsersIssueALoanPage usersIssueALoanPage;
    protected CarsReadAllPage carsReadAllPage;
    protected CarsCreateNewPage carsCreateNewPage;
    protected HousesReadAllPage housesReadAllPage;
    protected HousesReadOneByIdPage housesReadOneByIdPage;
    protected HousesSettleOrEvictUser housesSettleOrEvictUser;
    protected AllDeletePage allDeletePage;
    protected SwaggerDevJsDeprecatedPage swaggerDevJsDeprecatedPage;
    protected SwaggerDevMvcPage swaggerDevMvcPage;
    protected SwaggerMasterMvcPage swaggerMasterMvcPage;
    protected CarsBuyOrSellCarPage carsBuyOrSellCarPage;

    @BeforeSuite(alwaysRun = true)
    public void initSuite() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
        );
        Configuration.timeout = 20000;
        Configuration.baseUrl = "http://82.142.167.37:4881";
        Configuration.clickViaJs = true;
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.webdriverLogsEnabled = false;
        Configuration.fastSetValue = false;
    }

    @BeforeMethod(alwaysRun = true, description = "Настройка браузера")
    @Parameters({"browser"})
    @Step("Настройка браузера: {browser}")
    public void setUp(@Optional("firefox") String browser) {
        WebDriver driver;
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("privacy.popups.showBrowserMessage", false);
            options.addArguments("--no-sandbo");
            options.addArguments("--disable-dev-shm-usage");

            if (Configuration.headless) {
                options.addArguments("-headless");
            }
            driver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            if (Configuration.headless) {
                options.addArguments("--headless=new");
            }
            driver = new ChromeDriver(options);
        }
        WebDriverRunner.setWebDriver(driver);
        loginPage = new LoginPage();
        loginStep = new LoginStep();
        allPostPage = new AllPostPage();
        housesCreateNewPage = new HousesCreateNewPage();
        usersCreateNewPage = new UsersCreateNewPage();
        usersReadAllPage = new UsersReadAllPage();
        usersReadUserWithCarsPage = new UsersReadUserWithCarsPage();
        usersAddMoneyPage = new UsersAddMoneyPage();
        usersBuyOrSellCarPage = new UsersBuyOrSellCarPage();
        usersSettleToHousePage = new UsersSettleToHousePage();
        usersIssueALoanPage = new UsersIssueALoanPage();
        carsReadAllPage = new CarsReadAllPage();
        carsCreateNewPage = new CarsCreateNewPage();
        housesReadAllPage = new HousesReadAllPage();
        housesReadOneByIdPage = new HousesReadOneByIdPage();
        housesSettleOrEvictUser = new HousesSettleOrEvictUser();
        allDeletePage = new AllDeletePage();
        swaggerDevJsDeprecatedPage = new SwaggerDevJsDeprecatedPage();
        swaggerDevMvcPage = new SwaggerDevMvcPage();
        swaggerMasterMvcPage = new SwaggerMasterMvcPage();
        carsBuyOrSellCarPage = new CarsBuyOrSellCarPage();
    }

    @AfterMethod(alwaysRun = true, description = "Закрытие браузера")
    @io.qameta.allure.Description("Закрытие браузера")
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
