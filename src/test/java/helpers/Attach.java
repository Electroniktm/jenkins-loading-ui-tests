package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;

/**
 * Вложения в отчёт Allure: скриншот, исходник страницы, консольные логи браузера и видео.
 * Вызывается из AttachmentsExtension ДО closeWebDriver() — иначе драйвер уже не будет доступен.
 */
public class Attach {

    @Attachment(value = "{attachName}", type = "image/png", fileExtension = ".png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    public static void browserConsoleLogs() {
        attachAsText(
                "Browser console logs",
                String.join("\n", Selenide.getWebDriverLogs(BROWSER))
        );
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        // видео записывается Selenoid'ом (enableVideo=true) и доступно по ссылке с id сессии;
        // ссылка живёт, пока видео хранится на selenoid.qa.guru
        String videoUrl = "https://selenoid.qa.guru/video/" + sessionId() + ".mp4";
        return "<html><body><video width='100%' height='100%' controls autoplay>"
                + "<source src='" + videoUrl + "' type='video/mp4'></video></body></html>";
    }
}
