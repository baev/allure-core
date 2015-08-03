package ru.yandex.qatools.allure.data.plugins

import com.google.inject.Inject
import ru.yandex.qatools.allure.data.AllurePluginInfo

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 10.07.15
 */
@Plugin.Name("system")
@Plugin.Priority(-100)
class SystemPlugin extends AbstractPlugin implements Plugin {

    @Inject
    PluginsIndex index

    @Override
    List<PluginData> getPluginData() {
        def pluginInfo = index.plugins.collect {
            new AllurePluginInfo(name: it.class.canonicalName)
        }
        [new PluginData(name + '.json', pluginInfo)]
    }

    @Override
    Class getType() {
        this.class
    }
}
