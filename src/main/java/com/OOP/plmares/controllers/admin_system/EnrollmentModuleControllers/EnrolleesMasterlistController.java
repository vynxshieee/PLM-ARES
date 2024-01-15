package com.OOP.plmares.controllers.admin_system.EnrollmentModuleControllers;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEnrollmentMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;

public class EnrolleesMasterlistController implements DataInitializable {
    @FXML private AnchorPane anchorPaneContentContainer, btnViewSchedule, btnUnenroll;
    @FXML private TableView<TableModel.StudentEnrollees> tblVwEnrolleesMasterlist;
    @FXML private TableColumn<TableModel.StudentEnrollees, String> colStudentNo, colFullName, colCourse, colYear, colStatus;
    @FXML private TextField txtStudentSearch;
    @FXML private Label lblFullName, lblSyTitle, lblSemesterTitle, lblStudentNo, lblEnrolleeCount;
    private String strSy = "", strSemester = "";
    private final CommonUtils c = new CommonUtils();
    @Override
    public void initializeData(Map<String, Object> data) {
        lblStudentNo.setText((String) data.get("studentNo"));
        lblFullName.setText((String) data.get("fullName"));

        // Enable button on go back since fields have retained values
        if(!lblStudentNo.equals("< >")){
            btnViewSchedule.setDisable(false);
            btnUnenroll.setDisable(false);
        }
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

        // populate table + set enrollee count label
        tblVwEnrolleesMasterlist.getColumns().forEach(column -> column.setReorderable(false));
        lblEnrolleeCount.setText(updateTableWithSearchResults("") + "");

        // Attach the table row click listener
        TableUtils.setTableClickListener(tblVwEnrolleesMasterlist, this::updateLabels);
    }


    private void updateLabels(TableModel.StudentEnrollees studentInfo) {
        lblStudentNo.setText(studentInfo.getStrStudentNo());
        lblFullName.setText(studentInfo.getStrFullName().toUpperCase());
        btnViewSchedule.setDisable(false);
        btnUnenroll.setDisable(false);
    }

    private int updateTableWithSearchResults(String strSearchTerm) {
        // Call the method to get search results based on the search term
        ObservableList<TableModel.StudentEnrollees> enrolleesMasterlist =
                DBMethodsEnrollmentMod.getEnrolleesMasterlist(strSy, strSemester, strSearchTerm);

        // Update the TableView with the new search results
        TableUtils.populateTable(tblVwEnrolleesMasterlist, enrolleesMasterlist);

        return enrolleesMasterlist.size();
    }

    public void handleSearch(){
        String strSearchTerm = txtStudentSearch.getText();
        int intEnrolleeCount = updateTableWithSearchResults(strSearchTerm);
        lblEnrolleeCount.setText(intEnrolleeCount + "");
    }

    public void handleClearButton(){
        txtStudentSearch.setText("");
        clearAllFields();
        lblEnrolleeCount.setText(updateTableWithSearchResults("") + "");
    }

    private void clearAllFields() {
        lblFullName.setText("< >");
        lblStudentNo.setText("< >");
        txtStudentSearch.setText("");
        btnUnenroll.setDisable(true);
        btnViewSchedule.setDisable(true);
    }


    public void handleUnenrollStudent() {
        String studentName = lblFullName.getText();

        // Display a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Unenroll Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Do you want to unenroll: " + studentName + " for S.Y. " + strSy + " Semester " + strSemester + "?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            DBMethodsEnrollmentMod.deleteEnrolleeSySem(strSy, strSemester, lblStudentNo.getText());
            lblEnrolleeCount.setText(updateTableWithSearchResults("") + "");
            clearAllFields();
        }
    }


    public void onClickBtnViewStudentSchedule(){
        Map<String, Object> data = new HashMap<>();
        data.put("studentNo", lblStudentNo.getText());
        data.put("fullName", lblFullName.getText().toUpperCase());
        data.put("prevModule", "ENROLLEES");
        c.loadScreen("/FXML/admin_system/EnrollmentModule/ViewStudentSchedule.fxml", anchorPaneContentContainer, data);
    }

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/EnrollmentModule/EnrollmentModule.fxml", anchorPaneContentContainer);
    }

}
