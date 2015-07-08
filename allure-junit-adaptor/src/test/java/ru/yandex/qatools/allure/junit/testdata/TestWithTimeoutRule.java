package ru.yandex.qatools.allure.junit.testdata;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.model.TestCaseResult;

import java.util.concurrent.TimeUnit;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 28.01.15
 */
public class TestWithTimeoutRule {

    @Rule
    public Timeout timeout = new Timeout(10000, TimeUnit.MILLISECONDS);

    @Test
    public void someTest() throws Exception {
        Allure.LIFECYCLE.fire((TestCaseResult context) -> {
            context.setName(TestWithTimeoutAnnotation.NAME);
        });
    }
}
