package api.adapters.users;

import api.adapters.base.BaseAdapter;
import api.models.users.PersonDto;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import ui.dto.cars.CarGenerated;
import ui.dto.users.UserGenerated;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class UserService extends BaseAdapter {

    public final SelenideElement input = $("#user_input");
    private final UsersApiAdapter usersApi = new UsersApiAdapter();
    public String newUserId;
    public UserGenerated userAfterBuy;

    @Step("Создание нового пользователя через API с данными: {userGenerated}")
    public UserService createUserWithApiAccess(UserGenerated userGenerated) {
        // Авторизуемся
        usersApi.loginAsJson(validEmail, validPassword);
        // Создаём DTO пользователя
        PersonDto newUser = PersonDto.builder()
                .firstName(userGenerated.getFirstName())
                .secondName(userGenerated.getLastName())
                .age(Integer.parseInt(userGenerated.getAge()))
                .sex(userGenerated.getSex())
                .money(Double.parseDouble(userGenerated.getMoney()))
                .build();
        // создание нового пользователя
        Response newUserResponse = usersApi.createUser(newUser);
        newUserId = newUserResponse.jsonPath().getString("id");
        log.info("New user has been created with id = \"" + newUserId + "\"");
        return this;
    }

    @Step("Покупка нового автомобиля c Id = '{newCarId}' пользователю с Id = '{newUserId}' через API")
    public UserService buyCarToNewUserWithApiAccess(
            String newCarId,
            String newUserId,
            UserGenerated userGenerated,
            CarGenerated carGenerated
    ) {
        // Создаём DTO пользователя
        PersonDto userBuyer = PersonDto.builder()
                .id(Long.parseLong(newUserId))
                .firstName(userGenerated.getFirstName())
                .secondName(userGenerated.getLastName())
                .age(Integer.parseInt(userGenerated.getAge()))
                .sex(userGenerated.getSex())
                .money(Double.parseDouble(userGenerated.getMoney()))
                .build();
        usersApi.buyCar(newUserId, newCarId, userBuyer);
        log.info("Car with id = \"" + newCarId + "\" has been buy by user with id = \"" + newUserId + "\"");
        // Создаём пользователя после покупки
        userAfterBuy = UserGenerated.builder()
                .firstName(userGenerated.getFirstName())
                .lastName(userGenerated.getLastName())
                .age(userGenerated.getAge())
                .sex(userGenerated.getSex())
                .money(String.valueOf(
                        Integer.parseInt(userGenerated.getMoney()) - Integer.parseInt(carGenerated.getPrice())
                ))
                .build();
        return this;
    }
}
