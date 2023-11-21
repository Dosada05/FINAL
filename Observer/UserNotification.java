package org.example.Observer;

import org.example.Factory.Task;
import org.example.Factory.TimedTask;

import java.time.LocalDateTime;

public class UserNotification implements Observer {
    @Override
    public void update(Task task) {
        if (task instanceof TimedTask) {
            TimedTask timedTask = (TimedTask) task;
            LocalDateTime now = LocalDateTime.now();

            // Проверка, что текущее время близко к времени уведомления и уведомление еще не было отправлено
            if (timedTask.getNotifyTime().isBefore(now.plusMinutes(1)) && !timedTask.isNotified()) {
                System.out.println("Reminder: Time for task \"" + timedTask.getDescription() + "\" is approaching!");
                timedTask.setNotified(true); // Устанавливаем флаг, чтобы не отправлять уведомление повторно
            }
        }
    }
}

