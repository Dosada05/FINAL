package org.example.Singleton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static Logger instance;
    private List<String> logs;

    private Logger() {
        logs = new ArrayList<>();
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = timestamp + " - " + message;
        logs.add(logMessage);
    }

    public void showLogs() {
        if (logs.isEmpty()) {
            System.out.println("No logs to display.");
        } else {
            System.out.println("\nLogs:");
            for (String log : logs) {
                System.out.println(log);
            }
        }
    }

    public List<String> getLogs() {
        return this.logs;
    }
}
