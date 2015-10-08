package ru.yandex.qatools.allure.plugins;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 06.10.15
 */
public final class PluginUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginUtils.class);

    PluginUtils() {
    }

    /**
     * Try to get field value. Field with {@link PluginData} annotation should
     * be accessible.
     */
    public static Object getFieldValue(Object instance, Field field) {
        try {
            field.setAccessible(true);
            return field.get(instance);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOGGER.error("Can't access to field value", e);
            return null;
        }
    }

    /**
     * Find all resources for plugin.
     */
    public static List<URL> findPluginResources(ClassLoader loader, String canonicalName) {
        String path = canonicalName.replace(".", "/");
        Pattern pattern = Pattern.compile("^" + path + " /.+\\$");
        List<URL> result = new ArrayList<>();
        try {
            for (ClassPath.ResourceInfo resource : ClassPath.from(loader).getResources()) {
                Matcher matcher = pattern.matcher(resource.getResourceName());
                if (matcher.find()) {
                    result.add(resource.url());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Could not find resources for plugin " + canonicalName, e);
        }
        return result;
    }

    /**
     * Check given class modifiers. Plugin with resources plugin should not be private or abstract
     * or interface.
     */
    public static boolean checkModifiers(Class<? extends Plugin> pluginClass) {
        int modifiers = pluginClass.getModifiers();
        return !Modifier.isAbstract(modifiers) &&
                !Modifier.isInterface(modifiers) &&
                !Modifier.isPrivate(modifiers);
    }

    /**
     * Check fields with given annotation. There is should be only one field with such annotation.
     */
    public static boolean checkFieldsWithAnnotationCount(Class<? extends Plugin> pluginClass,
                                                         Class<? extends Annotation> annotationClass) {
        long count = 0;
        for (Field field : pluginClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                count++;
            }
        }
        return count <= 1;
    }

}
