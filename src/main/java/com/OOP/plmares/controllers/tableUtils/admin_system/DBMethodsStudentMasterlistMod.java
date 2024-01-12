package com.OOP.plmares.controllers.tableUtils.admin_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.utilities.CommonUtils;
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

public class DBMethodsStudentMasterlistMod {
    public static ObservableList<TableModel.StudentMasterlist> getStudentMasterlist(String strStudentSearch) {
        ObservableList<TableModel.StudentMasterlist> studentMasterlist = FXCollections.observableArrayList();

        String query ="SELECT * FROM vwStudentMasterlist " +
                "WHERE student_no LIKE ? OR full_name LIKE ? ";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, "%" + strStudentSearch + "%");
            preparedStatement.setString(2, "%" + strStudentSearch + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("full_name");
                String strGender = resultSet.getString("gender");
                String strCourse = resultSet.getString("course_code");
                String strBirthday = resultSet.getString("bday");
                String strMobileNum = resultSet.getString("cp_num");
                String strStatus = resultSet.getString("status");
                String strAddress = resultSet.getString("address");
                String strEmail = resultSet.getString("plm_email");
                String strActive = resultSet.getString("active");

                // Create an instance of StudentMasterlist and add to the list
                TableModel.StudentMasterlist student = new TableModel.StudentMasterlist(
                        strStudentNo, strFullName, strGender, strCourse, strStatus, strBirthday,
                        strMobileNum, strAddress, strEmail, strActive
                );
                studentMasterlist.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return studentMasterlist;
    }

    public static boolean editStudentMasterlist(String strStudentNo, String strLastName, String strFirstName,
                                                String strPLMEmail, String strMobileNum, String strBirthday,
                                                String strGender, String strActive, String strAddress,
                                                String strCourse, String strStatus) {
        // Show a confirmation dialog to ensure the user wants to edit the student's information
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to edit the information for student " + strStudentNo + "?\n\n"
                + "Note: Editing this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        String updateQuery = "UPDATE student SET " +
                "lastname = ?, firstname = ?, email = ?, gender = ?, course_code = ?, " +
                "cp_num = ?, address = ?, bday = ?, status = ?, reg_status = ? " +
                "WHERE student_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, strLastName);
            preparedStatement.setString(2, strFirstName);
            preparedStatement.setString(3, strPLMEmail);
            preparedStatement.setString(4, strGender);
            preparedStatement.setString(5, strCourse);
            preparedStatement.setString(6, strMobileNum);
            preparedStatement.setString(7, strAddress);
            preparedStatement.setString(8, strBirthday);
            preparedStatement.setString(9, strActive);
            preparedStatement.setString(10, strStatus);
            preparedStatement.setString(11, strStudentNo);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student information updated successfully.");
                return true;
            } else {
                System.out.println("No matching student record found for update.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

    public static boolean addStudentMasterlist(String strStudentNo, String strLastName, String strFirstName,
                                               String strPLMEmail, String strMobileNum, String strBirthday,
                                               String strGender, String strActive, String strAddress,
                                               String strCourse, String strStatus) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to add the information for student " + strStudentNo + "?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        String addStudentQuery = "INSERT INTO student (student_no, lastname, firstname, email, gender, course_code, cp_num, address, bday, status, reg_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String addCredentialQuery = "INSERT INTO login_credential (user_id, password, type) VALUES (?, ?, 'S')";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement studentStatement = connection.prepareStatement(addStudentQuery);
             PreparedStatement credentialStatement = connection.prepareStatement(addCredentialQuery)) {

            // Set parameters for the student query
            studentStatement.setString(1, strStudentNo);
            studentStatement.setString(2, strLastName);
            studentStatement.setString(3, strFirstName);
            studentStatement.setString(4, strPLMEmail);
            studentStatement.setString(5, strGender);
            studentStatement.setString(6, strCourse);
            studentStatement.setString(7, strMobileNum);
            studentStatement.setString(8, strAddress);
            studentStatement.setString(9, strBirthday);
            studentStatement.setString(10, strActive);
            studentStatement.setString(11, strStatus);

            // Set parameters for the credential query
            credentialStatement.setString(1, strStudentNo);
            credentialStatement.setString(2, CommonUtils.generateRandomPassword());

            // Execute the student query
            int studentRowsAffected = studentStatement.executeUpdate();

            // Execute the credential query
            int credentialRowsAffected = credentialStatement.executeUpdate();

            if (studentRowsAffected > 0 && credentialRowsAffected > 0) {
                System.out.println("Student information and login credentials added successfully.");
                return true;
            } else {
                System.out.println("Failed to add student information or login credentials.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }


    public static String getMaxStudentNo(String strSy) {
        String maxStudentNo = null;

        String query = "SELECT max_student_no FROM vwSchoolYearMaxStdNum WHERE sy = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, strSy);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                maxStudentNo = resultSet.getString("max_student_no");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxStudentNo;
    }

    public static boolean deleteStudentMasterlist(String strStudentNo) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete the information for student " + strStudentNo + "?\n\n"
                + "Note: Deleting this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        try (Connection connection = new ConnectDB().Connect()) {
            // Delete from student table
            DBCommonMethods.deleteRecord(connection, "student", "student_no", strStudentNo);

            // Delete from login_credential table
            DBCommonMethods.deleteRecord(connection, "login_credential", "user_id", strStudentNo);

            System.out.println("Student information and login credentials deleted successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }


}
