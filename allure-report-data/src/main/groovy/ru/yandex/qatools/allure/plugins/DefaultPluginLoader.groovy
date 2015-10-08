package ru.yandex.qatools.allure.plugins

import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Singleton
import ru.yandex.qatools.allure.utils.ServiceLoaderUtils

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 06.10.15
 */
@Singleton
class DefaultPluginLoader implements PluginLoader {

    private final ClassLoader pluginClassLoader

    private final Injector injector

    /**
     * Creates an instance of plugin loader.
     */
    @Inject
    DefaultPluginLoader(@PluginClassLoader ClassLoader pluginClassLoader, Injector injector) {
        this.pluginClassLoader = pluginClassLoader
        this.injector = injector
    }

    /**
     * Load all plugins using via Java SPI then remove all null plugins
     * and finally inject members to each plugin in case not null injector.
     */
    @Override
    List<Plugin> loadPlugins() {
        def result = [] as List<Plugin>
        def loaded = ServiceLoaderUtils.load(pluginClassLoader, Plugin.class) ?: [] as List<Plugin>

        loaded.each {
            if (it.valid) {
                injector?.injectMembers(it)
                result.add(it)
            }
        }
        result
    }
}
