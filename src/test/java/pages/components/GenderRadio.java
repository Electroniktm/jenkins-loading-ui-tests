package pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class GenderRadio {

    private final SelenideElement genderWrapper = $("#genterWrapper");
    private final SelenideElement radio1 = $("[id=gender-radio-1]");
    private final SelenideElement radio2 = $("[id=gender-radio-2]");
    private final SelenideElement radio3 = $("[id=gender-radio-3]");

    @Step("Выбираем пол: {gender}")
    public void select(String gender) {
        genderWrapper.$(byText(gender)).click();
    }

    @Step("Проверяем красную рамку у всех радиокнопок пола")
    public void checkErrorBorders(String expectedBorderColor) {
        radio1.shouldHave(cssValue("border-color", expectedBorderColor));
        radio2.shouldHave(cssValue("border-color", expectedBorderColor));
        radio3.shouldHave(cssValue("border-color", expectedBorderColor));
    }
}
