package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DatePicker {

    private final SelenideElement dateInput = $("[id=dateOfBirthInput]");

    public void selectDate(String date) {
        dateInput.setValue(date);
    }
}
