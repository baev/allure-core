package ru.yandex.qatools.allure.testdata

import ru.yandex.qatools.allure.AllureTestCase
import ru.yandex.qatools.allure.plugins.DefaultTabPlugin
import ru.yandex.qatools.allure.plugins.PluginName

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 22.04.15
 */
@PluginName("somePluginWithResources")
class SomePluginWithResources extends DefaultTabPlugin {

    @Override
    void process(AllureTestCase data) {
        //do nothing
    }
}
