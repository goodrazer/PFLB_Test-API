package ui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasePage {
    protected static final Logger log = LogManager.getLogger(BasePage.class);
    protected static final String BASE_URL = "http://82.142.167.37:4881";

    public abstract BasePage openPage();
}