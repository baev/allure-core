package ru.yandex.qatools.allure.plugins

import com.google.common.hash.Hashing
import com.google.inject.Inject
import groovy.transform.CompileStatic
import ru.yandex.qatools.allure.Widgets
import ru.yandex.qatools.allure.io.ReportWriter
import ru.yandex.qatools.allure.utils.PluginUtils

import java.nio.charset.StandardCharsets

/**
 * Plugin manager helps you to find plugins and run
 * only plugins you need.
 * <p/>
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 17.02.15
 */
@CompileStatic
class PluginManager {

    /**
     * File with this name contains list of plugins with resources.
     */
    public static final String PLUGINS_JSON = "plugins.json"
    public static final String WIDGETS_JSON = "widgets.json"

    protected final List<PreparePlugin> preparePlugins

    protected final List<ProcessPlugin> processPlugins

    protected final List<WithResources> pluginsWithResources

    protected final List<WithWidget> pluginsWithWidgets

    protected final List<WithData> pluginsWithData

    /**
     * Create an instance of plugin manager.
     */
    @Inject
    PluginManager(PluginsIndex index) {
        preparePlugins = index.findAll(PreparePlugin)
        processPlugins = index.findAll(ProcessPlugin)

        pluginsWithResources = index.findAll(WithResources)
        pluginsWithWidgets = index.findAll(WithWidget)
        pluginsWithData = index.findAll(WithData)
    }

    /**
     * Get list of names of plugins with resources.
     */
    @Deprecated
    List<String> getPluginsNames() {
        pluginsWithResources.collect { plugin ->
            (plugin as Plugin).name
        }
    }

    /**
     * Get all data for plugins with data.
     */
    @Deprecated
    List<Object> getPluginsData() {
        pluginsWithData*.pluginData.flatten()
    }

    /**
     * Find all prepare plugins an process given object for
     * each of found plugins.
     */
    public <T> void prepare(T object) {
        def plugins = preparePlugins.findAll { it.type == object?.class }
        plugins*.prepare(object)
    }

    /**
     * Find all process plugins an process given object for
     * each of found plugins.
     */
    public <T> void process(T object) {
        def plugins = processPlugins.findAll { it.type == object?.class }
        for (def plugin : plugins) {
            plugin.process(PluginUtils.clone(object))
        }
    }

    /**
     * Write list of plugins with resources to {@link #PLUGINS_JSON}
     *
     * @see ReportWriter
     */
    void writePluginList(ReportWriter writer) {
        writer.write(new PluginDataObject(PLUGINS_JSON, pluginsNames))
    }

    /**
     * Write plugins widgets to {@link #WIDGETS_JSON}.
     *
     * @see ReportWriter
     */
    void writePluginWidgets(ReportWriter writer) {
        def Map<String, Object> widgetData = (Map) pluginsWithWidgets.inject([:]) { memo, widget ->
            memo[(widget as Plugin).name] = widget.widgetData
            return memo
        };
        def hash = Hashing.sha1().hashString(widgetData.keySet().join(""), StandardCharsets.UTF_8).toString()
        writer.write(new PluginDataObject(WIDGETS_JSON, new Widgets(hash: hash, plugins: widgetData)))
    }

    /**
     * Write plugin resources. For each plugin search resources using
     *
     * @see ReportWriter
     */
    void writePluginResources(ReportWriter writer) {
        pluginsWithResources.each { plugin ->
            plugin.resources.each { resource ->
                writer.write((plugin as Plugin).name, resource)
            }
        }
    }

    /**
     * Write plugins data to data directory.
     *
     * @see ReportWriter
     */
    void writePluginData(ReportWriter writer) {
        pluginsData.each { writer.write(it) }
    }
}
