package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    protected static final Logger log = LoggerFactory.getLogger(BasePage.class);

    public abstract BasePage openPage();
    public abstract BasePage isPageOpened();
}