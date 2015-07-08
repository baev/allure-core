package ru.yandex.qatools.allure;

import ru.yandex.qatools.allure.model.Time;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.07.15
 */
public interface WithTimeUpdate {

    long getStart();

    long getStop();

    void setStart(Long start);

    void setStop(Long stop);

    void setDuration(Long duration);

    default void update(Time other) {
        setStart(Math.min(getStart(), other.getStart()));
        setStop(Math.max(getStop(), other.getStop()));
        setDuration(getStop() - getStart());
    }
}
