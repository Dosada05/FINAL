package org.example.Factory;

import org.example.Observer.Observer;

import java.time.LocalDateTime;

public class TimedTask extends Task {
    private LocalDateTime notifyTime;
    private boolean notified = false;
    private int minutesBeforeNotification; // Время в минутах до уведомления

    public TimedTask(String description, LocalDateTime notifyTime, int minutesBeforeNotification) {
        super(description);
        this.notifyTime = notifyTime;
        this.minutesBeforeNotification = minutesBeforeNotification;
    }

    @Override
    public void execute() {
        System.out.println("Executing timed task: " + description + " at " + notifyTime);
        notifyObservers();
    }

    public LocalDateTime getTime() {
        return notifyTime;
    }

    public LocalDateTime getNotifyTime() {
        return notifyTime.minusMinutes(minutesBeforeNotification);
    }
    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }
    @Override
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }
}
