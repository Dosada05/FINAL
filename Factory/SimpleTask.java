package org.example.Factory;

public class SimpleTask extends Task {
    public SimpleTask(String description) {
        super(description);
    }

    @Override
    public void execute() {
        System.out.println("Executing simple task: " + description);
    }
}