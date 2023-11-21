package org.example.Factory;
import org.example.Observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Task {
    protected String description;
    protected int priority;
    protected List<Observer> observers = new ArrayList<>();
    protected LocalDateTime creationTime;

    public Task(String description) {
        this.description = description;
        this.priority = 0; // Обычный приоритет
        this.creationTime = LocalDateTime.now();
    }

    public abstract void execute();

    public int getPriority() {
        return priority;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getDescription() {
        return description;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
