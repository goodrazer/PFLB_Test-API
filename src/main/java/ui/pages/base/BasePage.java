package ui.pages.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.PropertyReader;

public abstract class BasePage {
    protected static final Logger log = LogManager.getLogger(BasePage.class);
    protected static final String BASE_URL = PropertyReader.getProperty("baseUrl");

    public abstract BasePage openPage();
}
