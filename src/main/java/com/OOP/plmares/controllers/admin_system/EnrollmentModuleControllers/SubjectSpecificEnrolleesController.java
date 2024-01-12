package com.OOP.plmares.controllers.admin_system.EnrollmentModuleControllers;

import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEnrollmentMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import javax.swing.*;

public class SubjectSpecificEnrolleesController {

    @FXML private AnchorPane anchorPaneContentContainer, btnSearch;
    @FXML private TableView<TableModel.StudentEnrollees> tblVwSubjectSpecificEnrollees;
    @FXML private TableColumn<TableModel.StudentEnrollees, String> colStudentNo, colFullName, colCourse, colYear, colStatus;
    @FXML private TextField txtSubjectCode, txtSection;
    @FXML private Button btnUnenroll;
    @FXML private Label lblFullName, lblSyTitle, lblSemesterTitle, lblStudentNo, lblSubjectTitle, lblFaculty, lblEnrolleeCount;

    private String strSy = "", strSemester = "";

    private CommonUtils c = new CommonUtils();

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/EnrollmentModule/EnrollmentModule.fxml", anchorPaneContentContainer);
    }


    // Load the table upon initialization
    @FXML
    private void initialize() {
        // Set up active sy and sem
        strSy = DBMethodsSySem.getActiveSy();
        strSemester = DBMethodsSySem.getActiveSem();
        lblSyTitle.setText(strSy);
        lblSemesterTitle.setText(strSemester);

        // Set up columns
        colStudentNo.setCellValueFactory(cellData -> cellData.getValue().strStudentNoProperty());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().strFullNameProperty());
        colCourse.setCellValueFactory(cellData -> cellData.getValue().strCourseProperty());
        colYear.setCellValueFactory(cellData -> cellData.getValue().strYearProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());

        // Attach the table row click listener
        tblVwSubjectSpecificEnrollees.getColumns().forEach(column -> column.setReorderable(false));
        TableUtils.setTableClickListener(tblVwSubjectSpecificEnrollees, this::updateLabels);
    }

    private void updateLabels(TableModel.StudentEnrollees studentInfo) {
        lblStudentNo.setText(studentInfo.getStrStudentNo());
        lblFullName.setText(studentInfo.getStrFullName().toUpperCase());

        btnUnenroll.setDisable(false);
    }

    public void handleBtnSearch(){
        int intEnrolleeCount = updateTableWithSearchResults(txtSubjectCode.getText(), txtSection.getText());
        if(intEnrolleeCount != 0){
            Pair<String, String> descriptionAndFaculty = DBMethodsEnrollmentMod.getDescriptionAndFaculty(strSy, strSemester, txtSubjectCode.getText().toUpperCase(), txtSection.getText().toUpperCase());

            String description = descriptionAndFaculty.getKey();
            String faculty = descriptionAndFaculty.getValue();
            lblFaculty.setText(faculty.toUpperCase());
            lblSubjectTitle.setText(description.toUpperCase());
            lblEnrolleeCount.setText(intEnrolleeCount + "");

            // disable textfields and button
            txtSubjectCode.setDisable(true);
            txtSection.setDisable(true);
            btnSearch.setDisable(true);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No matching records found!",
                    "No Matching Records",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void handleBtnCancel(){
        updateTableWithSearchResults("","");
        clearAllFields();
    }

    private void clearAllFields() {
        txtSection.setText("");
        txtSubjectCode.setText("");
        lblSubjectTitle.setText("< >");
        lblFaculty.setText("< >");
        lblFullName.setText("< >");
        lblStudentNo.setText("< >");
        lblEnrolleeCount.setText("< >");
        btnUnenroll.setDisable(true);

        // enable buttons after search
        txtSubjectCode.setDisable(false);
        txtSection.setDisable(false);
        btnSearch.setDisable(false);
    }

    private int updateTableWithSearchResults(String strSubjectCode, String strSection) {
        // Call the method to get search results based on the search term
        ObservableList<TableModel.StudentEnrollees> subjectSpecificEnrollees =
                DBMethodsEnrollmentMod.getSubjectSpecificEnrollees(strSy, strSemester,  strSubjectCode, strSection);

        // Update the TableView with the new search results
        TableUtils.populateTable(tblVwSubjectSpecificEnrollees, subjectSpecificEnrollees);

        return subjectSpecificEnrollees.size();
    }

    public void handleUnenrollStudent() {
        String studentName = lblFullName.getText();
        String subjectCode = txtSubjectCode.getText();

        // Display a confirmation dialog
        int option = JOptionPane.showConfirmDialog(null,
                "Do you want to unenroll: " + studentName + " on Subject Code: " + subjectCode,
                "Unenroll Confirmation", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            DBMethodsEnrollmentMod.deleteSubjectScheduleRecord(strSy, strSemester, subjectCode, txtSection.getText(), lblStudentNo.getText());
            updateTableWithSearchResults(subjectCode, txtSection.getText());
        }
    }
}
