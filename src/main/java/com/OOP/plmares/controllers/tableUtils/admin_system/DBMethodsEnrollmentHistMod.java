package com.OOP.plmares.controllers.tableUtils.admin_system;

import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsEnrollmentHistMod {
    public static ObservableList<TableModel.StudentEnrollmentHistory> getEnrolleesMasterlist(String strSy, String strSemester, String strCollegeCode,
                                                                                             String strCourseCode, String strStudentSearch) {
        ObservableList<TableModel.StudentEnrollmentHistory> enrolleesMasterlist = FXCollections.observableArrayList();

        String query = "SELECT student_no, full_name, course_code, year_level, reg_status, sy, semester, active " +
                "FROM vwEnrolleesMasterlist " +
                "WHERE sy LIKE ? AND semester LIKE ? " +
                "AND (student_no LIKE ? OR full_name LIKE ?) " +
                "AND college_code LIKE ? AND course_code LIKE ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, "%" + strSy + "%");
            preparedStatement.setString(2, "%" + strSemester + "%");
            preparedStatement.setString(3, "%" + strStudentSearch + "%");
            preparedStatement.setString(4, "%" + strStudentSearch + "%");
            preparedStatement.setString(5, "%" + strCollegeCode + "%");
            preparedStatement.setString(6, "%" + strCourseCode + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("full_name");
                strCourseCode = resultSet.getString("course_code");
                int intYearLvl = resultSet.getInt("year_level");
                String strRegStatus = resultSet.getString("reg_status");
                strSy = resultSet.getString("sy");
                strSemester = resultSet.getString("semester");
                String strActive = resultSet.getString("active");

                // Create an instance of StudentEnrollmentHistory and add to the list
                TableModel.StudentEnrollmentHistory enrollee = new TableModel.StudentEnrollmentHistory(
                        strStudentNo, strFullName, strCourseCode,
                        Integer.toString(intYearLvl), strRegStatus, strSy, strSemester, strActive
                );
                enrolleesMasterlist.add(enrollee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return enrolleesMasterlist;
    }
}
