package ru.yandex.qatools.allure.data.io;

import com.google.inject.Inject;
import ru.yandex.qatools.allure.config.AllureModelUtils;
import ru.yandex.qatools.allure.model.Label;
import ru.yandex.qatools.allure.model.LabelName;
import ru.yandex.qatools.allure.model.TestCaseResult;
import ru.yandex.qatools.allure.model.TestSuiteResult;
import ru.yandex.qatools.allure.model.Description;

import java.util.Iterator;

import static ru.yandex.qatools.allure.data.utils.DescriptionUtils.mergeDescriptions;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 09.02.15
 */
public class TestCaseReader implements Reader<TestCaseResult> {

    private Iterator<TestSuiteResult> testSuites;

    private Iterator<TestCaseResult> testCases;

    private TestSuiteResult currentSuite;

    @Inject
    public TestCaseReader(Reader<TestSuiteResult> suiteResultsReader) {
        testSuites = suiteResultsReader.iterator();
    }

    @Override
    public Iterator<TestCaseResult> iterator() {
        return new TestCaseResultIterator();
    }

    private class TestCaseResultIterator implements Iterator<TestCaseResult> {

        private boolean nextSuite() {
            if ((testCases == null || !testCases.hasNext()) && testSuites.hasNext()) {

                currentSuite = testSuites.next();
                testCases = currentSuite.getTestCases().iterator();

                return true;
            }
            return false;
        }

        @Override
        public boolean hasNext() {
            return testCases != null && testCases.hasNext() || nextSuite() && hasNext();
        }

        @Override
        public TestCaseResult next() {
            if (!hasNext()) {
                return null;
            }

            TestCaseResult result = testCases.next();

            result.getLabels().add(AllureModelUtils.createLabel(LabelName.SUITE, currentSuite.getName()));
            result.getLabels().addAll(currentSuite.getLabels());
            Description description = mergeDescriptions(currentSuite, result);
            result.setDescription(description);

            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
