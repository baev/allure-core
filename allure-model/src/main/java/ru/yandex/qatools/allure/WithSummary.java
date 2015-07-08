package ru.yandex.qatools.allure;

import ru.yandex.qatools.allure.model.Attachment;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.Summary;

import java.util.List;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 07.07.15
 */
public interface WithSummary {

    List<Step> getSteps();

    List<Attachment> getAttachments();

    default Summary getSummary() {
        Summary reduce = getSteps().stream()
                .map(WithSummary::getSummary)
                .reduce(new Summary(), WithSummaryAdd::add);

        reduce.setSteps(reduce.getSteps() + getSteps().size());
        reduce.setAttachments(reduce.getAttachments() + getAttachments().size());
        return reduce;
    }
}
