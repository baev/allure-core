package ru.yandex.qatools.allure.data;

import ru.yandex.qatools.allure.model.SeverityLevel;
import ru.yandex.qatools.allure.model.Status;
import ru.yandex.qatools.allure.model.Time;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.07.15
 */
public interface WithInfoView {

    String getUid();

    String getName();

    SeverityLevel getSeverity();

    Time getTime();

    Status getStatus();

    default AllureTestCaseInfo toInfo() {
        AllureTestCaseInfo info = new AllureTestCaseInfo();
        info.setUid(getUid());
        info.setName(getName());
        info.setSeverity(getSeverity());
        info.setTime(getTime());
        info.setStatus(getStatus());
        return info;

    }
}
