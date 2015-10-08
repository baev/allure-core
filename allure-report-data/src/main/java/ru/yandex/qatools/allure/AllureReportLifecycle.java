package ru.yandex.qatools.allure;

import com.google.inject.Inject;
import ru.yandex.qatools.allure.converters.TestCaseConverter;
import ru.yandex.qatools.allure.io.Reader;
import ru.yandex.qatools.allure.io.ReportWriter;
import ru.yandex.qatools.allure.plugins.AttachmentsIndex;
import ru.yandex.qatools.allure.plugins.Plugin;
import ru.yandex.qatools.allure.plugins.PluginManager;
import ru.yandex.qatools.allure.model.TestCaseResult;
import ru.yandex.qatools.allure.plugins.PluginsIndex;
import ru.yandex.qatools.allure.plugins.PreparePlugin;
import ru.yandex.qatools.allure.plugins.ProcessPlugin;
import ru.yandex.qatools.allure.plugins.WithResources;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 29.09.15
 */
public class AllureReportLifecycle {

    @Inject
    private Reader<TestCaseResult> testCaseReader;

    @Inject
    private TestCaseConverter converter;

    @Inject
    private PluginManager pluginManager;

    @Inject
    private AttachmentsIndex attachmentsIndex;

    @Inject
    private PluginsIndex pluginsIndex;

    public void generate(ReportWriter writer) {
        if (!testCaseReader.iterator().hasNext()) {
            throw new ReportGenerationException("Could not find any allure results");
        }

        processPluginsWithResources(writer);
        processAttachments(writer);

        for (TestCaseResult result : testCaseReader) {
            pluginManager.prepare(result);

            AllureTestCase testCase = converter.convert(result);
            pluginManager.prepare(testCase);
            pluginManager.process(testCase);
            writer.write(testCase);
        }

        pluginManager.writePluginData(writer);
        pluginManager.writePluginResources(writer);
        pluginManager.writePluginList(writer);
        pluginManager.writePluginWidgets(writer);

        writer.writeReportInfo();
    }

    public <T extends Plugin & WithResources> T getT() {
        return null;
    }

    /**
     * Copy all resources and process index.html.
     */
    public void processPluginsWithResources(ReportWriter writer) {
        List<WithResources> plugins = pluginsIndex.findAll(WithResources.class);
        List<String> names = plugins.stream()
                .map(WithResources::getName).collect(Collectors.toList());

        plugins.stream().forEach(this::processPluginWithResources);

        writer.writeIndexHtml(names);
    }

    public void processAttachments(ReportWriter writer) {
        attachmentsIndex.findAll().forEach(writer::write);
    }

    public void processPluginWithResources(WithResources plugin) {

    }
}
