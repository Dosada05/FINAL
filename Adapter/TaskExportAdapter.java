package org.example.Adapter;

import org.example.Factory.Task;
import org.example.Factory.TimedTask;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskExportAdapter {
    public void exportTasks(List<Task> tasks, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.write(formatTaskForExport(task) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private String formatTaskForExport(Task task) {
        StringBuilder taskInfo = new StringBuilder();
        taskInfo.append("Task: ").append(task.getDescription());
        taskInfo.append(", Priority: ").append(task.getPriority());

        // Проверка, является ли задача экземпляром TimedTask
        if (task instanceof TimedTask) {
            TimedTask timedTask = (TimedTask) task;
            taskInfo.append(", Event Time: ").append(timedTask.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            taskInfo.append(", Notify Time: ").append(timedTask.getNotifyTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        return taskInfo.toString();
    }

}
