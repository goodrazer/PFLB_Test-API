package tests.api.base;

import api.adapters.houses.HouseAdapter;
import api.adapters.login.LoginAdapter;
import org.testng.annotations.Listeners;
import utils.PropertyReader;
import utils.TestListener;

@Listeners(TestListener.class)
    public class BaseApiTest {
        protected final LoginAdapter loginAdapter = new LoginAdapter();
        protected final HouseAdapter houseAdapter = new HouseAdapter();
        protected final String BASE_URL = PropertyReader.getProperty("baseUrl");
        protected final String BASE_API_URL = PropertyReader.getProperty("baseAPIUrl");
        protected final String validEmail = System.getProperty("Email", PropertyReader.getProperty("email"));
        protected final String validPassword = System.getProperty("Password", PropertyReader.getProperty("password"));
    }
