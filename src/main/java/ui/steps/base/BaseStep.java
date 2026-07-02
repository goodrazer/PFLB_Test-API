package ui.steps.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.PropertyReader;

public class BaseStep {
    protected static final Logger log = LogManager.getLogger(BaseStep.class);
    protected static final String BASE_URL = PropertyReader.getProperty("baseUrl");
    protected String validEmail = System.getProperty("Email", PropertyReader.getProperty("email"));
    protected String validPassword = System.getProperty("Password", PropertyReader.getProperty("password"));
}
