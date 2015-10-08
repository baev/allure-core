package ru.yandex.qatools.allure.plugins;

import java.lang.reflect.Field;

import static ru.yandex.qatools.allure.plugins.PluginUtils.getFieldValue;

/**
 * You can add widget to allure report. Widgets are shown at overview
 * tab in the report. There are few supported types of widgets:
 * Key-value list, List with statistics, defects (message with status and count
 * of these messages) and chart.
 *
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 22.04.15
 * @see ProcessPlugin
 */
public interface WithWidget extends Plugin {

    /**
     * Get plugin widget content. You must implement {@link ProcessPlugin} to collect
     * information from test results.
     */
    default Object getWidgetData() {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(WidgetData.class)) {
                return getFieldValue(this, field);
            }
        }
        return null;
    }
}
