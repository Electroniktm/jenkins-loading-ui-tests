package utils;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FakeDataProvider {

    private final Faker faker = new Faker();

    public String randomFirstName() {
        return faker.name().firstName();
    }

    public String randomLastName() {
        return faker.name().lastName();
    }

    public String randomFullName() {
        return faker.name().fullName();
    }

    public String randomEmail() {
        return faker.internet().emailAddress();
    }

    public String randomPhone() {
        return faker.options().option("6", "7", "8", "9") + faker.number().digits(9);
    }

    public String randomGender() {
        return faker.options().option("Male", "Female", "Other");
    }

    public String randomSubject() {
        return faker.options().option(
                "English", "Math", "Physics", "Chemistry",
                "History", "Biology", "Computer Science", "Geography");
    }

    public String randomAddress() {
        return faker.address().fullAddress();
    }

    public String randomBirthDate() {
        Date birthday = faker.date().birthday(18, 65);
        return new SimpleDateFormat("dd MMMM yyyy").format(birthday);
    }

    public String randomState() {
        return faker.options().option("NCR", "Uttar Pradesh", "Haryana", "Rajasthan");
    }

    public String randomCity(String state) {
        return switch (state) {
            case "NCR" -> faker.options().option("Delhi", "Gurgaon", "Noida");
            case "Uttar Pradesh" -> faker.options().option("Agra", "Lucknow", "Merrut");
            case "Haryana" -> faker.options().option("Karnal", "Panipat");
            case "Rajasthan" -> faker.options().option("Jaipur", "Jaiselmer");
            default -> throw new IllegalArgumentException("Unknown state: " + state);
        };
    }
}
