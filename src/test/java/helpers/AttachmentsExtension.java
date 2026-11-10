package helpers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.closeWebDriver;

/**
 * Прикрепляет вложения (скриншот, исходник страницы, логи консоли, видео) к тесту.
 *
 * Почему расширение, а не @AfterEach:
 * allure-junit-platform останавливает test case в executionFinished, которое JUnit
 * вызывает ДО выполнения @AfterEach — вложения из @AfterEach оказываются «осиротевшими»
 * (файл пишется на диск, но в отчёт не попадает). InvocationInterceptor оборачивает
 * вызов самого метода теста, т.е. работает пока test case ещё открыт.
 */
public class AttachmentsExtension implements InvocationInterceptor {

    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        try {
            invocation.proceed();
        } finally {
            // вложения — до закрытия браузера; браузер мог уже быть недоступен — не портим отчёт
            try {
                Attach.screenshotAs("Last screenshot");
                Attach.pageSource();
                Attach.browserConsoleLogs();
                Attach.addVideo();
            } catch (Exception ignored) {
            }
            closeWebDriver();
        }
    }
}
