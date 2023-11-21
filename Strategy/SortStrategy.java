package org.example.Strategy;

import org.example.Factory.Task;

import java.util.List;

public interface SortStrategy {
    void sort(List<Task> tasks);
}
