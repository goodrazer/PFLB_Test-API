package ui.pages;

import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.open;

public class UsersIssueALoanPage extends BasePage{
    @Override
    @Step("Открытие страницы 'Users -> Issue a loan'")
    public BasePage openPage() {
        log.info("Opening the 'Users -> Issue a loan' start page");
        open (BASE_URL + "/#/update/Issue_A_Loan");
        return this;
    }
}