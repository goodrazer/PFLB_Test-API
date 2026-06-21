# Фреймворк автоматизированного тестирования для веб-приложения PFLB Test-API
## Описание проекта

**PFLB Test-API** - веб-приложение с REST API и UI-интерфейсом для управления сущностями:

- **Users** - пользователи (создание, пополнение баланса, привязка авто/дома, кредиты)
- **Cars** - автомобили (создание, покупка/продажа)
- **Houses** - дома (создание с парковками, заселение/выселение)

...(в разработке)

---
## Технологический стек

| Категория | Технология  | Версия         |
|-----------|-------------|----------------|
| Язык | Java        | 17             |
| Сборщик | Maven       | 3.x            |
| UI-фреймворк | Selenide    | 7.16.2         |
| API-тестирование | RestAssured | 6.00           |
| Тестовый фреймворк | TestNG      | 7.12.0         |
| Отчётность | Allure      | 2.29.0         |
| Логирование | Log4j2      | 2.23.1         |
| Утилиты | Lombok      | 1.18.42        |
| Генерация данных | JavaFaker   | 1.0.2          |
| Валидация JSON | GSON        | 2.14.0 |

---
## Структура проекта

```text
PFLB_Test-API/
│
├── pom.xml # Конфигурация Maven
├── README.md # Документация проекта
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── api/ # API-часть фреймворка
│ │ │ │ ├── adapters/
│ │ │ │ │ BaseAdapter.java # Базовый адаптер для API-запросов
│ │ │ │ │
│ │ │ │ └── models/
│ │ │ │ Result.java # Модель результата API-ответа
│ │ │ │
│ │ │ └── ui/ # UI-часть фреймворка
│ │ │ ├── dto/ # Value Objects (DTO)
│ │ │ │ Car.java # DTO автомобиля
│ │ │ │ House.java # DTO дома
│ │ │ │ Parking.java # DTO парковки
│ │ │ │ User.java # DTO пользователя
│ │ │ │ UserCar.java # DTO связи User-Car
│ │ │ │
│ │ │ ├── pages/ # Page Objects
│ │ │ │ AllDeletePage.java # Страница "All DELETE"
│ │ │ │ AllPostPage.java # Страница "All POST"
│ │ │ │ BasePage.java # Базовая страница
│ │ │ │ CarsBuyOrSellCarPage.java # Покупка/продажа авто
│ │ │ │ CarsCreateNewPage.java # Создание авто
│ │ │ │ CarsReadAllPage.java # Список всех авто
│ │ │ │ HousesCreateNewPage.java # Создание дома
│ │ │ │ HousesReadAllPage.java # Список всех домов
│ │ │ │ HousesReadOneByIdPage.java # Просмотр дома по ID
│ │ │ │ HousesSettleOrEvictUser.java # Заселение/выселение
│ │ │ │ LoginPage.java # Страница авторизации
│ │ │ │ SwaggerDevJsDeprecatedPage.java
│ │ │ │ SwaggerDevMvcPage.java
│ │ │ │ SwaggerMasterMvcPage.java
│ │ │ │ UsersAddMoneyPage.java # Пополнение баланса
│ │ │ │ UsersBuyOrSellCarPage.java # Покупка/продажа авто
│ │ │ │ UsersCreateNewPage.java # Создание пользователя
│ │ │ │ UsersIssueALoanPage.java # Выдача кредита
│ │ │ │ UsersReadAllPage.java # Список всех пользователей
│ │ │ │ UsersReadUserWithCarsPage.java
│ │ │ │ UsersSettleToHousePage.java # Заселение в дом
│ │ │ │
│ │ │ ├── steps/ # Step-классы
│ │ │ │ BaseStep.java # Базовый шаг
│ │ │ │ LoginStep.java # Шаг авторизации
│ │ │ │ └── wrappers/
│ │ │ │ CarWrapper.java # Wrapper для авто
│ │ │ │
│ │ │ └── wrappers/ # UI Wrappers
│ │ │ ButtonWrapper.java # Wrapper для кнопок
│ │ │ InputCars.java # Wrapper для полей авто
│ │ │ InputHousesCreateNewOneTable.java
│ │ │ InputWrapper.java # Универсальный wrapper ввода
│ │ │ RadioWrapper.java # Wrapper для radio-кнопок
│ │ │
│ │ └── resources/
│ │ └── schema/
│ │ createCarSchema.json # JSON-схема для валидации
│ │
│ └── test/
│ ├── java/
│ │ ├── api/
│ │ │ └── tests/
│ │ │ CarsApitTest.java # API-тесты для Cars
│ │ │
│ │ └── ui/
│ │ ├── tests/ # UI-тесты
│ │ │ AllPostTest.java # E2E-тест полного цикла
│ │ │ BaseTest.java # Базовый тестовый класс
│ │ │ CarsTest.java # Тесты для Cars
│ │ │ HousesTest.java # Тесты для Houses
│ │ │ LoginTest.java # Тесты авторизации
│ │ │
│ │ └── utils/ # Утилиты
│ │ PropertyReader.java # Чтение config.properties
│ │ RetryAnalyzer.java # Повторный запуск тестов
│ │ RetryTransformer.java # Трансформер для Retry
│ │ TestListener.java # Слушатель событий TestNG
│ │
│ └── resources/
│ allure.properties # Настройки Allure
│ config.properties # Конфигурация (email, password)
│ CrossBrowserTestSuite.xml # Suite для кросс-браузерных тестов
│ log4j2.xml # Конфигурация логирования
│ NegativeTestSuite.xml # Suite для негативных тестов
│ PositeveTestSuite.xml # Suite для позитивных тестов
│ RegressionTestSuite.xml # Suite для регрессионных тестов
│ SecurityTestSuite.xml # Suite для тестов безопасности
│ SmokeTestSuite.xml # Suite для smoke-тестов
```
---

## Архитектурные паттерны

### Page Object Model (POM)

Каждая страница представлена отдельным классом, инкапсулирующим локаторы и действия:

- `LoginPage` - страница авторизации
- `AllPostPage` - агрегированная страница создания сущностей
- `AllDeletePage` - страница удаления сущностей
- `Users*Page`, `Cars*Page`, `Houses*Page` — специализированные страницы модулей

### Chain of Invocations

Методы возвращают `this` для построения цепочек вызовов:

```java
allPostPage.openPage()
           .isPageOpened()
           .createUser(user)
           .addMoneyToUser(userId, 10000)
           .createCar(car);
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

### Steps
Часть бизнес-логики вынесена в Step-классы (LoginStep, BaseStep) для переиспользования между тестами.

### Loadable Page
Каждая страница проверяет свою загрузку через метод isPageOpened() с retry-логикой до нескольких попыток.

### Retry Mechanism
Автоматический перезапуск упавших тестов через RetryAnalyzer + RetryTransformer

### DataProvider Pattern
Использование TestNG @DataProvider для параметризации тестов с различными наборами данных (позитивные/негативные сценарии).

---

## Чек-лист UI тестирования
### Модуль Authorization - Страница авторизации (`/`)

| № | Тест-кейс | Группа |
|---|-----------|--------|
| АТ.01.01 | Проверка валидации страницы на наличие всех элементов | Positive, Regression |
| АТ.01.02 | Авторизация с валидными кредами | Positive, Smoke, Regression |
| АТ.01.03 | Валидация поля Email (без домена @) | Negative, Regression |
| АТ.01.04 | Валидация поля Password (< 3 символов) | Negative, Regression |
| АТ.01.05 | Валидация поля Password (> 8 символов) | Negative, Regression |
| АТ.01.06 | Доступность действий для авторизованного пользователя | Positive, Regression, Smoke |
| АТ.01.07 | Недоступность действий для неавторизованного пользователя | Negative, Regression, Smoke |
| АТ.01.08 | Ошибки при пустых значениях Email и Password | Negative, Regression |
| АТ.01.09 | Авторизация с пустым Email и валидным Password | Negative, Regression |
| АТ.01.10 | Авторизация с пустым Password и валидным Email | Negative, Regression |
| АТ.01.11 | Алерт после ввода невалидного Email | Negative, Regression |
| АТ.01.12 | Алерт после ввода невалидного Password | Negative, Regression |
| АТ.01.13 | Защита от SQL-инъекций в поле Password (6 пейлоадов) | Negative, Security, Regression |

### Модуль All POST / ALL DELETE - Страница All POST (`/#/create/all`) + All DELETE (`/#/delete/all`)

| № | Тест-кейс                                               | Группа |
|---|---------------------------------------------------------|--------|
| АТ.02.01 | **Полный цикл CRUD** (создание -> операции -> удаление) | Positive, E2E, Regression, Smoke |

#### Детализация E2E-сценария АТ.02.01:

| Шаг | Операция | Ожидаемый результат |
|-----|----------|---------------------|
| 1 | Авторизация | Алерт "Successful authorization" |
| 2 | Создание пользователя | Статус 201, получен User ID |
| 3 | Пополнение баланса | Статус 200, баланс увеличен |
| 4 | Создание автомобиля | Статус 201, получен Car ID |
| 5 | Покупка авто пользователем | Статус 200, авто привязано |
| 6 | Продажа авто пользователем | Статус 200, авто отвязано |
| 7 | Создание дома | Статус 201, получен House ID |
| 8 | Заселение пользователя в дом | Статус 200 |
| 9 | Выселение пользователя из дома | Статус 200 |
| 10 | Удаление автомобиля | Статус 204 |
| 11 | Удаление дома | Статус 204 |
| 12 | Удаление пользователя | Статус 204 |

### Модуль Cars

#### Создание автомобиля (Cars -> Create new)

| № | Тест-кейс | Группа |
|---|-----------|--------|
| АТ.03.01 | Успешное создание нового авто (4 набора данных) | Positive, E2E, Regression, Smoke |
| АТ.03.02 | Создание нового авто c ошибкой (5 наборов данных) | Negative, E2E, Regression |
| АТ.03.03 | Проверка ввода в поле Price с помощью стрелок | Negative, E2E, Regression |

#### Таблица автомобилей (Cars -> Read all)

| № | Тест-кейс | Группа |
|---|-----------|--------|
| АТ.03.04 | Проверка наличия атрибутов в таблице cars | Positive, E2E, Regression, Smoke |
| АТ.03.05 | Сортировка ID по возрастанию | Positive, E2E, Regression |
| АТ.03.06 | Сортировка ID по убыванию | Positive, E2E, Regression |
| АТ.03.07 | Сортировка Price по возрастанию | Positive, E2E, Regression |
| АТ.03.08 | Сортировка Price по убыванию | Positive, E2E, Regression |
| АТ.03.09 | Сортировка Engine Type по алфавиту А-Я | Positive, E2E, Regression |
| АТ.03.10 | Сортировка Engine Type по алфавиту Я-А | Positive, E2E, Regression |
| АТ.03.15 | Проверка кнопки Reload | Positive, E2E, Regression |

#### Покупка/продажа автомобиля (Cars -> Buy or Sell)

| № | Тест-кейс | Группа |
|---|-----------|--------|
| АТ.03.16 | Позитивный ввод в поля idUser и idCar (2 набора данных) | Positive, E2E, Regression, Smoke |
| АТ.03.17 | Негативный ввод в поля idUser и idCar (3 набора данных) | Negative, E2E, Regression, Smoke |
| АТ.03.18 | Проверка ввода в поля UserId и CarId с помощью стрелок | Positive, E2E, Regression |
| АТ.03.19 | Проверка работы радиокнопки Buy | Positive, E2E, Regression |
| АТ.03.20 | Проверка работы радиокнопки Sell | Positive, E2E, Regression |
| АТ.03.21 | Проверка начального состояния радиокнопок | Positive, E2E, Regression |

### Модуль Houses

#### Создание дома (Houses -> Create new) — позитивные тесты

| № | Тест-кейс                                                                                                                            | Группа |
|---|--------------------------------------------------------------------------------------------------------------------------------------|--------|
| АТ.04/3.12 | Проверка создания дома с заполнением обязательных полей {floors} и {price} валидными кредами                                         | Positive, Regression, Smoke |
| АТ.04/3.13 | Проверка создания дома с заполнением всех полей валидными кредами (включая необязательные) + проверка через Houses -> Read One By ID | Positive, Regression, Smoke |

#### Создание дома (Houses -> Create new) — негативные тесты

| № | Тест-кейс | Группа |
|---|-----------|--------|
| АТ.04/4.14 | Проверка валидации полей с получением ошибок при вводе невалидных значений (DataProvider: 7 наборов данных) | Negative, Regression |

#### Детализация DataProvider АТ.04/4.14:

| № | Невалидные данные | Описание |
|---|-------------------|----------|
| 1 | floors = -1, остальные поля валидны | Отрицательное значение в поле 'Floors' |
| 2 | floors = 0, остальные поля валидны | Нулевое значение в поле 'Floors' |
| 3 | price = -1.00, остальные поля валидны | Отрицательное значение в поле 'Price' |
| 4 | Warm and Covered Parking = -1 | Отрицательное значение в поле 'Warm and Covered Parking' |
| 5 | Warm and Not Covered Parking = -1 | Отрицательное значение в поле 'Warm and Not Covered Parking' |
| 6 | Cold and Covered Parking = -1 | Отрицательное значение в поле 'Cold and Covered Parking' |
| 7 | Cold and Not Covered Parking = -1 | Отрицательное значение в поле 'Cold and Not Covered Parking' |

#### Детализация E2E-проверки АТ.04/3.13:

| Шаг | Операция                                               | Ожидаемый результат |
|-----|--------------------------------------------------------|---------------------|
| 1 | Авторизация                                            | Алерт "Successful authorization" |
| 2 | Открытие страницы Houses -> Create new                 | Страница загружена |
| 3 | Заполнение всех полей (floors, price, 4 типа парковок) | Поля заполнены |
| 4 | Клик по кнопке "PUSH TO API"                           | Статус "Status: Successfully pushed, code: 201" |
| 5 | Получение ID нового дома                               | Текст соответствует шаблону "New house ID: \d+" |
| 6 | Переход на страницу Houses -> Read One By ID           | Страница загружена |
| 7 | Ввод ID дома в поле поиска и клик "Read"               | Найденный дом отображён |
| 8 | Проверка ID найденного дома                            | ID совпадает с созданным |
---

## Чек лист API тестирования
### В процессе...

---
## Статистика покрытия

| Модуль | Позитивные | Негативные | Безопасность | E2E | Всего |
|--------|-----------|-----------|-------------|-----|-------|

---
## Команда проекта
| Роль | Имя           | Ответственность |
|------|---------------|-----------------|
| QA Engineer | Антон М.      | ...             |
| QA Engineer | Олег П.       | ...             |
| QA Engineer | Егор П.       | ...             |
| QA Engineer | Александра З. | ...             |
| QA Engineer | Анатолий С.   | ...             |
| QA Engineer | Анна К.       | ...             |