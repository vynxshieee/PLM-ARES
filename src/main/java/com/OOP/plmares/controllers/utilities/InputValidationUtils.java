package com.OOP.plmares.controllers.utilities;

import com.OOP.plmares.database.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputValidationUtils {

    // ----- 1.) validates name. may have upper, lowercase values, spaces, and special characters or accents. no numerals. has character limit
    public static boolean isValidName(String name) {
        if (name.isEmpty() || name.length() > 100) {
            return false; // empty or too long is not a valid name
        }

        // Remove diacritics (accents) from the name
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Regular expression to allow letters, spaces, and some common special characters
        String regex = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalized);

        return matcher.matches();
    }

    // ----- 2.) validates cp_num. starts with "09" and is followed by exactly 9 more digits
    public static boolean isValidPhilippineNumber(String number) {
        if (number.isEmpty()) {
            return false; // empty is not a valid number
        }
        // Regular expression to validate Filipino mobile number
        String regex = "^09\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    // ----- 3.) validates a type-written date picker date
    public static boolean isValidDate(String dateString) {
        if (dateString.isEmpty()) {
            System.out.println("Empty date");
            return false; // empty date
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
            return false;
        }
    }

    // ----- 4.) validates address, not null and has char limit
    public static boolean isValidAddress(String address) {
        return !address.isEmpty() && address.length() <= 255;
    }

    // ----- 5.) validates a description. can have upper, lower case and dashes, apostrophes, parentheses, dots, numerals, and dashes
    public static boolean isValidDescription(String description) {
        if (description.isEmpty()) {
            return false; // empty is not a valid description
        }

        // Remove diacritics (accents) from the description
        String normalized = Normalizer.normalize(description, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Regular expression to allow letters, spaces, dashes, apostrophes, parentheses, dots, and numerals
            String regex = "^[\\p{L} .'-()0-9-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalized);

        return matcher.matches();
    }

    // ----- 6.) validates a primary key and checks validity. can have upper, lower case and dash. must not have same code
    public static boolean isValidCode(String strCode, String strTableName, String strColumn) {
        if (!strCode.matches("^[A-Za-z\\-]*$") || strCode.isEmpty() || strCode.length() > 10) {
            return false; // Invalid code format
        }

        return isCodeUnique(strCode, strTableName, strColumn);
    }

    // ----- 7.) validates a subject primary key and checks validity. can have upper, lower case, dash, dots, spaces, numerals. must not have same code
    public static boolean isValidSubjectCode(String strCode, String strTableName, String strColumn) {
        if (!strCode.matches("^[A-Za-z0-9\\- .()]*$") || strCode.isEmpty() || strCode.length() > 10) {
            return false; // Invalid code format
        }
        return isCodeUnique(strCode, strTableName, strColumn);
    }

    // ----- 8.) validates primary keys uniqueness
    public static boolean isCodeUnique (String strCode, String strTableName, String strColumn){
        // Check if the code already exists in the specified column of the table
        String query = "SELECT COUNT(*) FROM " + strTableName + " WHERE " + strColumn + " = ?";
        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, strCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count == 0; // Return true

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----- 9.) validates a section or room string. can have upper, lower case, dash, dots, spaces, numerals.
    public static boolean isValidSecRoomCode(String strCode) {
        if (!strCode.matches("^[A-Za-z0-9\\- .()]*$") || strCode.isEmpty() || strCode.length() > 10) {
            return false; // Invalid code format
        }
        return true;
    }

    // ----- 10.) validates military time range string
    public static boolean isValidTimeRange(String timeRange) {
        if (timeRange.isEmpty()) {
            System.out.println("Empty time range");
            return false;
        }

        // Split the time range into start and end times
        String[] times = timeRange.split("-");
        if (times.length != 2) {
            System.out.println("Invalid time range format");
            return false;
        }

        // Check if both start and end times are valid
        return isValidTime(times[0]) && isValidTime(times[1]);
    }

    // ----- 11.) validates time format
    private static boolean isValidTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime.parse(time, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format: " + e.getMessage());
            return false;
        }
    }

    // ----- 12.) checks if inputted password is the same in stored one
    public static boolean isCorrectCurrentPassword(String strCode, String strPassword) {
        // check if any password and user matches
        String query = "SELECT COUNT(*) FROM login_credential WHERE user_id = ? AND password = ?";
        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            System.out.println("CURRENT PASSWORD ON MATCH CHECK: " + strPassword + "\n USER: " + strCode);
            preparedStatement.setString(1, strCode);
            preparedStatement.setString(2, strPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count == 0) {
                System.out.println("NO MATCH FOUND");
            }
            return count == 1; // Return true if a match is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----- 13.)  password validation class
    public static class PasswordValidator {
        public static boolean isValidPassword(String password) {
            // Minimum 8 characters
            if (password.length() < 8) {
                return false;
            }

            // Check for at least one uppercase letter
            if (!containsUppercase(password)) {
                return false;
            }

            // Check for at least one lowercase letter
            if (!containsLowercase(password)) {
                return false;
            }

            // Check for at least one symbol or number
            if (!containsSymbolOrNumber(password)) {
                return false;
            }

            return true;
        }

        private static boolean containsUppercase(String password) {
            return password.chars().anyMatch(Character::isUpperCase);
        }

        private static boolean containsLowercase(String password) {
            return password.chars().anyMatch(Character::isLowerCase);
        }

        private static boolean containsSymbolOrNumber(String password) {
            Pattern pattern = Pattern.compile("[^a-zA-Z]");
            Matcher matcher = pattern.matcher(password);

            return matcher.find();
        }
    }
}
