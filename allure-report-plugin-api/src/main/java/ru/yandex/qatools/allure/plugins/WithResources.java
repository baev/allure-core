package ru.yandex.qatools.allure.plugins;

import java.net.URL;
import java.util.List;

/**
 * Common interface for all plugins with some resources. You can pass resources
 * to the report by adding it to class path to ${yourPluginCanonicalClassName} folder.
 * These resources  will be copied to plugins/${pluginName} folder and added to
 * plugins.json. Then plugins/${pluginName}/script.js will be loaded.
 *
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 22.04.15
 */
public interface WithResources extends Plugin {

    /**
     * Return the resources for plugin.
     */
    default List<URL> getResources() {
        return PluginUtils.findPluginResources(
                getClass().getClassLoader(),
                getClass().getCanonicalName()
        );
    }

}
