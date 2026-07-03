# Фреймворк автоматизированного тестирования для веб-приложения PFLB Test-API

![](logo.PNG)

**Ссылка на проект:**
https://github.com/goodrazer/PFLB_Test-API  

**Ссылка на веб-приложение:**
http://82.142.167.37:4881/  

## Оглавление

- [Описание проекта](#описание-проекта)
- [Технологический стек](#технологический-стек)
- [Структура проекта](#структура-проекта)
- [Архитектурные паттерны](#архитектурные-паттерны)
    - [Page Object Model (POM)](#page-object-model-pom)
    - [Chain of Invocations](#chain-of-invocations)
    - [Value Object (DTO)](#value-object-dto)
    - [Wrappers](#wrappers)
    - [Steps](#steps)
    - [Loadable Page](#loadable-page)
    - [Retry Mechanism](#retry-mechanism)
    - [DataProvider Pattern](#dataprovider-pattern)
- [Чек-лист UI тестирования](#чек-лист-ui-тестирования)
    - [Модуль Authorization](#модуль-authorization---страница-авторизации-)
    - [Модуль All POST / ALL DELETE](#модуль-all-post--all-delete---страница-all-post-createall--all-delete-deleteall)
    - [Модуль Cars](#модуль-cars)
    - [Модуль Houses](#модуль-houses)
    - [Статистика покрытия UI тестов](#статистика-покрытия-ui-тестов)
- [Чек-лист API тестирования](#чек-лист-api-тестирования)
    - [Модуль Authorization (API)](#модуль-authorization-api)
    - [Модуль Users (API)](#модуль-users-api)
    - [Модуль Cars (API)](#модуль-cars-api)
    - [Модуль Houses (API)](#модуль-houses-api)
    - [Модуль User-Car Relations (API)](#модуль-user-car-relations-api)
    - [Модуль User-House Relations (API)](#модуль-user-house-relations-api)
    - [Модуль User Money/Loan (API)](#модуль-user-moneyloan-api)
    - [Модуль Logging (API)](#модуль-logging-api)
    - [Модуль Criminal Records (API)](#модуль-criminal-records-api)
    - [Модуль API User Management (API)](#модуль-api-user-management-api)
    - [Статистика покрытия API тестов](#статистика-покрытия-api-тестов)
    - [Общие негативные сценарии](#общие-негативные-сценарии-для-всех-модулей)
- [Команда проекта](#команда-проекта)
- [Запуск тестов](#запуск-тестов)
- [Отчётность](#отчётность)

---

## Описание проекта

**PFLB Test-API** - веб-приложение с REST API и UI-интерфейсом для управления сущностями:

- **Users** - пользователи (создание, пополнение баланса, привязка авто/дома, кредиты)
- **Cars** - автомобили (создание, покупка/продажа)
- **Houses** - дома (создание с парковками, заселение/выселение)

...(в разработке)

---
## Технологический стек

| Категория          | Технология  | Версия  |
|--------------------|-------------|---------|
| Язык               | Java        | 17      |
| Сборщик            | Maven       | 3.x     |
| UI-фреймворк       | Selenide    | 7.16.2  |
| API-тестирование   | RestAssured | 6.00    |
| Тестовый фреймворк | TestNG      | 7.12.0  |
| Отчётность         | Allure      | 2.29.0  |
| Логирование        | Log4j2      | 2.23.1  |
| Утилиты            | Lombok      | 1.18.42 |
| Генерация данных   | JavaFaker   | 1.0.2   |
| Валидация JSON     | GSON        | 2.14.0  |


## Плагины
| Плагин                  |  Версия  |
|-------------------------|---------|
| maven-surefire-plugin   | 3.5.5     |
| maven-compiler-plugin   | 1.18.42   |
| allure-maven   | 2.29.0     |

---
## Структура проекта

```text
PFLB_Test-API/
│
├── pom.xml                                                                   # Конфигурация Maven
├── README.md                                                                 # Документация проекта
├── logo.png                                                                  # Лого проекта
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── api/                                                                # API-часть фреймворка
│ │ │ │ ├── adapters/                                                         # Адаптеры фреймворка
│ │ │ │ │ ├── base/                                                           # Базовые конфигурации адаптера фреймворка
│ │ │ │ │ │ └── BaseAdapter.java                                              # Базовый адаптер для API-запросов
│ │ │ │ │ ├── cars/                                                           # Адаптеры автомобилей
│ │ │ │ │ │ └── CarAdapter.java                                               # Адаптер для тестов Car
│ │ │ │ │ ├── login/                                                          # Адаптеры для авторизации
│ │ │ │ │ │ ├── LoginAdapter.java                                             # Адаптер для тестов с авторизацией
│ │ │ │ │ │ └── AuthHelper.java                                               # Хелпер для авторизации
│ │ │ │ │ └── users/                                                          # Адаптеры для пользователей
│ │ │ │ │   └── UserApiAdapter.java                                           # Адаптер для тестов User
│ │ │ │ │ 
│ │ │ │ └── models/                                                           # Модели фреймворка
│ │ │ │   ├── cars/                                                           # Модели для тестов cars
│ │ │ │   │ ├── CreateCarRq.java                                              # Модель запроса при создании авто
│ │ │ │   │ ├── CreateCarRs.java                                              # Модель ответа при создании авто
│ │ │ │   │ ├── GetCarRs.java                                                 # Модель ответа при запросе авто
│ │ │ │   │ ├── UpdateCarRq.java                                              # Модель запроса на обновление авто
│ │ │ │   │ └── UpdateCarRs.java                                              # Модель ответа на обновление авто
│ │ │ │   ├── login/                                                          # Модели для авторизации
│ │ │ │   │ ├──LoginRequest.java                                              # Модель запроса при логин
│ │ │ │   │ └── LoginResponse.java                                            # Модель ответа при логине
│ │ │ │   └── users/                                                          # Модели для тестов users
│ │ │ │     ├── GetUserRs.java                                                # Модель ответа при запросе пользователя
│ │ │ │     └── PersonDTO.java                                                # DTO пользователя для API
│ │ │ │ 
│ │ │ ├── ui/                                                                 # UI-часть фреймворка
│ │ │ │ ├── dto/                                                              # Value Objects (DTO)
│ │ │ │ │ ├── cars/                                                           # DTO для автомобилей
│ │ │ │ │ │ ├── Car.java                                                      # DTO автомобиля
│ │ │ │ │ │ └── Parking.java                                                  # DTO парковки
│ │ │ │ │ ├── houses/                                                         # DTO для домов
│ │ │ │ │ │ └── House.java                                                    # DTO дома
│ │ │ │ │ └── users/                                                          # DTO для пользователей
│ │ │ │ │   ├── User.java                                                     # DTO пользователя
│ │ │ │ │   └── UserCar.java                                                  # DTO связи User-Car
│ │ │ │ │
│ │ │ │ ├── pages/                                                            # Page Objects фреймворка
│ │ │ │ │ ├── allDelete/                                                      # Страницы All Delete
│ │ │ │ │ │ └── AllDeletePage.java                                            # Страница "All DELETE"
│ │ │ │ │ ├── allPost/                                                        # Страницы All Post
│ │ │ │ │ │ └── AllPostPage.java                                              # Страница "All POST"
│ │ │ │ │ ├── base/                                                           # Базовые страницы фреймворка
│ │ │ │ │ │ └── BasePage.java                                                 # Базовая страница фреймворка
│ │ │ │ │ ├── cars/                                                           # Страницы автомобилей
│ │ │ │ │ │ ├── CarsBuyOrSellCarPage.java                                     # Страница покупки/продажи авто
│ │ │ │ │ │ ├── CarsCreateNewPage.java                                        # Страница создания авто
│ │ │ │ │ │ └── CarsReadAllPage.java                                          # Страница со списком всех авто
│ │ │ │ │ ├── houses/                                                         # Страницы домов
│ │ │ │ │ │ ├── HousesCreateNewPage.java                                      # Страница создания дома
│ │ │ │ │ │ ├── HousesReadAllPage.java                                        # Страница со списком всех домов
│ │ │ │ │ │ ├── HousesReadOneByIdPage.java                                    # Страница с просмотром дома по ID
│ │ │ │ │ │ └── HousesSettleOrEvictUser.java                                  # Страница заселения/выселения
│ │ │ │ │ ├── login/                                                          # Страницы авторизации
│ │ │ │ │ │ └── LoginPage.java                                                # Страница авторизации
│ │ │ │ │ ├── swaggers/                                                       # Страницы swagger
│ │ │ │ │ │ ├── SwaggerDevJsDeprecatedPage.java                               # Устаревшая страница "Swagger Dev Js"
│ │ │ │ │ │ ├── SwaggerDevMvcPage.java                                        # Страница "Swagger Dev MVC"
│ │ │ │ │ │ └── SwaggerMasterMvcPage.java                                     # Страница "Swagger Master Mvc"
│ │ │ │ │ └──  users/                                                         # Страницы пользователей
│ │ │ │ │   ├── UsersAddMoneyPage.java                                        # Страница пополнения баланса
│ │ │ │ │   ├── UsersBuyOrSellCarPage.java                                    # Страница покупки/продажи авто
│ │ │ │ │   ├── UsersCreateNewPage.java                                       # Страница создания пользователя
│ │ │ │ │   ├── UsersIssueALoanPage.java                                      # Страница выдачи кредита
│ │ │ │ │   ├── UsersReadAllPage.java                                         # Страница списка всех пользователей
│ │ │ │ │   ├── UsersReadUserWithCarsPage.java                                # Страница списка всех пользователей с авто
│ │ │ │ │   └── UsersSettleToHousePage.java                                   # Страница заселения в дом
│ │ │ │ │
│ │ │ │ ├── steps/                                                            # Общие шаги
│ │ │ │ │ ├── base/                                                           # Базовые шаги
│ │ │ │ │ │ └── BaseStep.java                                                 # Базовый шаг
│ │ │ │ │ └── login/                                                          # Шаги авторизации
│ │ │ │ │   └── LoginStep.java                                                # Шаг авторизации
│ │ │ │ │ 
│ │ │ │ └── wrappers/                                                         # Обертки фреймворка
│ │ │ │   ├── cars/                                                           # Обертки для автомобилей            
│ │ │ │   │ ├── CarWrapper.java                                               # Обертка для авто
│ │ │ │   │ └── InputCars.java                                                # Обертка для полей авто
│ │ │ │   ├── houses/                                                         # Обертки для домов
│ │ │ │   │ └── InputHousesCreateNewOneTable.java                             # Обертка для создания дома
│ │ │ │   ├── ButtonWrapper.java                                              # Обертка для кнопок
│ │ │ │   ├── InputWrapper.java                                               # Универсальная обертка для полей ввода
│ │ │ │   ├── RadioWrapper.java                                               # Обертка для radio-кнопок
│ │ │ │   ├── SortButton.java                                                 # Обертка для кнопок сортировки
│ │ │ │   └── TableColumn.java                                                # Обертка поиска по столбцу таблицы
│ │ │ │   
│ │ │ └── utils/                                                              # Утилиты фреймворка
│ │ │   │── PropertyReader.java                                               # Утилита чтения настроек конфигурации       
│ │ │   │── SortUtils.java                                                    # Утилита для сортировки
│ │ │   └── TestListener.java                                                 # Утилита слушатель тестов 
│ │ │ 
│ │ └── resources/                                                            # Пакет с ресурсами фреймворка 
│ │     └── config.properties                                                 # Настройки конфигурации
│ │
│ │
│ │
│ │
│ └── test/                                                                   
│   ├── java/
│   │ ├── listeners/                                                          # Пакет для слушателей фреймворка
│   │ │ ├── RetryTransformer.java                                             # Утилита перезапуска тестов при падении
│   │ │ └── TestListener.java                                                 # Утилита слушатель тестов 
│   │ │ 
│   │ ├── tests/                                                              # Пакет с тестами фреймворка 
│   │ │ ├── api/                                                              # Пакет с API тестами 
│   │ │ │ ├── cars/                                                           # Пакет с API тестами по авто
│   │ │ │ │ └── CarsApiTest.java                                              # API-тесты для Cars
│   │ │ │ ├── login/                                                          # Пакет с API тестами авторизации
│   │ │ │ │ └── LoginApiTest.java                                             # API-тесты авторизации
│   │ │ │ └── users/                                                          # Пакет с API тестами по пользователям
│   │ │ │   └── UsersApiTest.java                                             # API-тесты для Users
│   │ │ └── ui/                                                               # Пакет с UI тестами 
│   │ │   ├── allPost/                                                        # Пакет с UI тестами "all Post"
│   │ │   │ └── AllPostTest.java                                              # UI тесты "AllPost"
│   │ │   ├── base/                                                           # Пакет с базовыми UI тестами
│   │ │   │ └── BaseTest.java                                                 # Универсальный базовый UI тест
│   │ │   ├── cars/                                                           # Пакет с UI тестами по авто
│   │ │   │ └── CarsTest.java                                                 # UI тесты по авто
│   │ │   ├── houses/                                                         # Пакет с UI тестами по домам
│   │ │   │ └── HousesTest.java                                               # UI тесты по домам
│   │ │   ├── login/                                                          # Пакет с UI тестами по авторизации
│   │ │   │ └── LoginTest.java                                                # UI тесты авторизации
│   │ │   └── users/                                                          # Пакет с UI тестами по пользователям
│   │ │     ├── CreateUserTest.java                                           # UI тесты по созданию дома
│   │ │     ├── UsersAddMoneyTest.java                                        # UI тесты по пополнению баланса
│   │ │     ├── UsersReadAllTest.java                                         # UI тесты поиска пользователя по ID
│   │ │     └── UsersReadUserWithCarsTest.java                                # UI тесты поиска пользователя с авто
│   │ │ 
│   │ └── utils/                                                              # Утилиты фреймворка
│   │   ├── PropertyReader.java                                               # Утилита чтения настроек конфигурации 
│   │   └── RetryAnalyzer.java                                                # Утилита перезапуска тестов при падении
│   │
│   └── resources/                                                            # Пакет с ресурсами фреймворка
│     ├── AllTestSuite.xml                                                    # Сюит для запуска всех тестов
│     ├── allure.properties                                                   # Настройки Allure
│     ├── ApiTestSuite.xml                                                    # Сюит для запуска API тестов
│     ├── config.properties                                                   # Настройки конфигураций
│     ├── CrossBrowserTestSuite.xml                                           # Сюит для запуска кросс-браузерного тестирования
│     ├── log4j2.xml                                                          # Логгер
│     ├── NegativeTestSuite.xml                                               # Сюит для запуска негативных тестов
│     ├── PositiveTestSuite.xml                                               # Сюит для запуска позитивных тестов
│     ├── RegressionTestSuite.xml                                             # Сюит для запуска регрессионного тестирования
│     ├── SecurityTestSuite.xml                                               # Сюит для запуска тестирования безопасности
│     └── SmokeTestSuite.xml                                                  # Сюит для запуска дымового тестирования
│

```
---

## Архитектурные паттерны

### Page Object Model (POM)

Каждая страница представлена отдельным классом, инкапсулирующим локаторы и действия:

- `BasePage` - специализированная страница с базовыми конфигурациями для всех страниц.
- `LoginPage` - страница авторизации.
- `AllPostPage` - агрегированная страница создания сущностей.
- `AllDeletePage` - страница удаления сущностей.
- `CarsBuyOrSellCarPage` - страница покупки/продажи авто.
- `CarsCreateNewPage` - страница создания авто.
- `CarsReadAllPage` - страница поиска авто.
- `HousesCreateNewPage` - страница создания дома.
- `HousesReadAllPage` - страница поиска дома.
- `HousesReadOneByIdPage` - страница поиска дома по ID.
- `HousesSettleOrEvictUser` - страница урегулирование спора или выселение пользователя.
- `SwaggerDevJsDeprecatedPage` - страница "Swagger Dev Js Deprecated".
- `SwaggerDevMvcPage` - страница "Swagger Dev Mvc".
- `SwaggerMasterMvcPage` страница "Swagger Master Mvc".
- `UsersAddMoneyPage` - страница пополнения ДС.
- `UsersBuyOrSellCarPage` - страница пользователей, покупающих или продающих автомобили.
- `UsersCreateNewPage` - страница создания нового пользователя.
- `UsersIssueALoanPage` - страница оформления займа пользователями.
- `UsersReadAllPage` - страница редактирования пользователя.
- `UsersReadUserWithCarsPage` - страница поиска пользователя с авто.
- `UsersSettleToHousePage` - Страница перехода к дому через пользователя.

```java
public class HousesReadOneByIdPage extends BasePage {
    private final SelenideElement HOUSE_INPUT = $("#house_input");
    private final SelenideElement READ = $x(" //button[@class='tableButton btn btn-primary']");
    private final SelenideElement ID_FOUND_HOUSE = $x("//table[1]//tbody//td[1]");

    @Override
    @Step("Открытие страницы 'Houses -> Read one by ID'")
    public HousesReadOneByIdPage openPage() {
        log.info("Opening the 'Houses -> Read one by ID' start page");
        open(BASE_URL + "/#/read/house");
        return this;
    }

    @Step("Заполнение поля поиска объекта недвижимости по ID на странице 'Houses -> Read one by ID'")
    public HousesReadOneByIdPage fillingInTheHouseInputSearchField(String houseInputSearch) {
        log.info("Filling in the {house_input} search field");
        HOUSE_INPUT.setValue(houseInputSearch);
        return this;
    }
}
```

### Chain of Invocations

Методы возвращают `this` для построения цепочек вызовов:

```java
@Step("Заполнение поля 'Password' значением: ******")
public LoginPage enterPassword(@Param(mode = Parameter.Mode.MASKED) String password) {
  log.info("Filling in the 'Password' field");
  SelenideLogger.removeListener("AllureSelenide");
  INPUT_PASSWORD.setValue(password);
  SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
          .screenshots(true)
          .savePageSource(false));
  return this;
}


public void checkForTheEmailField() {
  loginPage.openPage()
          .enterEmail("notemail.com")
          .enterPassword("12345");
  assertEquals(loginPage.getErrorMessageEmail(), "incorrect Email");
}
```

### Value Object (DTO)

Данные передаются через объекты с использованием Lombok `@Builder`:

```java
User user = User.builder()
        .firstName("AQA_User")
        .lastName("Testov")
        .age(30)
        .sex("MALE")
        .money(100000)
        .build();
```

### Wrappers
Переиспользуемые обёртки для UI-элементов:

- **InputWrapper** - для полей ввода (с clear() + sendKeys() + проверкой значения)
- **ButtonWrapper** - для кнопок (с проверкой interactable)
- **RadioWrapper** - для radio-кнопок (с проверкой isSelected())
- **CarsWrapper** - специализированный wrapper для операций с авто
- **InputCars** - специализированный wrapper для операций с авто
- **InputHousesCreatedNewOneTable** - специализированный wrapper для операций с домами
- **SortButton** - специализированный wrapper для сортировки
- **TableColumn** - специализированный wrapper для столбцов таблиц

```java
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class InputHousesCreateNewOneTable {
  private String fieldName;
  private String id;
  private static final String INPUT_PATTERN_XPATH_ON_HOUSES_CREATE_NEW =
          "//*[contains(text(), '%s')]/ancestor::section[@class='workspace']//parent::div//input[@id='%s']";

  @Step("Заполнение полей 'Input' первой таблицы в зависимости параметров наименования поля и id на странице:" +
          " 'Houses --> CreateNew'")
  public void writeInputHousesCreateNewOneTable(String text) {
    log.info("Filling the 'Input' fields of the first table depending on the field name '{}' and id '{}' " +
            "parameters on the page: 'Houses --> CreateNew'", fieldName, id);
    String formattedXpath = String.format(INPUT_PATTERN_XPATH_ON_HOUSES_CREATE_NEW, fieldName, id);
    $x(formattedXpath).setValue(text);
  }
}
```

### Steps
Часть бизнес-логики вынесена в Step-классы (LoginStep, BaseStep) для переиспользования между тестами.

```java
public class LoginStep extends BaseStep {

  LoginPage loginPage = new LoginPage();

  @Step("Авторизация пользователя с валидными данными логина: ****** и пароля: ****** с кликом по кнопке 'GO'")
  public LoginStep successfulAuthorization(
          @Param(mode = Parameter.Mode.MASKED) String email,
          @Param(mode = Parameter.Mode.MASKED) String password){
    loginPage.successfulAuthorizationSwitchToAlert(email,password);
    loginPage = new LoginPage();
    return this;
  }
}
```

### Loadable Page
Страница проверяет свою загрузку через метод openPage() с retry-логикой до нескольких попыток.

```java
public AllDeletePage() {super();}
@Override
@Step("Открытие страницы 'All DELETE'.")
public AllDeletePage openPage() {
  log.info("Opening 'All DELETE' page");
  String targetUrl = BASE_URL + "/#/delete/all";

  // Пытаемся открыть страницу до 3 раз
  for (int attempt = 1; attempt <= 3; attempt++) {
    log.info("Attempt {} to open page: {}", attempt, targetUrl);
    com.codeborne.selenide.WebDriverRunner.getWebDriver().get(targetUrl);
    try {
      $x(BTN_DELETE_USER).shouldBe(visible, java.time.Duration.ofSeconds(15));
      log.info("Page loaded successfully on attempt {}", attempt);
      return this;
    } catch (Exception e) {
      log.warn("Attempt {} failed, retrying...", attempt);
      if (attempt == 3) {
        throw e;
      }
      sleep(1000);
    }
  }
  return this;
}
```

### Retry Mechanism
Автоматический перезапуск упавших тестов через RetryAnalyzer + RetryTransformer

```java
public class RetryAnalyzer implements IRetryAnalyzer {
  private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(RetryAnalyzer.class);
  private int count = 0;
  private static int maxTry = 3;
  @Override
  public boolean retry(ITestResult iTestResult) {
    if (!iTestResult.isSuccess()) {
      if (count < maxTry) {
        count++;
        iTestResult.setStatus(ITestResult.FAILURE);
        log.warn("The test is repeated!!!");
        return true;
      } else {
        iTestResult.setStatus(ITestResult.FAILURE);
      }
    } else {
      iTestResult.setStatus(ITestResult.SUCCESS);
    }
    return false;
  }
}
```

```java
public class RetryTransformer implements IAnnotationTransformer {
  @Override
  public void transform(ITestAnnotation annotation, Class testClass,
                        Constructor testConstructor, Method testMethod) {
    annotation.setRetryAnalyzer(RetryAnalyzer.class);
  }
}
```

### DataProvider Pattern
Использование TestNG @DataProvider для параметризации тестов с различными наборами данных (позитивные/негативные
сценарии).

```java
@DataProvider(name = "Тестовые данные для негативного создания авто")
public Object[][] carDataNegative() {
  return new Object[][] {
          //Пустой тип двигателя
          {Car.builder().engineType("").mark("Hyundai").model("Solaris").price("4999.99").build(),
                  "Status: Invalid request data"},
          //Тип двигателя не соответствует допустимому значению
          {Car.builder().engineType("Trash").mark("Hyundai").model("Solaris").price("4999.99").build(),
                  "Status: Invalid request data"},
          //Пустая марка
          {Car.builder().engineType("Diesel").mark("").model("Discovery").price("10999.99").build(),
                  "Status: Invalid request data"},
          //Пустая модель
          {Car.builder().engineType("Electric").mark("Tesla").model("").price("9999999.99").build(),
                  "Status: Invalid request data"},
          //Отрицательная стоимость
          {Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("-5000.00").build(),
                  "Status: Invalid request data"},
          //Нулевая стоимость
          {Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("0.00").build(),
                  "Status: Invalid request data"},
          //Слишком высокая стоимость
          {Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("100000000000000000.00").
                  build(), "Status: Invalid request data"},
          //Нечисловое значение в атрибуте стоимость
          {Car.builder().engineType("Electric").mark("Hyundai").model("Solaris").price("Жига").build(),
                  "Status: Invalid request data"},
  };
}
```

---

## Чек-лист UI тестирования
### Модуль Authorization - Страница авторизации (`/`)

| № | Тест-кейс | Группа | Автор        | Статус реализации |
|---|-----------|--------|--------------|-------------------|
| АТ.01.01 | Проверка валидации страницы на наличие всех элементов | Positive, Regression | Малеваный А.В | Готов |
| АТ.01.02 | Авторизация с валидными кредами | Positive, Smoke, Regression | Малеваный А.В | Готов |
| АТ.01.03 | Валидация поля Email (без домена @) | Negative, Regression | Малеваный А.В | Готов |
| АТ.01.04 | Валидация поля Password (< 3 символов) | Negative, Regression | Малеваный А.В | Готов |
| АТ.01.05 | Валидация поля Password (> 8 символов) | Negative, Regression | Малеваный А.В | Готов |
| АТ.01.06 | Доступность действий для авторизованного пользователя | Positive, Regression, Smoke | Малеваный А.В | Готов |
| АТ.01.07 | Недоступность действий для неавторизованного пользователя | Negative, Regression, Smoke | Малеваный А.В | Готов |
| АТ.01.08 | Ошибки при пустых значениях Email и Password | Negative, Regression | Малеваный А.В | Готов |
| АТ.01.09 | Авторизация с пустым Email и валидным Password | Negative, Regression | Малеваный А.В | Готов |
| АТ.01.10 | Авторизация с пустым Password и валидным Email | Negative, Regression | Малеваный А.В | Готов |
| АТ.01.11 | Алерт после ввода невалидного Email | Negative, Regression | Малеваный А.В | Готов |
| АТ.01.12 | Алерт после ввода невалидного Password | Negative, Regression | Малеваный А.В | Готов |
| АТ.01.13 | Защита от SQL-инъекций в поле Password (6 пейлоадов) | Negative, Security, Regression | Малеваный А.В | Готов |

### Модуль All POST / ALL DELETE - Страница All POST (`/#/create/all`) + All DELETE (`/#/delete/all`)

| № | Тест-кейс                                               | Группа | Автор | Статус реализации |
|---|---------------------------------------------------------|--------|--------|--------|
| АТ.02.01 | **Полный цикл CRUD** (создание -> операции -> удаление) | Positive, E2E, Regression, Smoke |--------|--------|

#### Детализация E2E-сценария АТ.02.01:

| Шаг | Операция                       | Ожидаемый результат              |
|-----|--------------------------------|----------------------------------|
| 1   | Авторизация                    | Алерт "Successful authorization" |
| 2   | Создание пользователя          | Статус 201, получен User ID      |
| 3   | Пополнение баланса             | Статус 200, баланс увеличен      |
| 4   | Создание автомобиля            | Статус 201, получен Car ID       |
| 5   | Покупка авто пользователем     | Статус 200, авто привязано       |
| 6   | Продажа авто пользователем     | Статус 200, авто отвязано        |
| 7   | Создание дома                  | Статус 201, получен House ID     |
| 8   | Заселение пользователя в дом   | Статус 200                       |
| 9   | Выселение пользователя из дома | Статус 200                       |
| 10  | Удаление автомобиля            | Статус 204                       |
| 11  | Удаление дома                  | Статус 204                       |
| 12  | Удаление пользователя          | Статус 204                       |

### Модуль Cars

#### Создание автомобиля (Cars -> Create new)

| № | Тест-кейс | Группа | Автор | Статус реализации |
|---|-----------|--------|--------|--------|
| АТ.03.01 | Успешное создание нового авто (4 набора данных) | Positive, E2E, Regression, Smoke |--------|--------|
| АТ.03.02 | Создание нового авто c ошибкой (5 наборов данных) | Negative, E2E, Regression |--------|--------|
| АТ.03.03 | Проверка ввода в поле Price с помощью стрелок | Negative, E2E, Regression |--------|--------|

#### Таблица автомобилей (Cars -> Read all)

| №        | Тест-кейс                                 | Группа | Автор | Статус реализации |
|----------|-------------------------------------------|--------|--------|--------|
| АТ.03.04 | Проверка наличия атрибутов в таблице cars | Positive, E2E, Regression, Smoke |--------|--------|
| АТ.03.05 | Сортировка ID по возрастанию              | Positive, E2E, Regression |--------|--------|
| АТ.03.06 | Сортировка ID по убыванию                 | Positive, E2E, Regression |--------|--------|
| АТ.03.07 | Сортировка Price по возрастанию           | Positive, E2E, Regression |--------|--------|
| АТ.03.08 | Сортировка Price по убыванию              | Positive, E2E, Regression |--------|--------|
| АТ.03.09 | Сортировка Engine Type по алфавиту А-Я    | Positive, E2E, Regression |--------|--------|
| АТ.03.10 | Сортировка Engine Type по алфавиту Я-А    | Positive, E2E, Regression |--------|--------|
| АТ.03.11 | Сортировка Mark по алфавиту А-Я           | Positive, E2E, Regression |--------|--------|
| АТ.03.12 | Сортировка Mark по алфавиту Я-А           | Positive, E2E, Regression |--------|--------|
| АТ.03.13 | Сортировка Model по алфавиту А-Я          | Positive, E2E, Regression |--------|--------|
| АТ.03.14 | Сортировка Model по алфавиту Я-А          | Positive, E2E, Regression |--------|--------|
| АТ.03.15 | Проверка кнопки Reload                    | Positive, E2E, Regression |--------|--------|

#### Продажа или покупка авто (Cars -> Buy or Sell)

| № | Тест-кейс | Группа | Автор | Статус реализации |
|---|-----------|--------|--------|--------|
| АТ.03.16 | Позитивный ввод в поля idUser и idCar (2 набора данных) | Positive, E2E, Regression, Smoke |--------|--------|
| АТ.03.17 | Негативный ввод в поля idUser и idCar (3 набора данных) | Negative, E2E, Regression, Smoke |--------|--------|
| АТ.03.18 | Проверка ввода в поля UserId и CarId с помощью стрелок | Positive, E2E, Regression |--------|--------|
| АТ.03.19 | Проверка работы радиокнопки Buy | Positive, E2E, Regression |--------|--------|
| АТ.03.20 | Проверка работы радиокнопки Sell | Positive, E2E, Regression |--------|--------|
| АТ.03.21 | Проверка начального состояния радиокнопок | Positive, E2E, Regression |--------|--------|

### Модуль Houses

#### Создание дома (Houses -> Create new) — позитивные тесты

| № | Тест-кейс                                                                                                                            | Группа | Автор | Статус реализации |
|---|--------------------------------------------------------------------------------------------------------------------------------------|--------|--------|--------|
| АТ.04/3.12 | Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами                                         | Positive, Regression, Smoke | Малеваный А.В | Готов |
| АТ.04/3.13 | Проверка создания дома с заполнением всех полей валидными кредами (включая необязательные) + проверка через Houses -> Read One By ID | Positive, Regression, Smoke | Малеваный А.В | Готов |

#### Создание дома (Houses -> Create new) — негативные тесты

| № | Тест-кейс | Группа | Автор | Статус реализации |
|---|-----------|--------|--------|--------|
| АТ.04/4.14 | Проверка валидации полей с получением ошибок при вводе невалидных значений (DataProvider: 7 наборов данных) | Negative, Regression | Малеваный А.В | Готов |

#### Детализация DataProvider АТ.04/4.14:

| № | Невалидные данные                     | Описание                                                     |
|---|---------------------------------------|--------------------------------------------------------------|
| 1 | floors = -1, остальные поля валидны   | Отрицательное значение в поле 'Floors'                       |
| 2 | floors = 0, остальные поля валидны    | Нулевое значение в поле 'Floors'                             |
| 3 | price = -1.00, остальные поля валидны | Отрицательное значение в поле 'Price'                        |
| 4 | Warm and Covered Parking = -1         | Отрицательное значение в поле 'Warm and Covered Parking'     |
| 5 | Warm and Not Covered Parking = -1     | Отрицательное значение в поле 'Warm and Not Covered Parking' |
| 6 | Cold and Covered Parking = -1         | Отрицательное значение в поле 'Cold and Covered Parking'     |
| 7 | Cold and Not Covered Parking = -1     | Отрицательное значение в поле 'Cold and Not Covered Parking' |

#### Детализация E2E-проверки АТ.04/3.13:

| Шаг | Операция                                               | Ожидаемый результат                             |
|-----|--------------------------------------------------------|-------------------------------------------------|
| 1   | Авторизация                                            | Алерт "Successful authorization"                |
| 2   | Открытие страницы Houses -> Create new                 | Страница загружена                              |
| 3   | Заполнение всех полей (floors, price, 4 типа парковок) | Поля заполнены                                  |
| 4   | Клик по кнопке "PUSH TO API"                           | Статус "Status: Successfully pushed, code: 201" |
| 5   | Получение ID нового дома                               | Текст соответствует шаблону "New house ID: \d+" |
| 6   | Переход на страницу Houses -> Read One By ID           | Страница загружена                              |
| 7   | Ввод ID дома в поле поиска и клик "Read"               | Найденный дом отображён                         |
| 8   | Проверка ID найденного дома                            | ID совпадает с созданным                        |

### Модуль Users

#### Таблица пользователей (Users -> Read all)

| №        | Тест-кейс                                                          | Группа                           | Автор | Статус реализации |
|----------|--------------------------------------------------------------------|----------------------------------|--------|--------|
| АТ.05.01 | Проверка открытия страницы "Users -> Read all" со всеми атрибутами | Positive, E2E, Regression, Smoke |--------|--------|
| АТ.05.02 | Проверка ховер эффекта кнопок                                      | Positive, Regression             |--------|--------|
| АТ.05.03 | Проверка сортировки пользователей по возрастанию ID                | Positive, E2E, Regression        |--------|--------|
| АТ.05.04 | Проверка сортировки пользователей по убыванию ID                   | Positive, E2E, Regression        |--------|--------|
| АТ.05.05 | Проверка сортировки пользователей по имени от A-Z                  | Positive, E2E, Regression        |--------|--------|
| АТ.05.06 | Проверка сортировки пользователей по имени от Z-A                  | Positive, E2E, Regression        |--------|--------|
| АТ.05.07 | Проверка сортировки пользователей по фамилии от A-Z                | Positive, E2E, Regression        |--------|--------|
| АТ.05.08 | Проверка сортировки пользователей по фамилии от Z-A                | Positive, E2E, Regression        |--------|--------|
| АТ.05.09 | Проверка сортировки пользователей по возрастанию возраста          | Positive, E2E, Regression        |--------|--------|
| АТ.05.10 | Проверка сортировки пользователей по убыванию возраста             | Positive, E2E, Regression        |--------|--------|
| АТ.05.11 | Проверка сортировки пользователей по полу от A-Z                   | Positive, E2E, Regression        |--------|--------|
| АТ.05.12 | Проверка сортировки пользователей по полу от Z-A                   | Positive, E2E, Regression        |--------|--------|
| АТ.05.13 | Проверка сортировки пользователей по возрастанию наличности        | Positive, E2E, Regression        |--------|--------|
| АТ.05.14 | Проверка сортировки пользователей по убыванию наличности           | Positive, E2E, Regression        |--------|--------|
| АТ.05.15 | Проверка обновления таблицы                                        | Positive, E2E, Regression        |--------|--------|

#### Таблица пользователей и их машин (Users -> Read user with cars)

| №        | Тест-кейс                                                                     | Группа                           | Автор | Статус реализации |
|----------|-------------------------------------------------------------------------------|----------------------------------|--------|--------|
| АТ.06.01 | Проверка открытия страницы "Users -> Read user with cars" со всеми атрибутами | Positive, E2E, Regression, Smoke |--------|--------|
| АТ.06.02 | Проверка ввода в инпут айди напрямую и через стрелочки                        | Positive, Regression, Smoke      |--------|--------|
| АТ.06.03 | Проверка ховер-эффектов и тултипов                                            | Positive, Regression             |--------|--------|
| АТ.06.04 | Проверка отображения данных по пользователю без машины                        | Positive, E2E, Regression        |--------|--------|
| АТ.06.05 | Проверка отображения данных по пользователю c одной машиной                   | Positive, E2E, Regression        |--------|--------|

#### Вызов данных по пользователям с машинами (Users -> Read user with cars) — негативные тесты

| №        | Тест-кейс                                                                                                   | Группа               | Автор | Статус реализации |
|----------|-------------------------------------------------------------------------------------------------------------|----------------------|
| АТ.06.06 | Проверка валидации полей с получением ошибок при вводе невалидных значений (DataProvider: 4 наборов данных) | Negative, Regression |

#### Детализация DataProvider АТ.06.07:

| № | Невалидные данные                                                | Описание                              | Автор | Статус реализации |
|---|------------------------------------------------------------------|---------------------------------------|--------|--------|
| 1 | userId = "999999", ожидаемый статус "Status: 204 user not found" | Запрос данных по несуществующему айди |--------|--------|
| 2 | userId = "-999999", ожидаемый статус Status:  Invalid input"     | Запрос данных по отрицательному айди  |--------|--------|
| 3 | userId = ",", ожидаемый статус "Status: 204 user not found"      | Запрос данных по невалидному айди     |--------|--------|
| 4 | userId = "", ожидаемый статус "Status: 204 user not found"       | Запрос данных по пустому айди         |--------|--------|

### Статистика покрытия UI тестов

| Модуль | Позитивные | Негативные | Безопасность | E2E | Всего |
|--------|------------|------------|--------------|-----|-------|
---
## Чек-лист API тестирования

### Модуль Authorization (API)

#### Авторизация через JSON

| №         | Тест-кейс | Метод | Эндпоинт | Группа | Автор | Статус реализации |
|-----------|-----------|-------|--------|--------|--------|-----------------|
| API.01.01 | Авторизация пользователя с валидными данными (JSON) | POST |`/login`|Positive, Regression, Smoke|Малеваный А.В|Готов|
| API.01.02 | Проверка получения access_token в ответе | POST | `/login` | Positive, Regression, Smoke | Малеваный А.В | Готов   |
| API.01.03 | Авторизация с невалидным email | POST | `/login` | Negative, Regression | Малеваный А.В | Готов |
| API.01.04 | Авторизация с невалидным паролем | POST | `/login` | Negative, Regression | Малеваный А.В | Готов  |
| API.01.05 | Авторизация с пустым телом запроса | POST | `/login` | Negative, Regression | Малеваный А.В | Готов |

### Модуль Users (API)

#### CRUD операции с пользователями

| № | Тест-кейс | Метод | Эндпоинт | Группа | Автор    | Статус реализации |
|---|-----------|-------|----------|--------|----------|--------|
| API.02.01 | Создание пользователя с валидными данными | POST | `/user` | Positive, Smoke | -------- |--------|
| API.02.02 | Получение пользователя по ID | GET | `/user/{userId}` | Positive | -------- |--------|
| API.02.03 | Получение списка всех пользователей | GET | `/users` | Positive | -------- |--------|
| API.02.04 | Получение информации о пользователе с имуществом | GET | `/user/{userId}/info` | Positive | -------- |--------|
| API.02.05 | Обновление данных пользователя | PUT | `/user/{userId}` | Positive | -------- |--------|
| API.02.06 | Удаление пользователя | DELETE | `/user/{userId}` | Positive | -------- |--------|
| API.02.07 | Создание пользователя с невалидными данными | POST | `/user` | Negative | -------- |--------|
| API.02.08 | Получение несуществующего пользователя | GET | `/user/{userId}` | Negative | -------- |--------|
| API.02.09 | Обновление несуществующего пользователя | PUT | `/user/{userId}` | Negative | -------- |--------|
| API.02.10 | Удаление несуществующего пользователя | DELETE | `/user/{userId}` | Negative | -------- |--------|
| API.02.11 | Создание пользователя без обязательных полей | POST | `/user` | Negative | -------- |--------|
| API.02.12 | Создание пользователя с невалидным возрастом | POST | `/user` | Negative | -------- |--------|
| API.02.13 | Создание пользователя с отрицательным балансом | POST | `/user` | Negative | -------- |--------|

### Модуль Cars (API)

#### CRUD операции с автомобилями

| № | Тест-кейс | Метод                     | Эндпоинт              | Группа | Автор        | Статус реализации |
|---|-----------|---------------------------|-----------------------|--------|--------------|-----------------|
| API.03.01 | Проверка цикла CRUD авто | POST / GET / PUT / DELETE | `/car`/`/car/{carId}` | Positive, Smoke | Пермяков Е.С | Готово |
| API.03.02 | Проверка списка авто с помощью GET /cars | GET | `/cars`               | Positive | Пермяков Е.С | Готово |
| API.03.03 | Проверка негативного создания авто через API | POST | `/car`                | Negative | Пермяков Е.С | Готово |
| API.03.04 | Удаление несуществующего авто с помощью API | DELETE | `/car/{carId}`        | Negative | Пермяков Е.С | Готово |
| API.03.05 | Запрос несуществующего авто с помощью API | GET | `/car/{carId}`        | Negative | Пермяков Е.С | Готово |
| API.03.06 | Изменение несуществующего авто с помощью API | PUT | `/car/{carId}`        | Negative | Пермяков Е.С | Готово |
| API.03.07 | Проверка обновления авто через API с невалидным телом | PUT | `/car/{carId}`        | Negative | Пермяков Е.С | Готово |
| API.03.08 | Получение авто пользователя | GET | `/user/{userId}/cars` | Positive | Пермяков Е.С | Готово |
| API.03.09 | Запрос несуществующего пользователя с авто | GET | `/user/{userId}/cars`        | Negative | Пермяков Е.С | Готово |

### Модуль Houses (API)

#### CRUD операции с домами

| № | Тест-кейс | Метод | Эндпоинт | Группа | Автор         | Статус реализации |
|---|-----------|-------|----------|--------|---------------|--------|
| API.04.01 | Создание дома с валидными данными | POST | `/house` | Positive, Smoke | Малеваный А.В | Взят в работу |
| API.04.02 | Получение дома по ID | GET | `/house/{houseId}` | Positive | Малеваный А.В | Взят в работу |
| API.04.03 | Получение списка всех домов | GET | `/houses` | Positive | Малеваный А.В | Взят в работу |
| API.04.04 | Обновление данных дома | PUT | `/house/{houseId}` | Positive | Малеваный А.В | Взят в работу |
| API.04.05 | Удаление дома | DELETE | `/house/{houseId}` | Positive | Малеваный А.В | Взят в работу |
| API.04.06 | Создание дома с невалидными данными | POST | `/house` | Negative | Малеваный А.В | Взят в работу |
| API.04.07 | Получение несуществующего дома | GET | `/house/{houseId}` | Negative | Малеваный А.В | Взят в работу |
| API.04.08 | Обновление несуществующего дома | PUT | `/house/{houseId}` | Negative | Малеваный А.В | Взят в работу |
| API.04.09 | Удаление несуществующего дома | DELETE | `/house/{houseId}` | Negative | Малеваный А.В | Взят в работу |
| API.04.10 | Создание дома с отрицательным количеством этажей | POST | `/house` | Negative | Малеваный А.В | Взят в работу |
| API.04.11 | Создание дома с отрицательной ценой | POST | `/house` | Negative | Малеваный А.В | Взят в работу |
| API.04.12 | Создание дома с невалидными парковочными местами | POST | `/house` | Negative | Малеваный А.В | Взят в работу |

### Модуль User-Car Relations (API)

#### Покупка и продажа автомобилей

| № | Тест-кейс | Метод | Эндпоинт | Группа | Автор | Статус реализации |
|---|-----------|-------|----------|--------|--------|--------|
| API.05.01 | Покупка автомобиля пользователем | POST | `/user/{userId}/buyCar/{carId}` | Positive, Smoke |--------|--------|
| API.05.02 | Продажа автомобиля пользователем | POST | `/user/{userId}/sellCar/{carId}` | Positive |--------|--------|
| API.05.03 | Покупка автомобиля с недостаточным балансом | POST | `/user/{userId}/buyCar/{carId}` | Negative |--------|--------|
| API.05.04 | Покупка несуществующего автомобиля | POST | `/user/{userId}/buyCar/{carId}` | Negative |
| API.05.05 | Продажа автомобиля, который не принадлежит пользователю | POST | `/user/{userId}/sellCar/{carId}` | Negative |--------|--------|
| API.05.06 | Покупка автомобиля несуществующим пользователем | POST | `/user/{userId}/buyCar/{carId}` | Negative |--------|--------|
| API.05.07 | Продажа несуществующего автомобиля | POST | `/user/{userId}/sellCar/{carId}` | Negative |--------|--------|

### Модуль User-House Relations (API)

#### Заселение и выселение пользователей

| № | Тест-кейс | Метод | Эндпоинт | Группа | Автор | Статус реализации |
|---|-----------|-------|----------|--------|--------|--------|
| API.06.01 | Заселение пользователя в дом | POST | `/house/{houseId}/settle/{userId}` | Positive, Smoke |--------|--------|
| API.06.02 | Выселение пользователя из дома | POST | `/house/{houseId}/evict/{userId}` | Positive |--------|--------|
| API.06.03 | Заселение в несуществующий дом | POST | `/house/{houseId}/settle/{userId}` | Negative |--------|--------|
| API.06.04 | Выселение из несуществующего дома | POST | `/house/{houseId}/evict/{userId}` | Negative |--------|--------|
| API.06.05 | Заселение несуществующего пользователя | POST | `/house/{houseId}/settle/{userId}` | Negative |--------|--------|
| API.06.06 | Выселение несуществующего пользователя | POST | `/house/{houseId}/evict/{userId}` | Negative |--------|--------|
| API.06.07 | Заселение пользователя без достаточного баланса | POST | `/house/{houseId}/settle/{userId}` | Negative |--------|--------|

### Модуль User Money/Loan (API)

#### Управление балансом и кредитами

| № | Тест-кейс                                   | Метод | Эндпоинт | Группа | Автор | Статус реализации |
|---|---------------------------------------------|-------|----------|--------|--------|--------|
| API.07.01 | Начисление денег пользователю               | POST | `/user/{userId}/money/{amount}` | Positive, Smoke |--------|--------|
| API.07.02 | Запрос кредита пользователем                | POST | `/user/{userId}/loan/{amount}` | Positive |--------|--------|
| API.07.03 | Начисление денег несуществующему пользователю | POST | `/user/{userId}/money/{amount}` | Negative |--------|--------|
| API.07.04 | Запрос кредита несуществующим пользователем | POST | `/user/{userId}/loan/{amount}` | Negative |--------|--------|
| API.07.05 | Начисление отрицательной суммы              | POST | `/user/{userId}/money/{amount}` | Negative |--------|--------|
| API.07.06 | Запрос кредита с отрицательной суммой       | POST | `/user/{userId}/loan/{amount}` | Negative |--------|--------|
| API.07.07 | Начисление нулевой суммы                    | POST | `/user/{userId}/money/{amount}` | Negative |--------|--------|

### Модуль Logging (API)

#### Работа с логами

| № | Тест-кейс | Метод | Эндпоинт | Группа | Автор | Статус реализации |
|---|-----------|-------|----------|--------|--------|--------|
| API.08.01 | Получение информации о логах | GET | `/logs/info` | Positive |--------|--------|
| API.08.02 | Получение списка файлов логов | GET | `/logs/list` | Positive |--------|--------|
| API.08.03 | Загрузка логов из файла | GET | `/logs/buffer/load` | Positive |--------|--------|
| API.08.04 | Загрузка архивных логов | GET | `/logs/buffer/load/archive/{dateAndPart}` | Positive |--------|--------|
| API.08.05 | Чтение всех загруженных логов | GET | `/logs/buffer/read/all` | Positive |--------|--------|
| API.08.06 | Чтение логов постранично | GET | `/logs/buffer/read/{page}` | Positive |--------|--------|
| API.08.07 | Скачивание логов потоком | GET | `/logs/download` | Positive |--------|--------|
| API.08.08 | Скачивание архивных логов потоком | GET | `/logs/download/archive/{dateAndPart}` | Positive |--------|--------|
| API.08.09 | Загрузка несуществующих архивных логов | GET | `/logs/buffer/load/archive/{dateAndPart}` | Negative |--------|--------|
| API.08.10 | Скачивание несуществующих архивных логов | GET | `/logs/download/archive/{dateAndPart}` | Negative |--------|--------|
| API.08.11 | Чтение несуществующей страницы логов | GET | `/logs/buffer/read/{page}` | Negative |--------|--------|

### Модуль Criminal Records (API)

#### Проверка судимостей пользователей

| № | Тест-кейс | Метод | Эндпоинт | Группа | Автор | Статус реализации |
|---|-----------|-------|----------|--------|--------|--------|
| API.09.01 | Получение списка судимостей пользователя | GET | `/user/{userId}/criminal_records` | Positive |--------|--------|
| API.09.02 | Получение информации о судимости по ID | GET | `/userCriminalRecord/{userId}` | Positive |--------|--------|
| API.09.03 | Получение судимостей несуществующего пользователя | GET | `/user/{userId}/criminal_records` | Negative |--------|--------|
| API.09.04 | Получение несуществующей судимости | GET | `/userCriminalRecord/{userId}` | Negative |--------|--------|

### Модуль API User Management (API)

#### Управление пользователями API

| № | Тест-кейс | Метод | Эндпоинт | Группа | Автор | Статус реализации |
|---|-----------|-------|----------|--------|--------|--------|
| API.10.01 | Создание пользователя API | POST | `/api/user/save` | Positive |--------|--------|
| API.10.02 | Создание роли API | POST | `/api/role/save` | Positive |--------|--------|
| API.10.03 | Получение списка пользователей API | GET | `/api/users` | Positive |--------|--------|
| API.10.04 | Создание пользователя API с невалидными данными | POST | `/api/user/save` | Negative |--------|--------|
| API.10.05 | Создание роли API с невалидными данными | POST | `/api/role/save` | Negative |--------|--------|


### Общие негативные сценарии для всех модулей

| № | Тест-кейс | Группа | Автор | Статус реализации |
|---|-----------|--------|--------|--------|
| API.99.01 | Запрос без авторизации (если требуется) | Negative, Security |--------|--------|
| API.99.02 | Запрос с невалидным JWT токеном | Negative, Security |--------|--------|
| API.99.03 | Запрос с истекшим JWT токеном | Negative, Security |--------|--------|
| API.99.04 | Запрос с невалидным Content-Type | Negative |--------|--------|
| API.99.05 | Запрос с невалидным JSON телом | Negative |--------|--------|
| API.99.06 | Запрос с превышением размера тела | Negative |--------|--------|
| API.99.07 | SQL инъекция в параметрах запроса | Negative, Security |--------|--------|
| API.99.08 | XSS атака в параметрах запроса | Negative, Security |--------|--------|

### Статистика покрытия API тестов

| Модуль | Позитивные | Негативные | Всего |
|--------|------------|------------|-------|

---

---
## Команда проекта
| Роль        | Имя           | Ответственность |
|-------------|---------------|-----------------|
| QA Engineer | Антон М.      | ...             |
| QA Engineer | Олег П.       | ...             |
| QA Engineer | Егор П.       | ...             |
| QA Engineer | Александра З. | ...             |
| QA Engineer | Анатолий С.   | ...             |
| QA Engineer | Анна К.       | ...             |