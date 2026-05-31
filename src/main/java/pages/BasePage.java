package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasePage {
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    public abstract BasePage openPage();
    public abstract BasePage isPageOpened();
}