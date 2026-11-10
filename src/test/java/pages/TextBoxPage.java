package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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

    @Step("Открываем страницу TextBox")
    public TextBoxPage open() {
        Selenide.open("/text-box");
        return this;
    }

    @Step("Вводим имя: {name}")
    public TextBoxPage fillName(String name) {
        userName.setValue(name);
        return this;
    }

    @Step("Вводим email: {email}")
    public TextBoxPage fillEmail(String email) {
        userEmail.setValue(email);
        return this;
    }

    @Step("Вводим текущий адрес: {address}")
    public TextBoxPage fillCurrentAddress(String address) {
        currentAddress.setValue(address);
        return this;
    }

    @Step("Вводим постоянный адрес: {address}")
    public TextBoxPage fillPermanentAddress(String address) {
        permanentAddress.setValue(address);
        return this;
    }

    @Step("Кликаем Submit")
    public TextBoxPage submit() {
        submitButton.click();
        return this;
    }

    @Step("Проверяем, что блок output появился")
    public TextBoxPage checkOutputVisible() {
        output.shouldBe(visible);
        return this;
    }

    @Step("Проверяем, что блок output скрыт")
    public TextBoxPage checkOutputHidden() {
        output.shouldBe(hidden);
        return this;
    }

    @Step("Проверяем имя в output: {name}")
    public TextBoxPage checkNameInOutput(String name) {
        outputName.shouldHave(text(name));
        return this;
    }

    @Step("Проверяем email в output: {email}")
    public TextBoxPage checkEmailInOutput(String email) {
        outputEmail.shouldHave(text(email));
        return this;
    }

    @Step("Проверяем текущий адрес в output: {address}")
    public TextBoxPage checkCurrentAddressInOutput(String address) {
        outputCurrentAddress.shouldHave(text(address));
        return this;
    }

    @Step("Проверяем постоянный адрес в output: {address}")
    public TextBoxPage checkPermanentAddressInOutput(String address) {
        outputPermanentAddress.shouldHave(text(address));
        return this;
    }

    @Step("Проверяем красную рамку у поля email")
    public TextBoxPage checkEmailErrorBorder(String expectedBorderColor) {
        userEmail.shouldHave(cssValue("border-color", expectedBorderColor));
        return this;
    }
}
