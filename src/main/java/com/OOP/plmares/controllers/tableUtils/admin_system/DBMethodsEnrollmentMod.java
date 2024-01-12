package com.OOP.plmares.controllers.tableUtils.admin_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsEnrollmentMod {
    public static ObservableList<TableModel.StudentEnrollees> getSubjectSpecificEnrollees(String strSy, String strSemester, String strSubjectCode,
                                                                                          String strSection) {
        ObservableList<TableModel.StudentEnrollees> studentEnrolleesList = FXCollections.observableArrayList();
        System.out.println("fetching StudentEnrollees");

        String query = "SELECT student_no, full_name, course_code, year_level, status " +
                "FROM vwSubjectSpecificEnrollees " +
                "WHERE sy LIKE ? AND semester LIKE ? AND subject_code = ? AND block_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, "%" + strSy + "%");
            preparedStatement.setString(2, "%" + strSemester + "%");
            preparedStatement.setString(3, strSubjectCode);
            preparedStatement.setString(4, strSection);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("full_name");
                String strCourse = resultSet.getString("course_code");
                String strYear = resultSet.getString("year_level");
                String strStatus = resultSet.getString("status");

                // Create an instance of StudentEnrollees and add to the list
                TableModel.StudentEnrollees enrolleeInfo = new TableModel.StudentEnrollees(
                        strStudentNo, strFullName, strCourse, strYear, strStatus
                );
                studentEnrolleesList.add(enrolleeInfo);
            }
            System.out.println("FETCHED");

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return studentEnrolleesList;
    }


    public static Pair<String, String> getDescriptionAndFaculty(String strSy, String strSemester, String strSubjectCode, String strSection) {
        String description = null;
        String faculty = null;

        String query = "SELECT DISTINCT description, faculty " +
                "FROM vwSubjectSpecificEnrollees " +
                "WHERE sy = ? AND semester = ? AND subject_code = ? AND block_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, strSubjectCode);
            preparedStatement.setString(4, strSection);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                description = resultSet.getString("description");
                faculty = resultSet.getString("faculty");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }

        return new Pair<>(description, faculty);
    }


    public static void deleteSubjectScheduleRecord(String strSy, String strSemester, String strSubjectCode, String strBlockNo, String strStudentNo) {
        String enrollmentQuery = "DELETE FROM enrollment " +
                "WHERE sy = '" + strSy + "' AND semester = '" + strSemester + "' AND subject_code = '" + strSubjectCode +
                "' AND block_no = '" + strBlockNo + "' AND student_no = '" + strStudentNo + "'";

        String gradeQuery = "DELETE FROM grade " +
                "WHERE sy = '" + strSy + "' AND semester = '" + strSemester + "' AND subject_code = '" + strSubjectCode +
                "' AND block_no = '" + strBlockNo + "' AND student_no = '" + strStudentNo + "'";

        // Use the executeQuery method for executing both delete operations
        DBCommonMethods.executeQuery(enrollmentQuery);
        DBCommonMethods.executeQuery(gradeQuery);
    }



    public static ObservableList<TableModel.StudentEnrollees> getEnrolleesMasterlist(String strSy, String strSemester, String strSearchTerm) {
        ObservableList<TableModel.StudentEnrollees> studentEnrolleesList = FXCollections.observableArrayList();
        System.out.println("fetching StudentEnrollees");

        String query = "SELECT student_no, full_name, course_code, year_level, reg_status " +
                "FROM vwEnrolleesMasterlist " +
                "WHERE sy = ? AND semester = ? AND (student_no LIKE ? OR full_name LIKE ?)";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, "%" + strSearchTerm + "%");
            preparedStatement.setString(4, "%" + strSearchTerm + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Retrieve data from the result set
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("full_name");
                String strCourse = resultSet.getString("course_code");
                String strYear = resultSet.getString("year_level");
                String strStatus = resultSet.getString("reg_status");

                // Create an instance of StudentEnrollees and add to the list
                TableModel.StudentEnrollees enrolleeInfo = new TableModel.StudentEnrollees(
                        strStudentNo, strFullName, strCourse, strYear, strStatus
                );
                studentEnrolleesList.add(enrolleeInfo);
            }
            System.out.println("FETCHED");

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return studentEnrolleesList;
    }

    public static void deleteEnrolleeSySem(String strSy, String strSemester, String strStudentNo) {
        String enrollmentQuery = "DELETE FROM enrollment " +
                "WHERE sy = '" + strSy + "' AND semester = '" + strSemester + "' AND student_no = '" + strStudentNo + "'";

        String gradeQuery = "DELETE FROM grade " +
                "WHERE sy = '" + strSy + "' AND semester = '" + strSemester + "' AND student_no = '" + strStudentNo + "'";

        // Use the executeQuery method for executing both delete operations
        DBCommonMethods.executeQuery(enrollmentQuery);
        DBCommonMethods.executeQuery(gradeQuery);
    }

    public static TableModel.StudentGeneralInfo getStudentGeneralInfo(String strSy, String student_no) {
        TableModel.StudentGeneralInfo studentInfo = null;

        String query = "SELECT student_no, fullname, description, status, year_level" +
                " FROM vwStudentGeneralInfo WHERE sy = ?  AND student_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1,  strSy );
            preparedStatement.setString(2, student_no);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("fullname");
                String strCourseDescription = resultSet.getString("description");
                String strStatus = resultSet.getString("status");
                String strYear = resultSet.getString("year_level");

                studentInfo = new TableModel.StudentGeneralInfo(strStudentNo, strFullName, strCourseDescription, strStatus, strYear);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }

        return studentInfo;
    }

    public static ObservableList<TableModel.IrregApprovals> getIrregApprovals(String strSy, String strSemester, String strSearchTerm) {
        ObservableList<TableModel.IrregApprovals> IrregApprovalsList = FXCollections.observableArrayList();

        String query = "SELECT student_no, full_name, course_code, year_level, pending_subjects " +
                "FROM vwStudentPendingSubjectCount " +
                "WHERE sy = ? AND semester = ? AND (student_no LIKE ? OR full_name LIKE ?)";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, "%" + strSearchTerm + "%");
            preparedStatement.setString(4, "%" + strSearchTerm + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Retrieve data from the result set
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("full_name");
                String strCourse = resultSet.getString("course_code");
                String strYear = resultSet.getString("year_level");
                int intPendingSubjects = resultSet.getInt("pending_subjects");

                // Create an instance of IrregApprovals and add to the list
                TableModel.IrregApprovals irregInfo = new TableModel.IrregApprovals(
                        strStudentNo, strFullName, strCourse, strYear, intPendingSubjects
                );
                IrregApprovalsList.add(irregInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return IrregApprovalsList;
    }

    public static ObservableList<TableModel.ApprovalSubjectInfo> getSubjectApprovalsInfo(String strSy, String strSemester, String strStudentNo) {
        ObservableList<TableModel.ApprovalSubjectInfo> subjectApplicationInfoList = FXCollections.observableArrayList();

        String query = "SELECT subject_code, description, block_no, day_time_modality, units, college_code, slots, queue, status " +
                "FROM vwStudentReviewApplication " +
                "WHERE sy = ? AND semester = ? AND student_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, strStudentNo);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strSubjectCode = resultSet.getString("subject_code");
                String strDescription = resultSet.getString("description");
                String strBlock_no = resultSet.getString("block_no");
                String strDayTimeModality = resultSet.getString("day_time_modality");
                int intUnits = resultSet.getInt("units");
                String strCollege_code = resultSet.getString("college_code");
                int intSlots = resultSet.getInt("slots");
                int intQueue = resultSet.getInt("queue");
                String strStatus = resultSet.getString("status");

                // Create an instance of ApprovalSubjectInfo and add to the list
                TableModel.ApprovalSubjectInfo subjectInfo = new TableModel.ApprovalSubjectInfo(
                        strSubjectCode, strDescription, strBlock_no, strDayTimeModality,
                        intUnits, strCollege_code, intSlots, intQueue, strStatus
                );
                subjectApplicationInfoList.add(subjectInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return subjectApplicationInfoList;
    }

    public static Boolean removePendingSubjectApplication(String strSy, String strSemester, String strStudentNo, String strSubjectCode, String strBlockNo) {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to remove this subject application?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation != JOptionPane.YES_OPTION) {
            return false;
        }

        String query = String.format("DELETE FROM enrollment WHERE sy = '%s' AND semester = '%s' AND student_no = '%s' AND subject_code = '%s' AND block_no = '%s'",
                strSy, strSemester, strStudentNo, strSubjectCode, strBlockNo);

        try {
            DBCommonMethods.executeQuery(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void approveSubjectApplication(String strSy, String strSemester, String strStudentNo, String strSubjectCode, String strBlockNo) {
        String updateQuery = String.format("UPDATE enrollment SET status = 'approved' " +
                        "WHERE sy = '%s' AND semester = '%s' AND student_no = '%s' AND subject_code = '%s' AND block_no = '%s'",
                strSy, strSemester, strStudentNo, strSubjectCode, strBlockNo);

        String insertQuery = String.format("INSERT INTO grade VALUES ('%s', '%s', '%s', '%s', '%s', 0)",
                strSy, strSemester, strStudentNo, strSubjectCode, strBlockNo);

        try {
            DBCommonMethods.executeQuery(updateQuery);
            DBCommonMethods.executeQuery(insertQuery);
            System.out.println("Subject application approved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRawStudentSchedule(String strSy, String strSemester, String strStudentNo) {
        String strRawSchedule = "";

        String query = "SELECT schedule FROM vwFormattedStudentSchedule " +
                "WHERE sy = ? AND semester = ? AND student_no = ? LIMIT 1";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, strStudentNo);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the ResultSet has at least one row
            if (resultSet.next()) {
                strRawSchedule = resultSet.getString("schedule");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return strRawSchedule;
    }


}
