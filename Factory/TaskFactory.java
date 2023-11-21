package org.example.Factory;
import java.time.LocalDateTime;
public class TaskFactory {
    public static Task createTask(String type, String description, LocalDateTime notifyTime, int minutesBeforeNotification) {
        switch (type) {
            case "simple":
                return new SimpleTask(description);
            case "timed":
                return new TimedTask(description, notifyTime, minutesBeforeNotification);
            default:
                throw new IllegalArgumentException("Unknown task type");
        }
    }
}
