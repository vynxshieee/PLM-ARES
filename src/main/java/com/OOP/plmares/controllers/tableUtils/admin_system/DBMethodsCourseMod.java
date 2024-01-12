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

public class DBMethodsCourseMod {
    public static ObservableList<TableModel.CourseModule> getCourses(String strSearchTerm) {
        ObservableList<TableModel.CourseModule> CourseModuleList = FXCollections.observableArrayList();

        String query ="SELECT * FROM course WHERE course_code LIKE ? OR description LIKE ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + strSearchTerm + "%");
            preparedStatement.setString(2, "%" + strSearchTerm + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strCourseCode = resultSet.getString("course_code");
                String strDescription = resultSet.getString("description");
                String strCollegeCode = resultSet.getString("college_code");
                String strDateOpened = resultSet.getString("date_opened");
                String strDateClosed = resultSet.getString("date_closed");
                String strStatus = resultSet.getString("status");

                // Create an instance of CourseModule and add to the list
                TableModel.CourseModule course = new TableModel.CourseModule(
                        strCourseCode, strDescription, strCollegeCode, strDateOpened, strDateClosed, strStatus
                );
                CourseModuleList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return CourseModuleList;
    }

    public static boolean deleteCourseModule(String strCourseCode) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete the information for course " + strCourseCode + "?\n\n"
                + "Note: Deleting this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        try (Connection connection = new ConnectDB().Connect()) {
            DBCommonMethods.deleteRecord(connection, "course", "course_code", strCourseCode);
            System.out.println("Course information deleted successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

    public static boolean editCourseModule(String strCourseCode, String strDescription, String strCollegeCode,
                                                String strDateOpened, String strDateClosed, String strStatus) {
        // Show a confirmation dialog to ensure the user wants to edit the course's information
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to edit the information for course " + strCourseCode + "?\n\n"
                + "Note: Editing this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        String updateQuery = "UPDATE course SET " +
                "description = ?, college_code = ?, date_opened = ?, date_closed = ?, status = ? " +
                "WHERE course_code = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, strDescription);
            preparedStatement.setString(2, strCollegeCode);
            preparedStatement.setString(3, strDateOpened);
            preparedStatement.setString(4, strDateClosed);
            preparedStatement.setString(5, strStatus);
            preparedStatement.setString(6, strCourseCode);


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Course information updated successfully.");
                return true;
            } else {
                System.out.println("No matching Course record found for update.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

    public static boolean addCourseModule(String strCourseCode, String strDescription, String strCollegeCode,
                                               String strDateOpened, String strDateClosed, String strStatus) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to add the information for course " + strCourseCode + "?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        String addQuery = "INSERT INTO course (course_code, description, college_code, date_opened, date_closed, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {

            preparedStatement.setString(1, strCourseCode);
            preparedStatement.setString(2, strDescription);
            preparedStatement.setString(3, strCollegeCode);
            preparedStatement.setString(4, strDateOpened);
            preparedStatement.setString(5, strDateClosed);
            preparedStatement.setString(6, strStatus);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Course added successfully.");
                return true;
            } else {
                System.out.println("Failed to add course.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }
}
