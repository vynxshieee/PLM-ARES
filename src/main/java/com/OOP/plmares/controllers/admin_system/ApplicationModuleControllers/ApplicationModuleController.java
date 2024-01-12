package com.OOP.plmares.controllers.admin_system.ApplicationModuleControllers;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsApplicationMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;


public class ApplicationModuleController implements DataInitializable {

    CommonUtils c = new CommonUtils();


    @FXML
    AnchorPane anchorPaneContentContainer;

    // Table View columns initialization
    @FXML private TableView<TableModel.IrregPotentialApplicant> tblVwIrregPotentialApplicants;
    @FXML private TableColumn<TableModel.IrregPotentialApplicant, String> colStudentNo, colFullName, colCourse, colYear, colStatus, colUnitsEnrolled;

    // other components
    @FXML private TextField txtSearchStudent;
    @FXML private Button btnApply;
    @FXML private Label btnClear, lblStudentNo, lblFullName, lblCourse, lblStatus, lblUnitsEnrolled, lblSyTitle, lblSemesterTitle;


    private String strSy = "", strSemester = "";
    private Boolean isTableFocusOnInitialized = true;

    // 1.) Load necessary data for normal and default functioning
    @Override
    public void initializeData(Map<String, Object> data) {
        lblStudentNo.setText((String) data.get("studentNo"));
        lblFullName.setText((String) data.get("fullName"));
        lblCourse.setText((String) data.get("course"));
        lblStatus.setText((String) data.get("status"));
        lblUnitsEnrolled.setText((String) data.get("unitsEnrolled"));
        btnApply.setDisable(false);
    }

    // 2.) Load the table upon initialization
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
        colUnitsEnrolled.setCellValueFactory(cellData -> cellData.getValue().intUnitsEnrolledProperty().asString());

        // Fetch data from the database and populate the table
        ObservableList<TableModel.IrregPotentialApplicant> irregApplicants =
                DBMethodsApplicationMod.getAllIrregPotentialApplicants(strSy, strSemester, "");

        // Populate the TableView
        tblVwIrregPotentialApplicants.getColumns().forEach(column -> column.setReorderable(false));
        tblVwIrregPotentialApplicants.setItems(irregApplicants);

        // Attach the table row click listener
        TableUtils.setTableClickListener(tblVwIrregPotentialApplicants, this::updateLabels);
    }


    // 3.) Filter name/student number on search bar
    @FXML
    private void handleSearchType() {
        String strSearchTerm = txtSearchStudent.getText();
        updateTableWithSearchResults(strSearchTerm);
    }


    // 4.) Clear name/student number on search bar, would refresh the table
    @FXML
    private void handleClearButton() {
        updateTableWithSearchResults("");
        txtSearchStudent.setText("");
    }



    // 5.) Update table according to filter
    private void updateTableWithSearchResults(String strSearchTerm) {
        // Call the method to get search results based on the search term
        ObservableList<TableModel.IrregPotentialApplicant> listSearchResults = DBMethodsApplicationMod.getAllIrregPotentialApplicants(strSy, strSemester, strSearchTerm);

        // Update the TableView with the new search results
        TableUtils.populateTable(tblVwIrregPotentialApplicants, listSearchResults);
    }



    // 6.) Update labels according to mouse click (row click listener is in initializer)
    private void updateLabels(TableModel.IrregPotentialApplicant studentInfo) {
        lblStudentNo.setText(studentInfo.getStrStudentNo());
        lblFullName.setText(studentInfo.getStrFullName().toUpperCase());
        lblCourse.setText(studentInfo.getStrCourse().toUpperCase());
        lblStatus.setText(studentInfo.getStrStatus().toUpperCase());
        lblUnitsEnrolled.setText(studentInfo.getIntUnitsEnrolled()+ "");
        btnApply.setDisable(false);
    }

    // 7.) Switch scenes to the Subject Application Module
    public void onClickBtnApply(){
        Map<String, Object> data = new HashMap<>();
        data.put("studentNo", lblStudentNo.getText());
        data.put("fullName", lblFullName.getText().toUpperCase());
        data.put("course", lblCourse.getText());
        data.put("status", lblStatus.getText().toUpperCase());
        data.put("unitsEnrolled", lblUnitsEnrolled.getText());

        c.loadScreen("/FXML/admin_system/ApplicationModule/SubjectsApplication.fxml", anchorPaneContentContainer, data);
    }

}
