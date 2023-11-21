package org.example.Decorator;

import org.example.Factory.Task;

public class UrgentTaskDecorator extends TaskDecorator {
    public UrgentTaskDecorator(Task decoratedTask) {
        super(decoratedTask);
        this.priority = 1; // Высокий приоритет для срочных задач
    }

    @Override
    public void execute() {
        System.out.println("Urgent Task: ");
        decoratedTask.execute();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
