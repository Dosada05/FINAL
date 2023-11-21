package org.example.Decorator;

import org.example.Factory.Task;

public class TaskDecorator extends Task {
    protected Task decoratedTask;

    public TaskDecorator(Task decoratedTask) {
        super(decoratedTask.getDescription());
        this.decoratedTask = decoratedTask;
    }

    @Override
    public void execute() {
        decoratedTask.execute();
    }
}
