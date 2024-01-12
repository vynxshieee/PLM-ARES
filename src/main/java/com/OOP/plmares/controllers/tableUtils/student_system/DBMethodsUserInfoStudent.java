package com.OOP.plmares.controllers.tableUtils.student_system;

import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.database.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsUserInfoStudent {
    public static TableModel.StudentMasterlist getStudentDetails(String strUserID) {
        TableModel.StudentMasterlist student = null;


        String query ="SELECT * FROM vwStudentMasterlist " +
                "WHERE student_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strUserID);

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
                student = new TableModel.StudentMasterlist(
                        strStudentNo, strFullName, strGender, strCourse, strStatus, strBirthday,
                        strMobileNum, strAddress, strEmail, strActive
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return student;
    }

}
