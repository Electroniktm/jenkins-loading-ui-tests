package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TextBoxPage {

    private final SelenideElement userName = $("[id=userName]");
    private final SelenideElement userEmail = $("[id=userEmail]");
    private final SelenideElement currentAddress = $("[id=currentAddress]");
    private final SelenideElement permanentAddress = $("[id=permanentAddress]");
    private final SelenideElement submitButton = $("[id=submit]");

    private final SelenideElement output = $("[id=output]");
    private final SelenideElement outputName = $("[id=output] [id=name]");
    private final SelenideElement outputEmail = $("[id=output] [id=email]");
    private final SelenideElement outputCurrentAddress = $("[id=output] [id=currentAddress]");
    private final SelenideElement outputPermanentAddress = $("[id=output] [id=permanentAddress]");

    public TextBoxPage open() {
        Selenide.open("/text-box");
        return this;
    }

    public TextBoxPage fillName(String name) {
        userName.setValue(name);
        return this;
    }

    public TextBoxPage fillEmail(String email) {
        userEmail.setValue(email);
        return this;
    }

    public TextBoxPage fillCurrentAddress(String address) {
        currentAddress.setValue(address);
        return this;
    }

    public TextBoxPage fillPermanentAddress(String address) {
        permanentAddress.setValue(address);
        return this;
    }

    public TextBoxPage submit() {
        submitButton.click();
        return this;
    }

    public TextBoxPage checkOutputVisible() {
        output.shouldBe(visible);
        return this;
    }

    public TextBoxPage checkOutputHidden() {
        output.shouldBe(hidden);
        return this;
    }

    public TextBoxPage checkNameInOutput(String name) {
        outputName.shouldHave(text(name));
        return this;
    }

    public TextBoxPage checkEmailInOutput(String email) {
        outputEmail.shouldHave(text(email));
        return this;
    }

    public TextBoxPage checkCurrentAddressInOutput(String address) {
        outputCurrentAddress.shouldHave(text(address));
        return this;
    }

    public TextBoxPage checkPermanentAddressInOutput(String address) {
        outputPermanentAddress.shouldHave(text(address));
        return this;
    }

    public TextBoxPage checkEmailErrorBorder(String expectedBorderColor) {
        userEmail.shouldHave(cssValue("border-color", expectedBorderColor));
        return this;
    }
}
