package api.tests;

import api.adapters.CarAdapter;
import api.models.*;
import org.testng.annotations.Test;
import java.util.concurrent.ThreadLocalRandom;

import static api.adapters.CarAdapter.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CarsApiTest {

    int randomId = ThreadLocalRandom.current().nextInt(1, 10000);

    //Данные для создания
    CreateCarRq rqCreateCar = CreateCarRq.builder()
            .id(randomId)
            .engineType("Electric")
            .model("Jeep")
            .mark("Wrangler")
            .price(4999.99)
            .build();

    //Данные для изменения
    UpdateCarRq rqUpdateCar = UpdateCarRq.builder()
            .id(randomId)
            .engineType("Diesel")
            .model("Jeep")
            .mark("Grand Cherokee")
            .price(7999.55)
            .build();

//    @Test
//    public void checkCarsCRUD() {
//        //Создание автомобиля
//        CreateCarRs rs = createCar(rqCreateCar);
//        assertTrue(rs.status);
//        assertEquals("1", rs.result.code);
//        //Проверка созданного авто
//        GetCarRs getCarId = getCar(rqCreateCar.getId());
//        assertEquals(rqCreateCar.getId(), 4);
//        //Изменение созданного авто
//        UpdateCarRs rsUpdate = updateCar(rqUpdateCar, rqCreateCar.getId());
//        assertTrue(rs.status);
//        assertEquals("1", rsUpdate.result.code);
//        //Проверка измененного автомобиля
//        getCarId = getCar(rqCreateCar.getId());
//        assertEquals(rqCreateCar.getId(), 4);
//        //Удаление автомобиля
//        CarAdapter.deleteCar(rqCreateCar.getId());
//        assertEquals(Result.builder(), "200");
//       //Проверка удаления автомобиля
//        getCarId = getCar(rqCreateCar.getId());
//        assertEquals(getCarId.status, 404);
//    }
}
