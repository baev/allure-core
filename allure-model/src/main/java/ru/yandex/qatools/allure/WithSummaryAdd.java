package ru.yandex.qatools.allure;

import ru.yandex.qatools.allure.model.Summary;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 07.07.15
 */
public interface WithSummaryAdd {

    long getSteps();

    long getAttachments();

    default Summary add(Summary other) {
        return new Summary().withSteps(getSteps() + other.getSteps())
                .withAttachments(getAttachments() + other.getAttachments());
    }
}
