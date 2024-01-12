package com.OOP.plmares.controllers.tableUtils.admin_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsEmployeeMasterlist {
    public static ObservableList<TableModel.EmployeeMasterlist> getEmployeeMasterlist(String strEmployeeSearch) {
        ObservableList<TableModel.EmployeeMasterlist> EmployeeMasterlist = FXCollections.observableArrayList();

        String query ="SELECT * FROM vwEmployeeMasterlist " +
                "WHERE employee_id LIKE ? OR full_name LIKE ? ";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, "%" + strEmployeeSearch + "%");
            preparedStatement.setString(2, "%" + strEmployeeSearch + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strEmployeeID = resultSet.getString("employee_id");
                String strFullName = resultSet.getString("full_name");
                String strEmail = resultSet.getString("email");
                String strGender = resultSet.getString("gender");
                String strMobileNum = resultSet.getString("cp_num");
                String strAddress = resultSet.getString("address");
                String strBirthday = resultSet.getString("bday");
                String strActive = resultSet.getString("active");


                // Create an instance of EmployeeMasterlist and add to the list
                TableModel.EmployeeMasterlist student = new TableModel.EmployeeMasterlist(
                        strEmployeeID, strFullName, strEmail, strGender, strMobileNum, strAddress,
                        strBirthday, strActive
                );
                EmployeeMasterlist.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return EmployeeMasterlist;
    }

    public static boolean deleteEmployeeMasterlist(String strEmployeeID) {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete the information for employee " + strEmployeeID + "?\n\n"
                        + "Note: Deleting this information may also affect other existing records.",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        if (result != JOptionPane.YES_OPTION) {
            // User chose not to delete, return false
            return false;
        }

        try (Connection connection = new ConnectDB().Connect()) {
            // Delete from employee table
            DBCommonMethods.deleteRecord(connection, "employee", "employee_id", strEmployeeID);

            // Delete from login_credential table
            DBCommonMethods.deleteRecord(connection, "login_credential", "user_id", strEmployeeID);
            System.out.println("employee information deleted successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

    public static boolean editEmployeeMasterlist(String strEmployeeID, String strLastName, String strFirstName,
                                                String strPLMEmail, String strMobileNum, String strBirthday,
                                                String strGender, String strActive, String strAddress) {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to edit the information for employee " + strEmployeeID + "?\n\n"
                        + "Note: Editing this information may also affect other existing records.",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        if (result != JOptionPane.YES_OPTION) {
            return false;
        }

        String insertQuery = "UPDATE employee SET " +
                "lastname = ?, firstname = ?, email = ?, gender = ?, " +
                "cp_num = ?, address = ?, bday = ?, status = ?" +
                "WHERE employee_id = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, strLastName);
            preparedStatement.setString(2, strFirstName);
            preparedStatement.setString(3, strPLMEmail);
            preparedStatement.setString(4, strGender);
            preparedStatement.setString(5, strMobileNum);
            preparedStatement.setString(6, strAddress);
            preparedStatement.setString(7, strBirthday);
            preparedStatement.setString(8, strActive);
            preparedStatement.setString(9, strEmployeeID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee information updated successfully.");
                return true;
            } else {
                System.out.println("No matching employee record found for update.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

    public static boolean addEmployeeMasterlist(String strEmployeeID, String strLastName, String strFirstName,
                                                String strPLMEmail, String strMobileNum, String strBirthday,
                                                String strGender, String strActive, String strAddress) {

        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to add the information for Employee " + strEmployeeID + "?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (result != JOptionPane.YES_OPTION) {
            return false;
        }

        String addEmployeeQuery = "INSERT INTO employee(employee_id, lastname, firstname, email, gender, cp_num, address, bday, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String addCredentialQuery = "INSERT INTO login_credential (user_id, password, type) VALUES (?, ?, 'E')";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement employeeStatement = connection.prepareStatement(addEmployeeQuery);
             PreparedStatement credentialStatement = connection.prepareStatement(addCredentialQuery)) {

            // Set parameters for the employee query
            employeeStatement.setString(1, strEmployeeID);
            employeeStatement.setString(2, strLastName);
            employeeStatement.setString(3, strFirstName);
            employeeStatement.setString(4, strPLMEmail);
            employeeStatement.setString(5, strGender);
            employeeStatement.setString(6, strMobileNum);
            employeeStatement.setString(7, strAddress);
            employeeStatement.setString(8, strBirthday);
            employeeStatement.setString(9, strActive);

            // Set parameters for the credential query
            credentialStatement.setString(1, strEmployeeID);
            credentialStatement.setString(2, CommonUtils.generateRandomPassword());

            // Execute the employee query
            int employeeRowsAffected = employeeStatement.executeUpdate();

            // Execute the credential query
            int credentialRowsAffected = credentialStatement.executeUpdate();

            if (employeeRowsAffected > 0 && credentialRowsAffected > 0) {
                System.out.println("Employee information and login credentials added successfully.");
                return true;
            } else {
                System.out.println("Failed to add employee information or login credentials.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }


    public static String getMaxEmployeeID(String strSy) {
        String maxEmployeeID = null;

        String query = "SELECT max_employee_id FROM vwrMaxEmployeeId";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                maxEmployeeID = resultSet.getString("max_employee_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxEmployeeID;
    }
}
