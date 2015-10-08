package ru.yandex.qatools.allure.plugins

import com.google.inject.Inject
import ru.yandex.qatools.allure.KeyValueWidgetItem

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 18.02.15
 */
@PluginName("environment")
@PluginPriority(100)
class EnvironmentPlugin implements WithWidget {

    @Inject
    Environment environment

    @Override
    Object getWidgetData() {
        environment.parameters.collect {
            new KeyValueWidgetItem(key: it.key, value: it.value)
        }.sort { it.key }
    }
}
