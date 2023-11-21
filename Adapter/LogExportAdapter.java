package org.example.Adapter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class LogExportAdapter {
    public void exportLogs(List<String> logs, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String log : logs) {
                writer.write(log + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing logs to file: " + e.getMessage());
        }
    }
}
