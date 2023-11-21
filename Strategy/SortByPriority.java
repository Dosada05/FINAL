package org.example.Strategy;

import org.example.Factory.Task;

import java.util.Comparator;
import java.util.List;

public class SortByPriority implements SortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(Task::getPriority).reversed());
    }
}