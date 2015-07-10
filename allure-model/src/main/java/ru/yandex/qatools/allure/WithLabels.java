package ru.yandex.qatools.allure;

import ru.yandex.qatools.allure.config.AllureConfig;
import ru.yandex.qatools.allure.model.Issue;
import ru.yandex.qatools.allure.model.Label;
import ru.yandex.qatools.allure.model.LabelName;
import ru.yandex.qatools.allure.model.SeverityLevel;
import ru.yandex.qatools.allure.model.TestId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 07.07.15
 */
public interface WithLabels {

    List<Label> getLabels();

    default List<String> getGroups() {
        return getLabelValues(LabelName.GROUP);
    }

    default String getMethodName() {
        return getLabelValue(LabelName.METHOD);
    }

    default String getClassName() {
        return getLabelValue(LabelName.CLASS);
    }

    default String getSuiteName() {
        return getLabelValue(LabelName.SUITE);
    }

    default TestId getTestId() {
        String pattern = AllureConfig.newInstance().getTmsPattern();
        String id = getLabelValue(LabelName.TEST_ID);
        return id == null ? null : new TestId().withName(id).withUrl(String.format(pattern, id));
    }

    default List<Issue> getIssues() {
        String pattern = AllureConfig.newInstance().getIssueTrackerPattern();
        return getLabelValues(LabelName.ISSUE).stream()
                .map(s -> new Issue().withName(s).withUrl(String.format(pattern, s)))
                .collect(Collectors.toList());
    }

    default SeverityLevel getSeverity() {
        String labelValue = getLabelValue(LabelName.SEVERITY);
        return labelValue == null ? SeverityLevel.NORMAL : SeverityLevel.fromValue(labelValue);
    }

    default String getThread() {
        return getLabelValue(LabelName.THREAD);
    }

    default String getHost() {
        return getLabelValue(LabelName.HOST);
    }

    default List<String> getStories() {
        return getLabelValues(LabelName.STORY);
    }

    default List<String> getFeatures() {
        return getLabelValues(LabelName.FEATURE);
    }

    default String getLabelValue(LabelName labelName) {
        return getLabels().stream()
                .filter(label -> labelName.value().equalsIgnoreCase(label.getName()))
                .findFirst()
                .map(Label::getValue).orElse(null);
    }

    default List<String> getLabelValues(LabelName labelName) {
        return getLabels().stream()
                .filter(label -> labelName.value().equalsIgnoreCase(label.getName()))
                .map(Label::getValue).collect(Collectors.toList());
    }
}
