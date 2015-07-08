package ru.yandex.qatools.allure.events;


import ru.yandex.qatools.allure.model.Status;
import ru.yandex.qatools.allure.model.Step;

/**
 * Using to start new step
 *
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 11.11.13
 *         <p/>
 * @see ru.yandex.qatools.allure.Allure
 */
public class StepStartedEvent extends AbstractStepStartedEvent {

    /**
     * Constructs an new event with specified name
     *
     * @param name initial name value
     */
    public StepStartedEvent(String name) {
        setName(name);
    }

    /**
     * Sets name, status, start time and title to specified step
     *
     * @param step which will be changed
     */
    @Override
    public void process(Step step) {
        step.setName(getName());
        step.setStatus(Status.PASSED);
        step.setStart(System.currentTimeMillis());
    }
}
