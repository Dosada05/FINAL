package org.example;


import org.example.Adapter.LogExportAdapter;
import org.example.Adapter.TaskExportAdapter;
import org.example.Decorator.UrgentTaskDecorator;
import org.example.Factory.SimpleTask;
import org.example.Factory.Task;
import org.example.Factory.TimedTask;
import org.example.Observer.UserNotification;
import org.example.Singleton.Logger;
import org.example.Strategy.SortByAlphabet;
import org.example.Strategy.SortByPriority;
import org.example.Strategy.SortByTime;
import org.example.Strategy.SortStrategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logger logger = Logger.getInstance();
        List<Task> tasks = new ArrayList<>();
        TaskExportAdapter exportAdapter = new TaskExportAdapter();
        UserNotification userNotification = new UserNotification();

        while (true) {
            // Проверяем и уведомляем о предстоящих задачах
            tasks.forEach(task -> {
                task.addObserver(userNotification);
                task.notifyObservers();
            });

            System.out.println("\nOptions:");
            System.out.println("1. Create task");
            System.out.println("2. View tasks");
            System.out.println("3. Export tasks");
            System.out.println("4. Delete task");
            System.out.println("5. Show logs");
            System.out.println("6. Export logs");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    // Создание задачи
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter task type (simple/timed): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter task priority (0 for normal, 1 for urgent): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Очистка буфера

                    Task newTask = null;
                    if ("timed".equals(type)) {
                        System.out.println("Choose the day of the week:");
                        System.out.println("1. Monday");
                        System.out.println("2. Tuesday");
                        System.out.println("3. Wednesday");
                        System.out.println("4. Thursday");
                        System.out.println("5. Friday");
                        System.out.println("6. Saturday");
                        System.out.println("7. Sunday");
                        System.out.print("Enter your choice (1-7): ");
                        int dayOfWeekChoice = scanner.nextInt();
                        scanner.nextLine(); // Очистка буфера

                        String dayOfWeekInput = DayOfWeek.of(dayOfWeekChoice).toString();

                        System.out.print("Enter time of the day (HH:mm): ");
                        String timeOfDayInput = scanner.nextLine();

                        LocalDateTime taskTime = getNextDateTime(dayOfWeekInput, timeOfDayInput);

                        System.out.print("Enter minutes before notification: ");
                        int minutesBefore = scanner.nextInt();
                        scanner.nextLine(); // Очистка буфера

                        newTask = new TimedTask(description, taskTime, minutesBefore);
                        tasks.add(newTask);
                    } else if ("simple".equals(type)) {
                        newTask = new SimpleTask(description);
                    } else {
                        System.out.println("Invalid task type entered. Please try again.");
                    }

                    if (newTask != null) {
                        if (priority == 1) {
                            newTask = new UrgentTaskDecorator(newTask);
                        }
                        if (newTask instanceof TimedTask) {
                            TimedTask timedTask = (TimedTask) newTask;
                            logger.log("Created a new timed task: " + description + ", Event at: " + timedTask.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        } else {
                        }
                        logger.log("Created a new task: " + description + " with priority: " + (priority == 1 ? "Urgent" : "Normal"));
                    }
                    break;

                case 2:
                    // Просмотр и сортировка задач
                    if (!tasks.isEmpty()) {
                        System.out.print("Sort by (priority/time/alphabet): ");
                        String sortType = scanner.nextLine();
                        sortTasks(tasks, sortType);
                        tasks.forEach(task -> {
                            String taskInfo = task.getDescription() + " (Created at: " + task.getCreationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";
                            if (task instanceof TimedTask) {
                                TimedTask timedTask = (TimedTask) task;
                                taskInfo += " [Event at: " + timedTask.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "]";
                            }
                            System.out.println(taskInfo);
                        });
                        logger.log("Displayed tasks");
                    } else {
                        System.out.println("No tasks to display.");
                    }
                    break;
                case 3:
                    // Экспорт задач
                    exportAdapter.exportTasks(tasks, "tasks.txt");
                    logger.log("Exported tasks to tasks.txt");
                    break;
                case 4:
                    // Удаление задачи
                    if (!tasks.isEmpty()) {
                        System.out.print("Enter task index to delete (1 to " + tasks.size() + "): ");
                        int index = scanner.nextInt() - 1;
                        scanner.nextLine(); // Очистка буфера
                        if (index >= 0 && index < tasks.size()) {
                            tasks.remove(index);
                            logger.log("Deleted task at index " + (index + 1));
                        } else {
                            System.out.println("Invalid task index.");
                            logger.log("Attempted to delete task with invalid index");
                        }
                    } else {
                        System.out.println("No tasks to delete.");
                    }
                    break;
                case 5:
                    // Показать логи
                    logger.showLogs();
                    break;
                case 6:
                    // Экспорт логов
                    System.out.print("Enter filename for logs: ");
                    String logFilename = scanner.nextLine();
                    LogExportAdapter logExportAdapter = new LogExportAdapter();
                    logExportAdapter.exportLogs(logger.getLogs(), logFilename);
                    logger.log("Exported logs to " + logFilename);
                    break;

                case 7:
                    // Выход
                    logger.log("Exited application");
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
                    logger.log("Made an invalid choice in menu");
                    break;
            }
        }
    }
    private static void sortTasks(List<Task> tasks, String sortType) {
        SortStrategy strategy;
        switch (sortType) {
            case "priority":
                strategy = new SortByPriority();
                break;
            case "time":
                strategy = new SortByTime();
                break;
            case "alphabet":
                strategy = new SortByAlphabet();
                break;
            default:
                strategy = new SortByPriority();
                System.out.println("Invalid sort type. Using default (priority).");
        }
        strategy.sort(tasks);
    }
    private static LocalDateTime getNextDateTime(String dayOfWeekInput, String timeOfDayInput) {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekInput.toUpperCase());
        LocalTime timeOfDay = LocalTime.parse(timeOfDayInput, DateTimeFormatter.ofPattern("HH:mm"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime taskDateTime = now.with(TemporalAdjusters.nextOrSame(dayOfWeek)).with(timeOfDay);

        if (taskDateTime.isBefore(now)) {
            // Если выбранное время уже прошло на этой неделе, перейдем к следующей неделе
            taskDateTime = taskDateTime.plusWeeks(1);
        }

        return taskDateTime;
    }
}