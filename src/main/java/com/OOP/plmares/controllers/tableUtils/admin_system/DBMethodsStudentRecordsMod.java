package com.OOP.plmares.controllers.tableUtils.admin_system;

import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsStudentRecordsMod {
    public static ObservableList<TableModel.StudentRecordSubject> getStudentRecords(String strSy, String strSemester, String strStudentNo) {
        ObservableList<TableModel.StudentRecordSubject> studentRecordsList = FXCollections.observableArrayList();

        String query = "SELECT subject_code, description, units, grade, remark " +
                "FROM vwStudentGradeRecords " +
                "WHERE sy = ? AND semester LIKE ? AND student_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, "%" + strSemester + "%");
            preparedStatement.setString(3, strStudentNo);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String strSubjectCode = resultSet.getString("subject_code");
                String strDescription = resultSet.getString("description");
                int intUnits = resultSet.getInt("units");
                String strGrade = resultSet.getString("grade");
                String strRemark = resultSet.getString("remark");

                TableModel.StudentRecordSubject stdRecord = new TableModel.StudentRecordSubject(
                        strSubjectCode, strDescription, intUnits, strGrade, strRemark
                );
                studentRecordsList.add(stdRecord);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return studentRecordsList;
    }

    public static Pair<String, String> getGwaAndUnitsTaken(String strSy, String strSem, String strStudentNo) {
        String strGwa = null;
        String strUnitsTaken = null;

        try (Connection connection = new ConnectDB().Connect()) {
            String query = "SELECT gwa, total_units FROM vwGWAPerSemSy WHERE sy = ? AND semester LIKE ? AND student_no = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, strSy);
                preparedStatement.setString(2, "%" + strSem + "%");
                preparedStatement.setString(3, strStudentNo);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        strGwa = resultSet.getString("gwa");
                        strUnitsTaken = resultSet.getString("total_units");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the Pair of strings
        return new Pair<>(strGwa, strUnitsTaken);
    }
}
