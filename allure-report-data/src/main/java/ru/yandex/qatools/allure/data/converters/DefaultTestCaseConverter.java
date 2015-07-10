package ru.yandex.qatools.allure.data.converters;

import com.google.inject.Inject;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import ru.yandex.qatools.allure.data.AllureAttachment;
import ru.yandex.qatools.allure.data.AllureStep;
import ru.yandex.qatools.allure.data.AllureTestCase;
import ru.yandex.qatools.allure.data.io.ResultDirectories;
import ru.yandex.qatools.allure.data.utils.TextUtils;
import ru.yandex.qatools.allure.model.Attachment;
import ru.yandex.qatools.allure.model.SeverityLevel;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;

import java.io.File;
import java.util.Optional;

import static ru.yandex.qatools.allure.data.utils.TextUtils.generateUid;
import static ru.yandex.qatools.allure.model.DescriptionType.MARKDOWN;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 07.07.15
 */
public class DefaultTestCaseConverter implements TestCaseConverter {

    public static final String UNKNOWN_STEP_NAME = "UnknownStepName";
    public static final String UNKNOWN_TEST_SUITE = "UnknownTestSuite";
    public static final String UNKNOWN_TEST_CASE = "UnknownTestCase";

    @Inject
    public DefaultTestCaseConverter(@ResultDirectories File... dirs) {
    }

    @Override
    public AllureTestCase convert(TestCaseResult result) {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(TestCaseResult.class, AllureTestCase.class).setPostConverter(new TestCaseResultProcessor());
        mapper.createTypeMap(Step.class, AllureStep.class).setPostConverter(new StepProcessor());
        mapper.createTypeMap(Attachment.class, AllureAttachment.class).setPostConverter(new AttachmentProcessor());

        return mapper.map(result, AllureTestCase.class);
    }


    class TestCaseResultProcessor implements Converter<TestCaseResult, AllureTestCase> {

        @Override
        public AllureTestCase convert(MappingContext<TestCaseResult, AllureTestCase> context) {
            AllureTestCase result = context.getDestination();
            TestCaseResult source = context.getSource();

            result.setUid(generateUid());
            result.setName(Optional.ofNullable(source.getName()).orElse(UNKNOWN_TEST_CASE));

            if (result.getDescription() != null && MARKDOWN.equals(result.getDescription().getType())) {
                result.getDescription().setValue(TextUtils.processMarkdown(result.getDescription().getValue()));
            }

            result.setTime(source.getTime());
            result.setIssues(source.getIssues());
            result.setTestId(source.getTestId());
            result.setSeverity(Optional.ofNullable(source.getSeverity()).orElse(SeverityLevel.NORMAL));
            result.setSummary(source.getSummary());
            result.setSuite(Optional.ofNullable(source.getSuiteName()).orElse(UNKNOWN_TEST_SUITE));

            return result;
        }
    }

    class StepProcessor implements Converter<Step, AllureStep> {

        @Override
        public AllureStep convert(MappingContext<Step, AllureStep> context) {
            AllureStep result = context.getDestination();
            Step source = context.getSource();

            result.setName(Optional.ofNullable(source.getName()).orElse(UNKNOWN_STEP_NAME));

            result.setTime(source.getTime());
            result.setSummary(source.getSummary());

            return result;
        }
    }

    class AttachmentProcessor implements Converter<Attachment, AllureAttachment> {

        @Override
        public AllureAttachment convert(MappingContext<Attachment, AllureAttachment> context) {
            AllureAttachment result = context.getDestination();

            result.setUid(generateUid());

            return result;
        }
    }
}
