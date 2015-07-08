package ru.yandex.qatools.allure.junit.testdata;

import org.junit.Test;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.model.TestCaseResult;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 28.01.15
 */
public class TestWithTimeoutAnnotation {

    public static final String NAME = "TestWithTimeoutRule#name";

    @Test(timeout = 10000)
    public void someTest() throws Exception {
        Allure.LIFECYCLE.fire((TestCaseResult context) -> {
            context.setName(NAME);
        });
    }
}
