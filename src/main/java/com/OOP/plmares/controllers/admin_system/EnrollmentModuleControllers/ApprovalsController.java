package com.OOP.plmares.controllers.admin_system.EnrollmentModuleControllers;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEnrollmentMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;

public class ApprovalsController implements DataInitializable {
    @FXML private AnchorPane anchorPaneContentContainer, btnReviewApplication;
    @FXML private TableView<TableModel.IrregApprovals> tblVwApprovalsConsole;
    @FXML private TableColumn<TableModel.IrregApprovals, String> colStudentNo, colFullName, colCourse, colYear, colSubjects;
    @FXML private TextField txtStudentSearch;
    @FXML private Label lblFullName, lblSyTitle, lblSemesterTitle, lblStudentNo, lblApplicantCount;

    private String strSy = "", strSemester = "";
    private CommonUtils c = new CommonUtils();

    @Override
    public void initializeData(Map<String, Object> data) {
        lblStudentNo.setText((String) data.get("studentNo"));
        lblFullName.setText((String) data.get("fullName"));

        if(!lblStudentNo.equals("< >")){
            btnReviewApplication.setDisable(false);
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
        colSubjects.setCellValueFactory(cellData -> cellData.getValue().intSubjectCountProperty().asString());

        // populate table + set enrollee count label
        tblVwApprovalsConsole.getColumns().forEach(column -> column.setReorderable(false));
        lblApplicantCount.setText(updateTableWithSearchResults("") + "");

        // Attach the table row click listener
        TableUtils.setTableClickListener(tblVwApprovalsConsole, this::updateLabels);
    }

    private void updateLabels(TableModel.IrregApprovals studentInfo) {
        lblStudentNo.setText(studentInfo.getStrStudentNo());
        lblFullName.setText(studentInfo.getStrFullName().toUpperCase());
        btnReviewApplication.setDisable(false);
    }

    private int updateTableWithSearchResults(String strSearchTerm) {
        // Call the method to get search results based on the search term
        ObservableList<TableModel.IrregApprovals> irregApprovalsList =
                DBMethodsEnrollmentMod.getIrregApprovals(strSy, strSemester, strSearchTerm);

        // Update the TableView with the new search results
        TableUtils.populateTable(tblVwApprovalsConsole, irregApprovalsList);

        return irregApprovalsList.size();
    }

    public void handleSearch(){
        String strSearchTerm = txtStudentSearch.getText();
        int intApplicantCount = updateTableWithSearchResults(strSearchTerm);
        lblApplicantCount.setText(intApplicantCount + "");
        if(intApplicantCount != 0){
            clearAllFields();
        }
    }

    public void handleClearButton(){
        txtStudentSearch.setText("");
        clearAllFields();
        lblApplicantCount.setText(updateTableWithSearchResults("") + "");
    }

    private void clearAllFields() {
        lblFullName.setText("< >");
        lblStudentNo.setText("< >");
        btnReviewApplication.setDisable(true);
    }


    public void onClickBtnReviewApplication(){
        Map<String, Object> data = new HashMap<>();
        data.put("studentNo", lblStudentNo.getText());
        data.put("fullName", lblFullName.getText().toUpperCase());
        c.loadScreen("/FXML/admin_system/EnrollmentModule/SubjectsApproval.fxml", anchorPaneContentContainer, data);
    }

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/EnrollmentModule/EnrollmentModule.fxml", anchorPaneContentContainer);
    }
}
