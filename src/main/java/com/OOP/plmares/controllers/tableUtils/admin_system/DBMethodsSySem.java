package com.OOP.plmares.controllers.tableUtils.admin_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class DBMethodsSySem {

    // ----- 1.) Query to get the active sem and sy and use it as the default setup for the application upon opening modules
    public static String getActiveSem() {
        String query = "SELECT semester FROM vwSySemesterActive";
        String strSem = "";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                strSem = resultSet.getString("semester");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            System.err.println("SQL Exception: " + e.getMessage());
        }

        return strSem;
    }

    public static String getActiveSy() {
        String query = "SELECT sy FROM vwSySemesterActive";
        String strSy = "";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                strSy = resultSet.getString("sy");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
            System.err.println("SQL Exception: " + e.getMessage());
        }

        return strSy;
    }


    // ----- 2.) Query to get the active sem and sy and use it as the default setup for the application upon opening modules

    public static void openSySemMethod(String tableName, String columnName, String conditionValue) {
        try (Connection connection = new ConnectDB().Connect()) {
            // Set status to 'I' for rows not matching the condition
            String updateQueryI = String.format("UPDATE %s SET status = 'I' WHERE NOT %s = ?", tableName, columnName);

            // Set status to 'A' for the row matching the condition
            String updateQueryA = String.format("UPDATE %s SET status = 'A' WHERE %s = ?", tableName, columnName);

            try (PreparedStatement preparedStatementI = connection.prepareStatement(updateQueryI);
                 PreparedStatement preparedStatementA = connection.prepareStatement(updateQueryA)) {

                preparedStatementI.setString(1, conditionValue);
                preparedStatementA.setString(1, conditionValue);

                // Execute the updates
                preparedStatementI.executeUpdate();
                preparedStatementA.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                showGenericErrorWarning();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
    }


    // ----- 6.) Query to get school year from sy table
    public static ObservableList<TableModel.SchoolYearModuleInfo> getAllSchoolYear() {

        ObservableList<TableModel.SchoolYearModuleInfo> schoolYears = FXCollections.observableArrayList();

        String query = "SELECT * FROM vwSchoolYearModuleInfo";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String strSy = resultSet.getString("sy");
                String strStatus = resultSet.getString("status");

                // instance of SchoolYearModuleInfo and add to the list
                TableModel.SchoolYearModuleInfo schoolYear = new TableModel.SchoolYearModuleInfo(strSy, strStatus);
                schoolYears.add(schoolYear);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }

        return schoolYears;
    }


    // ----- 7.) Query to get semester from semester table
    public static ObservableList<TableModel.SemesterModuleInfo> getAllSemester() {

        ObservableList<TableModel.SemesterModuleInfo> semesters = FXCollections.observableArrayList();

        String query = "SELECT * FROM vwSemesterModuleInfo";

        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String strSemester = resultSet.getString("semester");
                String strStatus = resultSet.getString("status");

                // instance of SemesterModuleInfo and add to the list
                TableModel.SemesterModuleInfo semester = new TableModel.SemesterModuleInfo(strSemester, strStatus);
                semesters.add(semester);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }

        return semesters;
    }

    // ----- 8.) Query to delete school year (on cascade delete)
    public static void deleteSchoolYear(String conditionValue) {
        try (Connection connection = new ConnectDB().Connect()) {
            DBCommonMethods.deleteRecord(connection, "sy", "sy", conditionValue);
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
    }


    // ----- 8.) Query to edit school year (edit child rows first)
    public static void editSchoolYear(String oldStrSy, String newStrSy) {
        try (Connection connection = new ConnectDB().Connect()) {
            DBCommonMethods.updateValuesWithFilter(connection, "sy", "sy", "sy", oldStrSy, newStrSy);
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
    }


    // ----- 10.) Query to add a school year (would return true if successful)
    public static void addSchoolYear(String strSy) {
        try (Connection connection = new ConnectDB().Connect()) {
            String insertQuery = "INSERT INTO sy (sy, status) VALUES (?, 'I')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, strSy);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
    }


    // ----- 8.) Query to edit semester
    public static void editSemester(String oldStrSemester, String newStrSemester) {
        try (Connection connection = new ConnectDB().Connect()) {
            DBCommonMethods.updateValuesWithFilter(connection, "semester", "semester", "semester", oldStrSemester, newStrSemester);
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
    }


    public static void addSemesterWithStrSy(String strSemester) {  // controller copy of add school year
        try (Connection connection = new ConnectDB().Connect()) {
            String insertQuery = "INSERT INTO semester (semester, status) VALUES (?, 'I')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, strSemester);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
    }


    // ----- 12.) Query to delete semester (on cascade delete)
    public static void deleteSemester(String conditionValue) {
        try (Connection connection = new ConnectDB().Connect()) {
            DBCommonMethods.deleteRecord(connection, "semester", "semester", conditionValue);
        } catch (SQLException e) {
            e.printStackTrace();
            showGenericErrorWarning();
        }
    }


}
