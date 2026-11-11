package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.AttachmentsExtension;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

// расширение прикрепляет к тесту вложения (скриншот/HTML/логи/видео) и закрывает браузер:
// через @AfterEach это не работает — allure-junit-platform к тому моменту уже закрыл test case
@ExtendWith(AttachmentsExtension.class)
public class TestBase {

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @BeforeAll
    static void beforeALL() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1280";
        Configuration.browser = "chrome";
        // версию не фиксируем: хаб selenoid.qa.guru использует пул слотов
        // и отдаёт доступную версию Chrome (на момент настройки — 157)
        Configuration.timeout = 15000;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-dev-shm-usage", "--no-sandbox");
        // без этой capability chromedriver не собирает логи консоли браузера (получали пустой файл)
        chromeOptions.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
        chromeOptions.setCapability("se:cdpEnabled", false);
        chromeOptions.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = chromeOptions;
        // браузер запускается в облачном Selenoid (как в примерах курса);
        // адрес можно переопределить параметром -DremoteURL=...
        Configuration.remote = System.getProperty(
                "remoteURL", "https://user1:1234@selenoid.qa.guru/wd/hub");
    }
}
