package com.OOP.plmares.controllers;

import java.io.*;
import java.time.LocalDate;
import java.util.Properties;

public class GlobalSchedule {
    private static final String FILE_PATH = "src/main/resources/globalSchedule.properties";
    private static Properties properties;
    static {
        properties = new Properties();
        loadProperties();
    }

    public static LocalDate getDtStartDate() {
        String startDateStr = properties.getProperty("dtStartDate", "");
        return parseLocalDate(startDateStr);
    }

    public static void setDtStartDate(LocalDate value) {
        properties.setProperty("dtStartDate", formatLocalDate(value));
        saveProperties();
    }

    public static LocalDate getDtEndDate() {
        String endDateStr = properties.getProperty("dtEndDate", "");
        return parseLocalDate(endDateStr);
    }

    public static void setDtEndDate(LocalDate value) {
        properties.setProperty("dtEndDate", formatLocalDate(value));
        saveProperties();
    }

    private static void loadProperties() {
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveProperties() {
        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            properties.store(output, "Global Schedule");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LocalDate parseLocalDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    private static String formatLocalDate(LocalDate date) {
        if (date != null) {
            return date.toString();
        } else {
            return "";
        }
    }
}
