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

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showDuplicateEntryWarning;
import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsSubjectSchedulingMod {
    public static ObservableList<TableModel.SubjectModuleInfo> getSubjects(String strSearchTerm) {
        ObservableList<TableModel.SubjectModuleInfo> subjectModuleInfo = FXCollections.observableArrayList();

        String query ="SELECT * FROM subject WHERE subject_code LIKE ? OR description LIKE ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + strSearchTerm + "%");
            preparedStatement.setString(2, "%" + strSearchTerm + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strSubjectCode = resultSet.getString("subject_code");
                String strDescription = resultSet.getString("description");
                int intUnits = resultSet.getInt("units");
                String strCurriculum = resultSet.getString("curriculum");
                String strCollegeCode = resultSet.getString("college_code");
                String strStatus = resultSet.getString("status");

                // Create an instance of SubjectModuleInfo and add to the list
                TableModel.SubjectModuleInfo course = new TableModel.SubjectModuleInfo(
                        strSubjectCode, strDescription, intUnits, strCurriculum, strCollegeCode, strStatus
                );
                subjectModuleInfo.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return subjectModuleInfo;
    }
    public static boolean deleteSubjectModule(String strSubjectCode) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete the information for subject " + strSubjectCode + "?\n\n"
                + "Note: Deleting this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        try (Connection connection = new ConnectDB().Connect()) {
            DBCommonMethods.deleteRecord(connection, "subject", "subject_code", strSubjectCode);
            System.out.println("Subject information deleted successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

    public static boolean editSubjectModule(String strSubjectCode, String strDescription, int intUnits, String strCurriculum,
                                            String strCollegeCode, String strStatus) {
        // Show a confirmation dialog to ensure the user wants to edit the course's information
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to edit the information for subject " + strSubjectCode + "?\n\n"
                + "Note: Editing this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            String updateQuery = "UPDATE subject SET " +
                    "description = ?, units = ?, curriculum = ?, college_code = ?, status = ? " +
                    "WHERE subject_code = ?";

            try (Connection connection = new ConnectDB().Connect();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                preparedStatement.setString(1, strDescription);
                preparedStatement.setInt(2, intUnits);
                preparedStatement.setString(3, strCurriculum);
                preparedStatement.setString(4, strCollegeCode);
                preparedStatement.setString(5, strStatus);
                preparedStatement.setString(6, strSubjectCode);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Subject information updated successfully.");
                    return true;
                } else {
                    System.out.println("No matching subject record found for update.");
                    return false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showGenericErrorWarning();
                return false;
            }
        } else {
            // User clicked CANCEL or closed the dialog
            return false;
        }
    }

    public static boolean addSubjectModule(String strSubjectCode, String strDescription, int intUnits, String strCurriculum,
                                          String strCollegeCode, String strStatus) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to add the information for subject " + strSubjectCode + "?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        String addQuery = "INSERT INTO subject (subject_code, description, units, curriculum, college_code, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {

            preparedStatement.setString(1, strSubjectCode);
            preparedStatement.setString(2, strDescription);
            preparedStatement.setInt(3, intUnits);
            preparedStatement.setString(4, strCurriculum);
            preparedStatement.setString(5, strCollegeCode);
            preparedStatement.setString(6, strStatus);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Subject added successfully.");
                return true;
            } else {
                System.out.println("Failed to add subject.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }


    // schedule DBMethods

    public static ObservableList<TableModel.ScheduleModule> getSchedules(String strSubjectSearchTerm, String strFacultySearchTerm, String strSectionSearchTerm,
                                                                         String strDay, String strCollegeCode) {
        ObservableList<TableModel.ScheduleModule> ScheduleModule = FXCollections.observableArrayList();

        String query ="SELECT * FROM vwScheduleDetails\n" +
                "WHERE (subject_code LIKE ? OR description LIKE ?)\n" +
                "      AND COALESCE(faculty, '') LIKE ?\n" +
                "      AND block_no LIKE ? AND day LIKE ? AND college_code LIKE ?\n";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + strSubjectSearchTerm + "%");
            preparedStatement.setString(2, "%" + strSubjectSearchTerm + "%");
            preparedStatement.setString(3, "%" + strFacultySearchTerm + "%");
            preparedStatement.setString(4, "%" + strSectionSearchTerm + "%");
            preparedStatement.setString(5, "%" + strDay + "%");
            preparedStatement.setString(6, "%" + strCollegeCode + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strSubjectCode = resultSet.getString("subject_code");
                String strDescription = resultSet.getString("description");
                String strSemester = resultSet.getString("semester");
                strCollegeCode = resultSet.getString("college_code");
                String strSection = resultSet.getString("block_no");
                strDay = resultSet.getString("day");
                String strTime = resultSet.getString("time");
                String strRoom = resultSet.getString("room");
                String strType = resultSet.getString("type");
                String strSequence = resultSet.getString("sequence_no");
                String strFaculty = resultSet.getString("faculty");

                // Create an instance of ScheduleModule and add to the list
                TableModel.ScheduleModule schedule = new TableModel.ScheduleModule(
                        strSemester, strCollegeCode, strSection, strSubjectCode, strDescription, strDay,
                        strTime, strRoom, strType, strSequence, strFaculty
                );
                ScheduleModule.add(schedule);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return ScheduleModule;
    }
    public static ObservableList<TableModel.SubjectOptions> getSubjectOptions(String strSubjectSearchTerm) {

        ObservableList<TableModel.SubjectOptions> subjectOptionList = FXCollections.observableArrayList();

        String query ="SELECT DISTINCT subject_code, description FROM subject WHERE subject_code LIKE ? OR description LIKE ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + strSubjectSearchTerm + "%");
            preparedStatement.setString(2, "%" + strSubjectSearchTerm + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strSubjectCode = resultSet.getString("subject_code");
                String strDescription = resultSet.getString("description");

                // Create an instance of subjectOption and add to the list
                TableModel.SubjectOptions subjectOption = new TableModel.SubjectOptions(
                        strSubjectCode, strDescription
                );
                subjectOptionList.add(subjectOption);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return subjectOptionList;
    }

    public static ObservableList<TableModel.FacultyOptions> getEmployeeOptions(String strFacultySearchTerm) {

        ObservableList<TableModel.FacultyOptions> facultyOptionList = FXCollections.observableArrayList();

        String query ="SELECT DISTINCT employee_id, full_name FROM vwEmployeeMasterlist WHERE employee_id LIKE ? OR full_name LIKE ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + strFacultySearchTerm + "%");
            preparedStatement.setString(2, "%" + strFacultySearchTerm + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                String strEmployeeID = resultSet.getString("employee_id");
                String strFullName = resultSet.getString("full_name");

                // Create an instance of subjectOption and add to the list
                TableModel.FacultyOptions employeeOption = new TableModel.FacultyOptions(
                        strEmployeeID, strFullName
                );
                facultyOptionList.add(employeeOption);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
        return facultyOptionList;
    }

    public static boolean editScheduleModule(String strSubjectCode, String strCollegeCode, String strSemester, String strSequence,
                                             String strBlockNo, String strDay, String strType, String strRoom, String strTime, String strFacultyID) {
        // Show a confirmation dialog to ensure the user wants to edit the course's information
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to edit the information for the selected schedule?\n\n"
                + "Note: Editing this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        String updateQuery = "UPDATE subject_schedule SET " +
                "day = ?, time = ?, room = ?, type = ?, faculty_id = ? " +
                "WHERE semester = ? AND college_code = ? AND block_no = ? AND subject_code = ? AND sequence_no = ?";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, strDay);
            preparedStatement.setString(2, strTime);
            preparedStatement.setString(3, strRoom);
            preparedStatement.setString(4, strType);
            preparedStatement.setString(5, strFacultyID);
            preparedStatement.setString(6, strSemester);
            preparedStatement.setString(7, strCollegeCode);
            preparedStatement.setString(8, strBlockNo);
            preparedStatement.setString(9, strSubjectCode);
            preparedStatement.setString(10, strSequence);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Schedule information updated successfully.");
                return true;
            } else {
                System.out.println("No matching subject Schedule found for update.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }
    public static boolean addScheduleModule(String strSubjectCode, String strCollegeCode, String strSemester, String strSequence,
                                            String strBlockNo, String strDay, String strType, String strRoom, String strTime, String strFacultyID) {

        String addQuery = "INSERT INTO subject_schedule VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {

            preparedStatement.setString(1, strSemester);
            preparedStatement.setString(2, strCollegeCode);
            preparedStatement.setString(3, strBlockNo);
            preparedStatement.setString(4, strSubjectCode);
            preparedStatement.setString(5, strDay);
            preparedStatement.setString(6, strTime);
            preparedStatement.setString(7, strRoom);
            preparedStatement.setString(8, strType);
            preparedStatement.setString(9, strSequence);
            preparedStatement.setString(10, strFacultyID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Schedule added successfully.");
                return true;
            } else {
                System.out.println("Failed to add schedule.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showDuplicateEntryWarning();
            return false;
        }
    }

    public static boolean deleteScheduleModule(String strSubjectCode, String strCollegeCode, String strSemester, String strSequence, String strBlockNo) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete the information for schedule:\n"
                + strSubjectCode + " [" + strBlockNo + "-Seq " + strSequence + "]" + "?\n\n"
                + "Note: Deleting this information may also affect other existing records.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return false;
        }

        try (Connection connection = new ConnectDB().Connect()) {
            // delete query
            String deleteQuery = "DELETE FROM subject_schedule " +
                    "WHERE semester = ? AND college_code = ? AND block_no = ? AND subject_code = ? AND sequence_no = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                // set parameters
                preparedStatement.setString(1, strSemester);
                preparedStatement.setString(2, strCollegeCode);
                preparedStatement.setString(3, strBlockNo);
                preparedStatement.setString(4, strSubjectCode);
                preparedStatement.setString(5, strSequence);

                // execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Schedule information deleted successfully.");
                    return true;
                } else {
                    System.out.println("No records deleted. Check the provided parameters.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            return false;
        }
    }

}
