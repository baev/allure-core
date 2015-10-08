package ru.yandex.qatools.allure.plugins;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 09.02.15
 */
public class PluginDataObject {

    private String name;

    private Object data;

    public PluginDataObject(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Object getData() {
        return data;
    }
}
