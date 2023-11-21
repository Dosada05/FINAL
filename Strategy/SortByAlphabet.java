package org.example.Strategy;

import org.example.Factory.Task;

import java.util.Comparator;
import java.util.List;

public class SortByAlphabet implements SortStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort(Comparator.comparing(task -> task.getDescription().toLowerCase()));
    }
}