package org.example.Strategy;

import org.example.Factory.Task;
import org.example.Factory.TimedTask;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class SortByTime implements SortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort(Comparator.comparing(task -> {
            if (task instanceof TimedTask) {
                return ((TimedTask) task).getTime();
            }
            return LocalDateTime.MAX;
        }));
    }
}