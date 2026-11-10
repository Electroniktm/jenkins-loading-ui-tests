package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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

    public RegistrationFormPage open() {
        Selenide.open("/automation-practice-form");
        return this;
    }

    public RegistrationFormPage fillFirstName(String value) {
        firstName.setValue(value);
        return this;
    }

    public RegistrationFormPage fillLastName(String value) {
        lastName.setValue(value);
        return this;
    }

    public RegistrationFormPage fillEmail(String value) {
        userEmail.setValue(value);
        return this;
    }

    public RegistrationFormPage selectGender(String gender) {
        genderRadio.select(gender);
        return this;
    }

    public RegistrationFormPage fillPhone(String value) {
        userNumber.setValue(value);
        return this;
    }

    public RegistrationFormPage selectBirthDate(String date) {
        datePicker.selectDate(date);
        return this;
    }

    public RegistrationFormPage fillSubject(String value) {
        subjectsInput.setValue(value).pressEnter();
        return this;
    }

    public RegistrationFormPage uploadPicture(String fileFromClasspath) {
        uploadPicture.uploadFromClasspath(fileFromClasspath);
        return this;
    }

    public RegistrationFormPage fillCurrentAddress(String value) {
        currentAddress.setValue(value);
        return this;
    }

    public RegistrationFormPage selectState(String value) {
        stateSelect.click();
        stateCityWrapper.$(byText(value)).shouldBe(visible).click();
        return this;
    }

    public RegistrationFormPage selectCity(String value) {
        citySelect.click();
        stateCityWrapper.$(byText(value)).shouldBe(visible).click();
        return this;
    }

    public RegistrationFormPage checkHobby() {
        hobbyCheckbox.click();
        return this;
    }

    public void submit() {
        submitButton.click();
    }

    public void checkModalVisible() {
        modalContent.shouldBe(visible);
    }

    public void checkResultTableContains(String expectedText) {
        resultTable.shouldHave(text(expectedText));
    }

    public void checkEmailErrorBorder(String expectedBorderColor) {
        userEmail.shouldHave(cssValue("border-color", expectedBorderColor));
    }

    public void checkPhoneErrorBorder(String expectedBorderColor) {
        userNumber.shouldHave(cssValue("border-color", expectedBorderColor));
    }

    public void checkFirstNameErrorBorder(String expectedBorderColor) {
        firstName.shouldHave(cssValue("border-color", expectedBorderColor));
    }

    public void checkLastNameErrorBorder(String expectedBorderColor) {
        lastName.shouldHave(cssValue("border-color", expectedBorderColor));
    }

    public void checkGenderErrorBorders(String expectedBorderColor) {
        genderRadio.checkErrorBorders(expectedBorderColor);
    }
}
