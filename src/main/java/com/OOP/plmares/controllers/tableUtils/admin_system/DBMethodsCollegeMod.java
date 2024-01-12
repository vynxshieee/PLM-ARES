package com.OOP.plmares.controllers.tableUtils.admin_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsCollegeMod {
    public static ObservableList<TableModel.CollegeModule> getColleges(String strSearchTerm) {
        ObservableList<TableModel.CollegeModule> CollegeModuleList = FXCollections.observableArrayList();

        String query ="SELECT * FROM college WHERE college_code LIKE ? OR description LIKE ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + strSearchTerm + "%");
            preparedStatement.setString(2, "%" + strSearchTerm + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strCollegeCode = resultSet.getString("college_code");
                String strDescription = resultSet.getString("description");
                String strDateOpened = resultSet.getString("date_opened");
                String strDateClosed = resultSet.getString("date_closed");
                String strStatus = resultSet.getString("status");

                // Create an instance of CollegeModule and add to the list
                TableModel.CollegeModule college = new TableModel.CollegeModule(
                        strCollegeCode, strDescription, strDateOpened, strDateClosed, strStatus
                );
                CollegeModuleList.add(college);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return CollegeModuleList;
    }

    public static boolean deleteCollegeModule(String strCollegeCode) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete the information for college " + strCollegeCode + "?\n\n"
                + "Note: Deleting this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        try (Connection connection = new ConnectDB().Connect()) {
            DBCommonMethods.deleteRecord(connection, "college", "college_code", strCollegeCode);
            System.out.println("College information deleted successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

    public static boolean editCollegeModule(String strCollegeCode, String strDescription,
                                                String strDateOpened, String strDateClosed, String strStatus) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to edit the information for college " + strCollegeCode + "?\n\n"
                + "Note: Editing this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        String updateQuery = "UPDATE college SET " +
                "description = ?, date_opened = ?, date_closed = ?, status = ? " +
                "WHERE college_code = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, strDescription);
            preparedStatement.setString(2, strDateOpened);
            preparedStatement.setString(3, strDateClosed);
            preparedStatement.setString(4, strStatus);
            preparedStatement.setString(5, strCollegeCode);


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("College information updated successfully.");
                return true;
            } else {
                System.out.println("No matching College record found for update.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

    public static boolean addCollegeModule(String strCollegeCode, String strDescription,
                                               String strDateOpened, String strDateClosed, String strStatus) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to add the information for college " + strCollegeCode + "?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        String addQuery = "INSERT INTO college (college_code, description, date_opened, date_closed, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {

            preparedStatement.setString(1, strCollegeCode);
            preparedStatement.setString(2, strDescription);
            preparedStatement.setString(3, strDateOpened);
            preparedStatement.setString(4, strDateClosed);
            preparedStatement.setString(5, strStatus);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("college added successfully.");
                return true;
            } else {
                System.out.println("Failed to add college.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }
}
