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

public class DBMethodsGradeEntryMod {
    public static ObservableList<TableModel.GradeEntryStudent> getGradeRecords(String strSy, String strSemester, String strSubject_code, String strSection) {
        ObservableList<TableModel.GradeEntryStudent> gradeRecordList = FXCollections.observableArrayList();

        String query = "SELECT student_no, full_name, grade, remark " +
                "FROM vwSubjectGradeEntry " +
                "WHERE sy = ? AND semester = ? AND subject_code = ? AND block_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, strSubject_code);
            preparedStatement.setString(4, strSection);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String strSubjectCode = resultSet.getString("student_no");
                String strDescription = resultSet.getString("full_name");
                double dblGrade = resultSet.getDouble("grade");
                String strRemark = resultSet.getString("remark").toUpperCase();

                TableModel.GradeEntryStudent stdRecord = new TableModel.GradeEntryStudent(
                        strSubjectCode, strDescription, dblGrade, strRemark
                );
                gradeRecordList.add(stdRecord);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return gradeRecordList;
    }

    public static void updateGrades(String strSy, String strSemester, String strStudentNo, String strSubjectCode, String strBlockNo, double dblGrade) {
        String updateQuery = "UPDATE grade SET grade = ? WHERE sy = ? AND semester = ? AND student_no = ? AND subject_code = ? AND block_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, dblGrade);
            preparedStatement.setString(2, strSy);
            preparedStatement.setString(3, strSemester);
            preparedStatement.setString(4, strStudentNo);
            preparedStatement.setString(5, strSubjectCode);
            preparedStatement.setString(6, strBlockNo);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
