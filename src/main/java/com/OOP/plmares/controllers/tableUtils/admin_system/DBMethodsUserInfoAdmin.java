package com.OOP.plmares.controllers.tableUtils.admin_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.database.ConnectDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsUserInfoAdmin {
    public static TableModel.EmployeeMasterlist getEmployeeDetails(String strUserID) {
        TableModel.EmployeeMasterlist employee = null;

        String query ="SELECT * FROM vwEmployeeMasterlist " +
                "WHERE employee_id = ? ";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1,  strUserID);

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
                employee = new TableModel.EmployeeMasterlist(
                        strEmployeeID, strFullName, strEmail, strGender, strMobileNum, strAddress,
                        strBirthday, strActive
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return employee;
    }

    public static boolean updateAdminPassword(String strNewPass, String strUserID) {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to change your password?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        if (result != JOptionPane.YES_OPTION) {
            // User chose not to delete, return false
            return false;
        }

        try{
            DBCommonMethods.updateDBValues("login_credential", "password", "user_id", strNewPass, strUserID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }

    }
}
