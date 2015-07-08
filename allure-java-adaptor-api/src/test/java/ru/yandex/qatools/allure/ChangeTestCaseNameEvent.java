package ru.yandex.qatools.allure;

import ru.yandex.qatools.allure.events.TestCaseEvent;
import ru.yandex.qatools.allure.model.TestCaseResult;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 15.12.13
 */
public class ChangeTestCaseNameEvent implements TestCaseEvent {
    private String name;

    public ChangeTestCaseNameEvent(String name) {
        this.name = name;
    }

    @Override
    public void process(TestCaseResult context) {
        context.setName(name);
    }
}
