package ru.yandex.qatools.allure;

import ru.yandex.qatools.allure.model.Time;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 07.07.15
 */
public interface WithTime {

    long getStart();

    long getStop();

    default Time getTime() {
        return new Time().withStart(getStart())
                .withStop(getStop())
                .withDuration(getStop() - getStart());
    }
}
