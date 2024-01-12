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

public class DBMethodsClasslistMod {
    public static ObservableList<TableModel.ClasslistEnrollees> getClasslistEnrollees(String strSy, String strSemester, String strSubjectCode,
                                                                                      String strSection) {
        ObservableList<TableModel.ClasslistEnrollees> ClasslistEnrollees = FXCollections.observableArrayList();

        String query = "SELECT student_no, full_name, status " +
                "FROM vwSubjectSpecificEnrollees " +
                "WHERE sy = ? AND semester = ? AND subject_code = ? AND block_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy );
            preparedStatement.setString(2, strSemester );
            preparedStatement.setString(3, strSubjectCode);
            preparedStatement.setString(4, strSection);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("full_name");
                String strStatus = resultSet.getString("status");


                // Create an instance of ClasslistEnrollees and add to the list
                TableModel.ClasslistEnrollees enrolleeInfo = new TableModel.ClasslistEnrollees(
                        strStudentNo, strFullName, strStatus
                );
                ClasslistEnrollees.add(enrolleeInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return ClasslistEnrollees;
    }

    public static TableModel.SubjectSectionClasslist getClassDetails(String strSy, String strSemester, String strSubjectCode,
                                                                     String strSection) {
        TableModel.SubjectSectionClasslist classDetails = null;

        String query = "SELECT sy, semester, subject_code, faculty, block_no, description  " +
                "FROM vwSubjectSpecificEnrollees " +
                "WHERE sy = ? AND semester = ? AND subject_code = ? AND block_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy );
            preparedStatement.setString(2, strSemester );
            preparedStatement.setString(3, strSubjectCode);
            preparedStatement.setString(4, strSection);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                strSy = resultSet.getString("sy");
                strSemester = resultSet.getString("semester");
                strSubjectCode = resultSet.getString("subject_code");
                String strFaculty = resultSet.getString("faculty");
                String strBlockNo = resultSet.getString("block_no");
                String strDescription = resultSet.getString("description");


                // Create an instance of ClasslistEnrollees and add to the list
                classDetails = new TableModel.SubjectSectionClasslist(
                        strSy, strSemester, strSubjectCode, strFaculty, strSection, strDescription
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }

        return classDetails;
    }
    public static ObservableList<TableModel.ClasslistEnrollees> getGeneralClasslistEnrollees(String strSy, String strSemester, String strSection) {
        ObservableList<TableModel.ClasslistEnrollees> ClasslistEnrollees = FXCollections.observableArrayList();

        String query = "SELECT student_no, full_name, status " +
                "FROM vwCourseYearSectionClasslist " +
                "WHERE sy = ? AND semester = ? AND block_no LIKE ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy );
            preparedStatement.setString(2, strSemester );
            preparedStatement.setString(3, "%" + strSection + "%" );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("full_name");
                String strStatus = resultSet.getString("status");


                // Create an instance of ClasslistEnrollees and add to the list
                TableModel.ClasslistEnrollees enrolleeInfo = new TableModel.ClasslistEnrollees(
                        strStudentNo, strFullName, strStatus
                );
                ClasslistEnrollees.add(enrolleeInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return ClasslistEnrollees;
    }
}
