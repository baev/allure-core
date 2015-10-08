package ru.yandex.qatools.allure.plugins;

import java.lang.reflect.Field;

import static ru.yandex.qatools.allure.plugins.PluginUtils.getFieldValue;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 24.04.15
 */
public interface WithData extends Plugin {

    /**
     * Using this method plugin can store some information to
     * data folder.
     */
    default Object getPluginData() {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(PluginData.class)) {
                return getFieldValue(this, field);
            }
        }
        return null;
    }
}
