package ru.yandex.qatools.allure.plugins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation you can specify the plugin name. This annotation
 * is required for all plugins. Plugin name should contains only latin characters
 * or numbers (but can't start with number).
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginName {

    String value();
}
