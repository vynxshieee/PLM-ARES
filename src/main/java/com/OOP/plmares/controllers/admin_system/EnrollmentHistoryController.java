package com.OOP.plmares.controllers.admin_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEnrollmentHistMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class EnrollmentHistoryController {
    @FXML private TableView<TableModel.StudentEnrollmentHistory> tblVwEnrollmentHistory;
    @FXML private TableColumn<TableModel.StudentEnrollmentHistory, String> colStudentNo, colFullName, colCourse, colYear, colStatus, colSchoolYear, colSemester, colActive;
    @FXML private ComboBox<String> cmbSchoolYear, cmbSemester, cmbCollege, cmbCourse;
    @FXML private Label lblEnrolleeCount;
    @FXML private TextField txtSearchStudent;
    @FXML private AnchorPane anchorPaneTableHolder;

    private String strSy = "", strSemester = "";
    private final CommonUtils c = new CommonUtils();


    @FXML
    private void initialize() {
        // Set up active sy and sem
        strSy = DBMethodsSySem.getActiveSy();
        strSemester = DBMethodsSySem.getActiveSem();
        cmbSchoolYear.setValue(strSy);
        cmbSemester.setValue(strSemester);

        // Populate school year and semester comboboxes
        DBCommonMethods.populateComboBox(cmbSchoolYear, "sy", "sy");
        DBCommonMethods.populateComboBox(cmbSemester, "semester", "semester");

        // Populate college, course, and status comboboxes
        DBCommonMethods.populateComboBox(cmbCollege, "college", "college_code");
        DBCommonMethods.populateComboBox(cmbCourse, "course", "course_code");


        // Set up columns for Enrollment History table
        colStudentNo.setCellValueFactory(cellData -> cellData.getValue().strStudentNoProperty());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().strFullNameProperty());
        colCourse.setCellValueFactory(cellData -> cellData.getValue().strCourseProperty());
        colYear.setCellValueFactory(cellData -> cellData.getValue().strYearProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());
        colSchoolYear.setCellValueFactory(cellData -> cellData.getValue().strSyProperty());
        colSemester.setCellValueFactory(cellData -> cellData.getValue().strSemesterProperty());
        colActive.setCellValueFactory(cellData -> cellData.getValue().strActiveProperty());

        // Populate the TableView for Enrollment History
        tblVwEnrollmentHistory.getColumns().forEach(column -> column.setReorderable(false));
        tblVwEnrollmentHistory.setFocusModel(null);
        updateTableWithFilter(strSy, strSemester, "", "", "");
    }

    private void updateTableWithFilter(String strSy, String strSemester, String strCollegeCode,
                                       String strCourseCode, String strStudentSearch) {

        ObservableList<TableModel.StudentEnrollmentHistory> listFilteredResults = DBMethodsEnrollmentHistMod.getEnrolleesMasterlist(
                strSy, strSemester, strCollegeCode, strCourseCode, strStudentSearch);

        System.out.println("fetched results: " + listFilteredResults.size());

        lblEnrolleeCount.setText(listFilteredResults.size() + "");
        TableUtils.populateTable(tblVwEnrollmentHistory, listFilteredResults);
    }

    public void handleApplyFilter() {
        String strSy = c.getSelectedItemOrEmptyCmbBox(cmbSchoolYear);
        String strSemester = c.getSelectedItemOrEmptyCmbBox(cmbSemester);
        String strCollegeCode = c.getSelectedItemOrEmptyCmbBox(cmbCollege);
        String strCourseCode = c.getSelectedItemOrEmptyCmbBox(cmbCourse);

        String strStudentSearch = txtSearchStudent.getText();

        // Apply the values to the updateTableWithFilter method
        updateTableWithFilter(strSy, strSemester, strCollegeCode, strCourseCode, strStudentSearch);
    }

    public void handleClearAll(){
        txtSearchStudent.setText("");
        cmbSchoolYear.setValue(strSy);  // revert back to default sy and sem
        cmbSemester.setValue(strSemester);
        cmbCollege.getSelectionModel().clearSelection();
        cmbCourse.getSelectionModel().clearSelection();
        updateTableWithFilter(strSy, strSemester,"", "", "");
    }


}
