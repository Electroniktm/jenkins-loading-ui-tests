package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.components.DatePicker;
import pages.components.GenderRadio;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationFormPage {

    private final SelenideElement firstName = $("#firstName");
    private final SelenideElement lastName = $("#lastName");
    private final SelenideElement userEmail = $("#userEmail");
    private final SelenideElement userNumber = $("#userNumber");
    private final SelenideElement subjectsInput = $("#subjectsInput");
    private final SelenideElement uploadPicture = $("#uploadPicture");
    private final SelenideElement currentAddress = $("#currentAddress");
    private final SelenideElement stateSelect = $("#state");
    private final SelenideElement citySelect = $("#city");
    private final SelenideElement stateCityWrapper = $("#stateCity-wrapper");
    private final SelenideElement hobbyCheckbox = $("[id='hobbies-checkbox-1']");
    private final SelenideElement submitButton = $("#submit");

    private final SelenideElement modalContent = $(".modal-content");
    private final SelenideElement resultTable = $(".table-responsive");

    private final DatePicker datePicker = new DatePicker();
    private final GenderRadio genderRadio = new GenderRadio();

    @Step("Открываем форму регистрации")
    public RegistrationFormPage open() {
        Selenide.open("/automation-practice-form");
        return this;
    }

    @Step("Вводим имя: {value}")
    public RegistrationFormPage fillFirstName(String value) {
        firstName.setValue(value);
        return this;
    }

    @Step("Вводим фамилию: {value}")
    public RegistrationFormPage fillLastName(String value) {
        lastName.setValue(value);
        return this;
    }

    @Step("Вводим email: {value}")
    public RegistrationFormPage fillEmail(String value) {
        userEmail.setValue(value);
        return this;
    }

    @Step("Выбираем пол: {gender}")
    public RegistrationFormPage selectGender(String gender) {
        genderRadio.select(gender);
        return this;
    }

    @Step("Вводим номер телефона: {value}")
    public RegistrationFormPage fillPhone(String value) {
        userNumber.setValue(value);
        return this;
    }

    @Step("Выбираем дату рождения: {date}")
    public RegistrationFormPage selectBirthDate(String date) {
        datePicker.selectDate(date);
        return this;
    }

    @Step("Выбираем предмет: {value}")
    public RegistrationFormPage fillSubject(String value) {
        subjectsInput.setValue(value).pressEnter();
        return this;
    }

    @Step("Загружаем фотографию")
    public RegistrationFormPage uploadPicture(String fileFromClasspath) {
        uploadPicture.uploadFromClasspath(fileFromClasspath);
        return this;
    }

    @Step("Вводим адрес: {value}")
    public RegistrationFormPage fillCurrentAddress(String value) {
        currentAddress.setValue(value);
        return this;
    }

    @Step("Выбираем штат: {value}")
    public RegistrationFormPage selectState(String value) {
        stateSelect.click();
        stateCityWrapper.$(byText(value)).shouldBe(visible).click();
        return this;
    }

    @Step("Выбираем город: {value}")
    public RegistrationFormPage selectCity(String value) {
        citySelect.click();
        stateCityWrapper.$(byText(value)).shouldBe(visible).click();
        return this;
    }

    @Step("Отмечаем хобби")
    public RegistrationFormPage checkHobby() {
        hobbyCheckbox.click();
        return this;
    }

    @Step("Кликаем Submit")
    public void submit() {
        submitButton.click();
    }

    @Step("Проверяем, что модальное окно появилось")
    public void checkModalVisible() {
        modalContent.shouldBe(visible);
    }

    @Step("Проверяем значение в таблице результатов: {expectedText}")
    public void checkResultTableContains(String expectedText) {
        resultTable.shouldHave(text(expectedText));
    }

    @Step("Проверяем красную рамку у поля email")
    public void checkEmailErrorBorder(String expectedBorderColor) {
        userEmail.shouldHave(cssValue("border-color", expectedBorderColor));
    }

    @Step("Проверяем красную рамку у поля телефона")
    public void checkPhoneErrorBorder(String expectedBorderColor) {
        userNumber.shouldHave(cssValue("border-color", expectedBorderColor));
    }

    @Step("Проверяем красную рамку у поля имени")
    public void checkFirstNameErrorBorder(String expectedBorderColor) {
        firstName.shouldHave(cssValue("border-color", expectedBorderColor));
    }

    @Step("Проверяем красную рамку у поля фамилии")
    public void checkLastNameErrorBorder(String expectedBorderColor) {
        lastName.shouldHave(cssValue("border-color", expectedBorderColor));
    }

    @Step("Проверяем красную рамку у полей пола")
    public void checkGenderErrorBorders(String expectedBorderColor) {
        genderRadio.checkErrorBorders(expectedBorderColor);
    }
}
