package ui.pages.users;

import ui.pages.base.BasePage;
import static com.codeborne.selenide.Selenide.open;

public class UsersIssueALoanPage extends BasePage {
    @Override
    public BasePage openPage() {
        log.info("Opening the 'UsersIssueALoanPage' start page");
        open (BASE_URL + "/#/update/Issue_A_Loan");
        return this;
    }
}
