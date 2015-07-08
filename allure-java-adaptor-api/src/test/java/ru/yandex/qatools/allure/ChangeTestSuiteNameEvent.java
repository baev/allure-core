package ru.yandex.qatools.allure;

import ru.yandex.qatools.allure.events.TestSuiteEvent;
import ru.yandex.qatools.allure.model.TestSuiteResult;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 15.12.13
 */
public class ChangeTestSuiteNameEvent implements TestSuiteEvent {
    private String uid;
    private String name;

    public ChangeTestSuiteNameEvent(String uid, String title) {
        this.uid = uid;
        this.name = title;
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public void process(TestSuiteResult context) {
        context.setName(name);
    }
}
