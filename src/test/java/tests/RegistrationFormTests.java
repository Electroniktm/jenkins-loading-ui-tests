package tests;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import pages.RegistrationFormPage;
import utils.FakeDataProvider;

import static testdata.TestData.*;

@Story("Registration form")
public class RegistrationFormTests extends TestBase {

    private final RegistrationFormPage registrationFormPage = new RegistrationFormPage();
    private final FakeDataProvider fakeData = new FakeDataProvider();

    @Test
    void fillAllFieldsAndSubmitTest() {
        String firstName = fakeData.randomFirstName();
        String lastName = fakeData.randomLastName();
        String email = fakeData.randomEmail();
        String gender = fakeData.randomGender();
        String phone = fakeData.randomPhone();
        String birthDate = fakeData.randomBirthDate();
        String subject = fakeData.randomSubject();
        String address = fakeData.randomAddress();
        String state = fakeData.randomState();
        String city = fakeData.randomCity(state);

        registrationFormPage.open();
        registrationFormPage.fillFirstName(firstName)
                .fillLastName(lastName)
                .fillEmail(email)
                .selectGender(gender)
                .fillPhone(phone)
                .selectBirthDate(birthDate)
                .fillSubject(subject)
                .uploadPicture(UPLOAD_FILE)
                .fillCurrentAddress(address)
                .selectState(state)
                .selectCity(city)
                .checkHobby();
        registrationFormPage.submit();

        registrationFormPage.checkModalVisible();
        registrationFormPage.checkResultTableContains(EXPECTED_STUDENT_NAME_LABEL);
        registrationFormPage.checkResultTableContains(firstName + " " + lastName);
        registrationFormPage.checkResultTableContains(email);
        registrationFormPage.checkResultTableContains(gender);
        registrationFormPage.checkResultTableContains(phone);
        registrationFormPage.checkResultTableContains(subject);
        registrationFormPage.checkResultTableContains(EXPECTED_HOBBY);
        registrationFormPage.checkResultTableContains(address);
        registrationFormPage.checkResultTableContains(state);
        registrationFormPage.checkResultTableContains(city);
    }

    @Test
    void fillOnlyRequiredFieldsTest() {
        String firstName = fakeData.randomFirstName();
        String lastName = fakeData.randomLastName();
        String email = fakeData.randomEmail();
        String gender = fakeData.randomGender();
        String phone = fakeData.randomPhone();
        String birthDate = fakeData.randomBirthDate();

        registrationFormPage.open();
        registrationFormPage.fillFirstName(firstName)
                .fillLastName(lastName)
                .fillEmail(email)
                .selectGender(gender)
                .fillPhone(phone)
                .selectBirthDate(birthDate);
        registrationFormPage.submit();

        registrationFormPage.checkModalVisible();
        registrationFormPage.checkResultTableContains(EXPECTED_STUDENT_NAME_LABEL);
        registrationFormPage.checkResultTableContains(firstName + " " + lastName);
        registrationFormPage.checkResultTableContains(email);
        registrationFormPage.checkResultTableContains(gender);
        registrationFormPage.checkResultTableContains(phone);
    }

    @Test
    void invalidEmailFormatTest() {
        String firstName = fakeData.randomFirstName();
        String lastName = fakeData.randomLastName();
        String gender = fakeData.randomGender();
        String phone = fakeData.randomPhone();
        String birthDate = fakeData.randomBirthDate();

        registrationFormPage.open();
        registrationFormPage.fillFirstName(firstName)
                .fillLastName(lastName)
                .fillEmail(INVALID_EMAIL_FORMAT)
                .selectGender(gender)
                .fillPhone(phone)
                .selectBirthDate(birthDate);
        registrationFormPage.submit();

        registrationFormPage.checkEmailErrorBorder(REGISTRATION_ERROR_BORDER);
    }

    @Test
    void missingGenderSelectionTest() {
        String firstName = fakeData.randomFirstName();
        String lastName = fakeData.randomLastName();
        String phone = fakeData.randomPhone();
        String birthDate = fakeData.randomBirthDate();

        registrationFormPage.open();
        registrationFormPage.fillFirstName(firstName)
                .fillLastName(lastName)
                .fillEmail(EMAIL_RU)
                .fillPhone(phone)
                .selectBirthDate(birthDate);
        registrationFormPage.submit();

        registrationFormPage.checkGenderErrorBorders(REGISTRATION_ERROR_BORDER);
    }

    @Test
    void invalidPhoneNumberTest() {
        String firstName = fakeData.randomFirstName();
        String lastName = fakeData.randomLastName();
        String gender = fakeData.randomGender();
        String birthDate = fakeData.randomBirthDate();

        registrationFormPage.open();
        registrationFormPage.fillFirstName(firstName)
                .fillLastName(lastName)
                .fillEmail(EMAIL_RU)
                .selectGender(gender)
                .fillPhone(INVALID_PHONE)
                .selectBirthDate(birthDate);
        registrationFormPage.submit();

        registrationFormPage.checkPhoneErrorBorder(REGISTRATION_ERROR_BORDER);
    }

    @Test
    void tooLongNameTest() {
        String lastName = fakeData.randomLastName();
        String email = fakeData.randomEmail();
        String gender = fakeData.randomGender();
        String phone = fakeData.randomPhone();
        String birthDate = fakeData.randomBirthDate();

        registrationFormPage.open();
        registrationFormPage.fillFirstName(TOO_LONG_FIRST_NAME)
                .fillLastName(lastName)
                .fillEmail(email)
                .selectGender(gender)
                .fillPhone(phone)
                .selectBirthDate(birthDate);
        registrationFormPage.submit();

        registrationFormPage.checkModalVisible();
        registrationFormPage.checkResultTableContains(EXPECTED_STUDENT_NAME_LABEL);
        registrationFormPage.checkResultTableContains(TOO_LONG_FIRST_NAME);
        registrationFormPage.checkResultTableContains(lastName);
        registrationFormPage.checkResultTableContains(email);
        registrationFormPage.checkResultTableContains(gender);
    }

    @Test
    void emptyRequiredFieldsTest() {
        registrationFormPage.open();
        registrationFormPage.submit();

        registrationFormPage.checkGenderErrorBorders(REGISTRATION_ERROR_BORDER);
        registrationFormPage.checkPhoneErrorBorder(REGISTRATION_ERROR_BORDER);
        registrationFormPage.checkFirstNameErrorBorder(REGISTRATION_ERROR_BORDER);
        registrationFormPage.checkLastNameErrorBorder(REGISTRATION_ERROR_BORDER);
    }
}
