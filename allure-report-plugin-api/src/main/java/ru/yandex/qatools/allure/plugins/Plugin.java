package ru.yandex.qatools.allure.plugins;

import static ru.yandex.qatools.allure.plugins.PluginUtils.checkFieldsWithAnnotationCount;
import static ru.yandex.qatools.allure.plugins.PluginUtils.checkModifiers;

/**
 * Main interface for all Allure report plugins.
 *
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 09.02.15
 */
public interface Plugin {

    /**
     * Name for plugin. Name should be unique and contains only latin characters.
     */
    default String getName() {
        return getClass().isAnnotationPresent(PluginName.class) ?
                getClass().getAnnotation(PluginName.class).value() : null;
    }

    /**
     * Returns the priority of the plugin.
     */
    default int getPriority() {
        return getClass().isAnnotationPresent(PluginPriority.class) ?
                getClass().getAnnotation(PluginPriority.class).value() : 0;
    }

    /**
     * Returns true if plugin is valid, false otherwise.
     */
    default boolean isValid() {
        return getClass().isAnnotationPresent(PluginName.class)
                && checkModifiers(getClass())
                && checkFieldsWithAnnotationCount(getClass(), PluginData.class)
                && checkFieldsWithAnnotationCount(getClass(), WidgetData.class);
    }
}
