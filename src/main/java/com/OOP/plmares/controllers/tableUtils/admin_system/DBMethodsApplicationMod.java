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

public class DBMethodsApplicationMod {


    // ----- 1.) Query to populate and filter Application module's potential irregular applicants
    public static ObservableList<TableModel.IrregPotentialApplicant> getAllIrregPotentialApplicants(String strSy, String strSemester, String strSearchTerm) {

        ObservableList<TableModel.IrregPotentialApplicant> irregApplicants = FXCollections.observableArrayList();

        String query = "SELECT student_no, full_name, course_code, year_level, status, units_enrolled" +
                " FROM vwIrregPotentialApplicants WHERE (sy = ? AND semester = ?" +
                " AND (student_no LIKE ? OR full_name LIKE ?))";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filter of current sy and sem, and search for student_no or full_name
            preparedStatement.setString(1,  strSy );
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, "%" + strSearchTerm + "%"); // Replace searchTerm with the actual search term
            preparedStatement.setString(4, "%" + strSearchTerm + "%"); // Replace searchTerm with the actual search term

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String strStudentNo = resultSet.getString("student_no");
                String strFullName = resultSet.getString("full_name");
                String strCourse = resultSet.getString("course_code");
                String strYear = resultSet.getString("year_level");
                String strStatus = resultSet.getString("status");
                int intUnitsEnrolled = resultSet.getInt("units_enrolled");

                // instance of IrregPotentialApplicant and add to the list
                TableModel.IrregPotentialApplicant applicant = new TableModel.IrregPotentialApplicant(strStudentNo, strFullName, strCourse, strYear, strStatus, intUnitsEnrolled);
                irregApplicants.add(applicant);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }

        return irregApplicants;
    }



    // ----- 2.) Query to populate and filter Subject Application with subjects according to the selected student and potential filters
    public static ObservableList<TableModel.GeneralSubjectInfo> getSubjectApplicationInfo(String strSy, String strSemester, String strStudentNo, String strSubjSearchTerm,
                                                                                          String strSecSearchTerm, String strSchedule, String strCollegeCode,
                                                                                          String strRemark) {
        ObservableList<TableModel.GeneralSubjectInfo> subjectApplicationInfoList = FXCollections.observableArrayList();
        System.out.println("fetching SubjectApplicationInfo");

        String query = "SELECT subject_code, description, block_no, day_time_modality, units, faculty, college_code, slots, queue, remark, class_count " +
                "FROM vwSubjectApplicationInfo " +
                "WHERE sy = ? AND semester = ? AND student_no = ? " +
                "AND (subject_code LIKE ? OR description LIKE ?) " +
                "AND block_no LIKE ? AND day_time_modality LIKE ? AND college_code LIKE ? AND remark LIKE ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // parameters with wildcards for filtering
            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, strStudentNo);
            preparedStatement.setString(4, "%" + strSubjSearchTerm + "%");
            preparedStatement.setString(5, "%" + strSubjSearchTerm + "%");
            preparedStatement.setString(6, "%" + strSecSearchTerm + "%");
            preparedStatement.setString(7, "%" + strSchedule + "%");
            preparedStatement.setString(8, "%" + strCollegeCode + "%");
            preparedStatement.setString(9, "%" + strRemark + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String subjectCode = resultSet.getString("subject_code");
                String description = resultSet.getString("description");
                String block_no = resultSet.getString("block_no");
                String dayTimeModality = resultSet.getString("day_time_modality");
                int units = resultSet.getInt("units");
                String faculty = resultSet.getString("faculty");
                String college_code = resultSet.getString("college_code");
                int classCount = resultSet.getInt("class_count");
                int slots = resultSet.getInt("slots");
                int queue = resultSet.getInt("queue");
                String remark = resultSet.getString("remark");

                // Create an instance of GeneralSubjectInfo and add to the list
                TableModel.GeneralSubjectInfo subjectInfo = new TableModel.GeneralSubjectInfo(
                        subjectCode, description, block_no, dayTimeModality,
                        units, faculty, college_code, classCount, slots, queue, remark
                );
                subjectApplicationInfoList.add(subjectInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return subjectApplicationInfoList;
    }


    // ----- 4.) addApplicationPendingSchedule - using the selected row from the table, generate a corresponding insert query
    public static Boolean addApplicationPendingSchedule(String strSy, String strSemester, String strStudentNo, String strSubjectCode, String strBlockNo) {
        String query = "SELECT * FROM vwSubjectApplicationExtractInfo " +
                "WHERE sy = ? AND semester = ? AND student_no = ? AND subject_code = ? AND block_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, strSy);
            preparedStatement.setString(2, strSemester);
            preparedStatement.setString(3, strStudentNo);
            preparedStatement.setString(4, strSubjectCode);  // Adjusted for wildcard
            preparedStatement.setString(5, strBlockNo);      // Adjusted for wildcard

            ResultSet resultSet = preparedStatement.executeQuery();

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to apply to the subject?\nSubject Code: " + strSubjectCode + "\nSection: " + strBlockNo);

            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            while (resultSet.next()) {
                String strCollegeCode = resultSet.getString("college_code");
                String strDay = resultSet.getString("day");
                String strTime = resultSet.getString("time");
                String strRoom = resultSet.getString("room");
                String strType = resultSet.getString("type");
                String strSequenceNo = resultSet.getString("sequence_no");
                String strEmployeeId = resultSet.getString("employee_id");

                if (result == ButtonType.OK) {
                    // Create INSERT statement for enrollment table
                    String insertSubjectScheduleQuery = String.format("INSERT INTO enrollment VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', 'pending');",
                            strSy, strSemester, strCollegeCode, strStudentNo, strBlockNo, strSubjectCode, strDay, strTime, strRoom, strType, strSequenceNo, strEmployeeId);

                    // Execute the insertQuery for the enrollment table
                    DBCommonMethods.executeQuery(insertSubjectScheduleQuery);
                } else {
                    System.out.println("Application cancelled!");
                }
            }

            return result == ButtonType.OK;

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return false;
    }



}
