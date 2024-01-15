package com.OOP.plmares.controllers.tableUtils;

import com.OOP.plmares.database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBCommonMethods {
    public static void populateComboBox(ComboBox<String> comboBox, String tableName, String columnName) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("--");
        String query = "SELECT " + columnName + " FROM " + tableName;

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String value = resultSet.getString(columnName);
                items.add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }

        comboBox.setItems(items);
    }

    public static void executeQuery(String query) {
        try (Connection connection = new ConnectDB().Connect();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(query);
            System.out.println("Query executed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
    }

    public static void updateDBValues(String tableName, String columnName, String conditionColumn, String newValue, String oldValue) throws SQLException {
        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     String.format("UPDATE %s SET %s = ? WHERE %s = ?", tableName, columnName, conditionColumn))) {

            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, oldValue);
            preparedStatement.executeUpdate();
        }
    }

    public static void updateValuesWithFilter(Connection connection, String tableName, String columnName, String conditionColumn, String oldValue, String newValue) throws SQLException {
        String updateQuery = String.format("UPDATE %s SET %s = ? WHERE %s = ?", tableName, columnName, conditionColumn);
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, oldValue);
            preparedStatement.executeUpdate();
        }
    }

    public static void deleteRecord(Connection connection, String tableName, String conditionColumn, String conditionValue) throws SQLException {
        String deleteQuery = String.format("DELETE FROM %s WHERE %s = ?", tableName, conditionColumn);

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, conditionValue);
            preparedStatement.executeUpdate();
        }
    }

    public static String fetchNameData(String strTable, String strConditionColumn, String strDataColumn, String strUserID) {
        String result = null;

        try (Connection connection = new ConnectDB().Connect()) {
            String selectQuery = "SELECT " + strDataColumn + " FROM " + strTable + " WHERE " + strConditionColumn + " = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, strUserID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result = resultSet.getString(strDataColumn);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void removeProfileImage(String strTable, String strProfileTable, String strConditionColumn, String strUserID) {
        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE " + strTable + " SET " + strProfileTable + " = null WHERE " + strConditionColumn + " = ?")) {
            preparedStatement.setString(1, strUserID);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Profile picture removed successfully.");
            } else {
                System.out.println("No rows affected. Profile picture removal might have failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error removing profile picture: " + e.getMessage());
        }
    }

    public static boolean storeProfilePictureInDB(File file, String strTable, String strProfileTable,
                                               String strConditionColumn, String strUserID) {
        try (Connection connection = new ConnectDB().Connect();
             FileInputStream fileInputStream = new FileInputStream(file)) {

            String query = "UPDATE " + strTable + " SET  " + strProfileTable +" = ? WHERE " + strConditionColumn + " = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setBinaryStream(1, fileInputStream);
                preparedStatement.setString(2, strUserID);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Image stored in the database successfully.");
                    return true;
                } else {
                    System.out.println("Failed to store the image in the database.");
                    return false;
                }
            }
        } catch (SQLException | IOException e) {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Large Image Size");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Error: Image file size too big!");
            warningAlert.showAndWait();
            System.out.println(e);
            return false;
        }
    }

    public static Image getProfilePictureFromDB(String strTable, String strProfileTable,
                                                String strConditionColumn, String strUserID) {
        try (Connection connection = new ConnectDB().Connect()) {
            String query = "SELECT " + strProfileTable + " FROM " + strTable +
                    " WHERE " + strConditionColumn + " = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, strUserID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve the image as a byte array
                        byte[] imageData = resultSet.getBytes("profile_image");

                        // Convert the byte array to an Image
                        if (imageData != null) {
                            return new Image(new ByteArrayInputStream(imageData));
                        } else {
                            System.out.println("Image data is null for the specified user.");
                        }
                    } else {
                        System.out.println("No image found for the specified user.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
}
