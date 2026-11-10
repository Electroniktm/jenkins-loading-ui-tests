package pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class DatePicker {

    private final SelenideElement dateInput = $("[id=dateOfBirthInput]");

    @Step("Выбираем дату рождения: {date}")
    public void selectDate(String date) {
        dateInput.setValue(date);
    }
}
