package tests;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import pages.TextBoxPage;
import utils.FakeDataProvider;

import static testdata.TestData.*;

@Story("Text Box")
public class TextBoxTests extends TestBase {

    private final TextBoxPage textBoxPage = new TextBoxPage();
    private final FakeDataProvider fakeData = new FakeDataProvider();

    @Test
    void successfulFillFormTest() {
        String name = fakeData.randomFullName();
        String email = fakeData.randomEmail();
        String currentAddress = fakeData.randomAddress();
        String permanentAddress = fakeData.randomAddress();

        textBoxPage.open();
        textBoxPage.fillName(name)
                .fillEmail(email)
                .fillCurrentAddress(currentAddress)
                .fillPermanentAddress(permanentAddress);
        textBoxPage.submit();

        textBoxPage.checkOutputVisible();
        textBoxPage.checkNameInOutput(name);
        textBoxPage.checkEmailInOutput(email);
        textBoxPage.checkCurrentAddressInOutput(currentAddress);
        textBoxPage.checkPermanentAddressInOutput(permanentAddress);
    }

    @Test
    void textBoxMinimalFieldsTest() {
        String name = fakeData.randomFullName();

        textBoxPage.open();
        textBoxPage.fillName(name);
        textBoxPage.submit();

        textBoxPage.checkOutputVisible();
        textBoxPage.checkNameInOutput(name);
    }

    @Test
    void textBoxEmptyFieldsTest() {
        textBoxPage.open();
        textBoxPage.submit();

        textBoxPage.checkOutputHidden();
    }

    @Test
    void textBoxTooLongNameTest() {
        String name = TOO_LONG_NAME;

        textBoxPage.open();
        textBoxPage.fillName(name);
        textBoxPage.submit();

        textBoxPage.checkOutputVisible();
        textBoxPage.checkNameInOutput(name);
    }

    @Test
    void failEmailFillFormTest() {
        String name = fakeData.randomFullName();
        String currentAddress = fakeData.randomAddress();
        String permanentAddress = fakeData.randomAddress();

        textBoxPage.open();
        textBoxPage.fillName(name)
                .fillEmail(INVALID_EMAIL)
                .fillCurrentAddress(currentAddress)
                .fillPermanentAddress(permanentAddress);
        textBoxPage.submit();

        textBoxPage.checkEmailErrorBorder(TEXTBOX_ERROR_BORDER);
    }
}
