# Автоматизированные тесты форм DemoQA

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/)
[![Selenide](https://img.shields.io/badge/Selenide-7.16.2-blue.svg)](https://selenide.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5-green.svg)](https://junit.org/junit5/)
[![JavaFaker](https://img.shields.io/badge/JavaFaker-1.0.2-purple.svg)](https://github.com/BrunoBonacci/java-faker)
[![Allure](https://img.shields.io/badge/Allure-2.32.0-red.svg)](https://allurereport.org/)
[![Jenkins](https://img.shields.io/badge/Jenkins-CI-blue.svg)](https://www.jenkins.io/)

> UI-автоматизация веб-форм [DemoQA](https://demoqa.com) на Selenide и JUnit 5 с генерацией случайных данных через JavaFaker, отчётностью Allure Reports и запуском в Jenkins. (обновлено 08.11.2026)

---

## 📋 Содержание

- [Обзор](#обзор)
- [Технологии](#технологии)
- [Структура проекта](#структура-проекта)
- [Покрытие тестами](#покрытие-тестами)
- [Генерация данных](#генерация-данных)
- [Начало работы](#начало-работы)
- [Запуск тестов](#запуск-тестов)
- [Принятые решения](#принятые-решения)
- [Лучшие практики](#лучшие-практики)
- [CI/CD (Jenkins)](#cicd-jenkins)

---

## 🎯 Обзор

Проект демонстрирует современное UI-автоматизированное тестирование веб-форм:

- **Регистрационная форма** — заполнение, валидация обязательных полей, проверка результата
- **Текстовое поле (Text Box)** — обработка полей ввода и проверка вывода данных

Набор тестов охватывает успешные сценарии, пограничные случаи и негативные сценарии. Все валидные данные генерируются случайно при каждом запуске через JavaFaker, поэтому тесты устойчивы к дублированию и проверяют форму на «живых» значениях.

---

## 🛠 Технологии

| Категория | Технология | Версия |
|------|------|---------|
| **Язык** | Java | 17 |
| **Тестовый фреймворк** | JUnit Jupiter | 5.11.4 |
| **Обёртка Selenium** | Selenide | 7.16.2 |
| **Генерация данных** | JavaFaker | 1.0.2 |
| **Отчётность** | Allure Reports | 2.32.0 (allure-selenide) |
| **Сборка** | Gradle | 9.3.0 |
| **Браузер** | Chrome | В облачном Selenoid (`selenoid.qa.guru`) |

---

## 📁 Структура проекта

```
java-reigstration-form-tests-page-object-rand-data/
├── build.gradle                          # Конфигурация сборки и зависимости
├── README.MD                             # Этот файл
├── LICENSE                               # Лицензия
└── src/
    └── test/
        ├── java/
        │   ├── pages/
        │   │   ├── TextBoxPage.java      # Page Object: форма Text Box
        │   │   ├── RegistrationFormPage.java  # Page Object: форма регистрации
        │   │   └── components/           # Сложные элементы, вынесенные отдельно
        │   │       ├── DatePicker.java   # Выбор даты рождения
        │   │       └── GenderRadio.java  # Группа radio-кнопок пола
        │   ├── testdata/
        │   │   └── TestData.java         # Фиксированные невалидные и граничные данные
        │   ├── utils/
        │   │   └── FakeDataProvider.java # Генерация случайных валидных данных (JavaFaker)
        │   └── tests/
        │       ├── TestBase.java         # Базовая конфигурация тестов
        │       ├── RegistrationFormTests.java  # Тесты регистрационной формы
        │       └── TextBoxTests.java     # Тесты текстового поля
        └── resources/
            └── test.png                  # Файл для проверки загрузки
```

---

## 🧪 Покрытие тестами

### Тесты регистрационной формы ([`RegistrationFormTests.java`](src/test/java/tests/RegistrationFormTests.java))

| Тест | Описание | Тип |
|------|------|------|
| `fillAllFieldsAndSubmitTest` | Полное заполнение формы со всеми проверками | ✅ Сценарий успеха |
| `fillOnlyRequiredFieldsTest` | Минимальная валидная отправка | ✅ Сценарий успеха |
| `invalidEmailFormatTest` | Обработка ошибки формата email | ❌ Отрицательный |
| `missingGenderSelectionTest` | Валидация обязательного поля «пол» | ❌ Отрицательный |
| `invalidPhoneNumberTest` | Валидация номера телефона | ❌ Отрицательный |
| `tooLongNameTest` | Обработка слишком длинного имени | ⚠️ Крайний случай |
| `emptyRequiredFieldsTest` | Отправка полностью пустой формы | ❌ Отрицательный |

### Тесты текстового поля ([`TextBoxTests.java`](src/test/java/tests/TextBoxTests.java))

| Тест | Описание | Тип |
|------|------|------|
| `successfulFillFormTest` | Полное заполнение валидными данными | ✅ Сценарий успеха |
| `textBoxMinimalFieldsTest` | Отправка только обязательных полей | ✅ Сценарий успеха |
| `textBoxEmptyFieldsTest` | Отправка пустой формы | ❌ Отрицательный |
| `textBoxTooLongNameTest` | Обработка слишком длинного имени | ⚠️ Крайний случай |
| `failEmailFillFormTest` | Валидация некорректного email | ❌ Отрицательный |

---

## 🎲 Генерация данных

Валидные данные генерируются динамически в [`FakeDataProvider.java`](src/test/java/utils/FakeDataProvider.java) на базе JavaFaker. Провайдер — обычный объект (без `static`), создаётся локально в каждом тестовом классе, поэтому экземпляр `Faker` не разделяется между потоками и тесты безопасны при параллельном запуске:

```java
private final FakeDataProvider fakeData = new FakeDataProvider();
```

| Метод | Что генерирует |
|------|------|
| `randomFirstName()` / `randomLastName()` / `randomFullName()` | Имя, фамилия, полное имя |
| `randomEmail()` | Корректный email-адрес |
| `randomPhone()` | 10-значный номер, начинающийся с цифры 6–9 |
| `randomGender()` | Male / Female / Other |
| `randomSubject()` | Один из доступных предметов |
| `randomAddress()` | Полный адрес |
| `randomBirthDate()` | Дата рождения в формате `dd MMMM yyyy` (возраст 18–65) |
| `randomState()` / `randomCity(state)` | Состояние и город из реального списка формы |

Выбор случайного элемента выполняется штатным методом JavaFaker `faker.options().option(...)`, а соответствие города выбранному состоянию — Switch Expression (Java 14+): неизвестное состояние бросает `IllegalArgumentException` вместо `NullPointerException`:

```java
public String randomCity(String state) {
    return switch (state) {
        case "NCR" -> faker.options().option("Delhi", "Gurgaon", "Noida");
        case "Uttar Pradesh" -> faker.options().option("Agra", "Lucknow", "Merrut");
        case "Haryana" -> faker.options().option("Karnal", "Panipat");
        case "Rajasthan" -> faker.options().option("Jaipur", "Jaiselmer");
        default -> throw new IllegalArgumentException("Unknown state: " + state);
    };
}
```

Список состояний и городов соответствует реальному выпадающему списку demoqa.com:

| State | Cities |
|------|------|
| NCR | Delhi, Gurgaon, Noida |
| Uttar Pradesh | Agra, Lucknow, Merrut |
| Haryana | Karnal, Panipat |
| Rajasthan | Jaipur, Jaiselmer |

Невалидные и граничные значения (слишком длинные строки, некорректные email/телефон) хранятся в [`TestData.java`](src/test/java/testdata/TestData.java), так как они должны быть фиксированными для воспроизводимости негативных сценариев.

---

## 🚀 Начало работы

### Требования

- **Java Development Kit (JDK)** 17 или выше
- **Gradle** 9.3+ (установленный локально; Gradle wrapper в репозиторий не коммитится)
- Локальный Chrome **не требуется**: браузер запускается в облачном Selenoid (`selenoid.qa.guru`)

### Установка

```bash
git clone https://github.com/Electroniktm/jenkins-allure-java-reigstration-form-tests-page-object-rand-data.git
cd jenkins-allure-java-reigstration-form-tests-page-object-rand-data
```

---

## ▶️ Запуск тестов

### Запуск всех тестов

```bash
gradle test
```

### Запуск конкретного класса тестов

```bash
gradle test --tests "tests.RegistrationFormTests"
gradle test --tests "tests.TextBoxTests"
```

### Запуск конкретного метода

```bash
gradle test --tests "tests.RegistrationFormTests.fillAllFieldsAndSubmitTest"
```

### Запуск с подробным выводом

```bash
gradle test --info
```

---

## 🎨 Принятые решения

### Паттерн Page Object

Все локаторы вынесены из тестовых классов в page objects — в тестах остались только вызовы методов и данные:

```java
registrationFormPage.open();
registrationFormPage.fillFirstName(firstName)
        .fillLastName(lastName)
        .fillEmail(email)
        .selectGender(gender);
registrationFormPage.submit();
registrationFormPage.checkModalVisible();
```

Структура:

- [`pages/TextBoxPage.java`](src/test/java/pages/TextBoxPage.java) — элементы и действия формы Text Box
- [`pages/RegistrationFormPage.java`](src/test/java/pages/RegistrationFormPage.java) — элементы и действия формы регистрации
- Сложные элементы вынесены в `pages/components`:
  - [`DatePicker.java`](src/test/java/pages/components/DatePicker.java) — выбор даты рождения
  - [`GenderRadio.java`](src/test/java/pages/components/GenderRadio.java) — группа radio-кнопок пола

Методы page objects возвращают `this`, поэтому заполнение формы читается как цепочка действий.

### Разделение данных: генерация + фиксированные значения

Валидные данные генерируются случайно через [`FakeDataProvider`](src/test/java/utils/FakeDataProvider.java), невалидные и граничные — хранятся в [`TestData`](src/test/java/testdata/TestData.java). В каждом тесте данные берутся в локальные переменные и используются и для заполнения, и для проверок:

```java
String firstName = fakeData.randomFirstName();
String email = fakeData.randomEmail();

registrationFormPage.fillFirstName(firstName)
        .fillEmail(email);
// ...
registrationFormPage.checkResultTableContains(firstName + " " + lastName);
registrationFormPage.checkResultTableContains(email);
```

### Выбор даты рождения

Дата рождения генерируется штатным методом JavaFaker `faker.date().birthday(18, 65)` и форматируется одной строкой через `SimpleDateFormat` в формат `dd MMMM yyyy` (например, `05 June 2023`) — именно его принимает поле даты. Дата выставляется напрямую через `setValue`, что надёжнее кликов по календарю:

```java
public String randomBirthDate() {
    Date birthday = faker.date().birthday(18, 65);
    return new SimpleDateFormat("dd MMMM yyyy").format(birthday);
}
```

### Выбор состояния и города

Состояние выбирается случайно через `faker.options().option(...)`, город — Switch Expression по выбранному состоянию (см. [Генерация данных](#-генерация-данных)). В UI оба значения выбираются кликом по выпадающему списку и последующим кликом по опции с нужным текстом:

```java
stateSelect.click();
stateCityWrapper.$(byText(value)).shouldBe(visible).click();
```

Город всегда выбирается из списка городов выбранного состояния, поэтому значение гарантированно присутствует в выпадающем списке.

### Загрузка ресурсов через classpath

Загрузка файлов использует `uploadFromClasspath` для независимости от окружения:

```java
uploadPicture.uploadFromClasspath("test.png");
```

### Паттерны Selenide

- **Цепочечные селекторы** для вложенных элементов
- **Проверка CSS-границ**: `cssValue("border-color", "rgb(220, 53, 69)")` для состояний ошибок
- **Явные ожидания видимости** перед кликом по опциям

---

## 📐 Лучшие практики

| Практика | Реализация |
|------|------|
| **Page Object Pattern** | Локаторы вынесены в `pages/` и `pages/components/`, в тестах — только методы page objects |
| **Data-Driven Testing** | Валидные данные генерируются через JavaFaker, невалидные — в `TestData.java` |
| **Потокобезопасность** | `FakeDataProvider` — инстанс без `static`, каждый тест получает собственный `Faker` |
| **Современный синтаксис Java** | Switch Expression (Java 14+) для выбора города по состоянию, `faker.options().option(...)` вместо ручных списков |
| **Описательные имена тестов** | `invalidEmailFormatTest`, `missingGenderSelectionTest` |
| **Явные ассерции** | `shouldHave(text(...))` для проверок |
| **Чистый код** | Без inline-комментариев; самодокументирующиеся имена методов |
| **Управление конфигурацией** | Настройки браузера централизованы в `TestBase.beforeALL()` |

---

## 📸 Тестовые ресурсы

| Файл | Назначение |
|------|------|
| [`test.png`](src/test/resources/test.png) | Образец изображения для тестирования загрузки файлов |

---

## 🔧 Конфигурация

Настройки браузера и тестов управляются в [`TestBase.java`](src/test/java/tests/TestBase.java):

```java
Configuration.browserSize = "1920x1280";
Configuration.browser = "chrome";
Configuration.baseUrl = "https://demoqa.com";
// браузер запускается в облачном Selenoid, версия не фиксируется —
// хаб использует пул слотов и отдаёт доступную версию Chrome
Configuration.remote = "https://user1:1234@selenoid.qa.guru/wd/hub";
```

Слушатель `AllureSelenide` (скриншоты + исходник страницы при падении) подключается в каждом тесте:

```java
@BeforeEach
void addListener() {
    SelenideLogger.addListener("AllureSelenide",
            new AllureSelenide().screenshots(true).savePageSource(true));
}
```

---

## 🏗 CI/CD (Jenkins)

Сборка прогоняется в Jenkins: git-склонирование репозитория → `gradle clean test` → публикация отчёта Allure.

| Параметр | Значение |
|------|------|
| **SCM** | `https://github.com/Electroniktm/jenkins-allure-java-reigstration-form-tests-page-object-rand-data.git`, ветка `main` |
| **Агент** | нода с меткой `java` (JDK 21) |
| **Gradle** | установленный в Jenkins `Gradle 8.6`, флаг `--no-daemon`, задачи `clean test` |
| **Allure** | плагин allure-jenkins-plugin, результаты из `build/allure-results`, отчёт собирается всегда (`ALWAYS`) |

Результаты тестов пишутся в `build/allure-results` (плагин `io.qameta.allure` + адаптер JUnit 5 + AspectJ-weave для автоматических шагов), а Jenkins собирает из них интерактивный отчёт, доступный по ссылке `<job>/allure/`.

---

## 🤝 Вклад в проект

1. Следуйте существующим соглашениям об именовании тестов
2. Используйте текстовые селекторы для поиска элементов
3. Добавляйте ассерции для всех пользовательских проверок
4. Делайте тесты независимыми и идемпотентными

---

## 📄 Лицензия

Этот проект создан в образовательных целях. Тесты работают с [demoqa.com](https://demoqa.com) — специальным сайтом для практики автоматизации.

---

<div align="center">

**Создано с ❤️ на Selenide + JUnit 5 + JavaFaker**

[⬆ Наверх](#автоматизированные-тесты-форм-demoqa)

</div>
