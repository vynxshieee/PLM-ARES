package com.OOP.plmares.controllers.admin_system;

import com.OOP.plmares.controllers.tableUtils.*;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEnrollmentMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsStudentRecordsMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.Pair;

import javax.swing.*;

public class StudentRecordsController {

    @FXML private TableView<TableModel.StudentRecordSubject> tblVwStudentRecords;
    @FXML private TableColumn<TableModel.StudentRecordSubject, String> colSubjectCode, colDescription, colGrade, colRemark;
    @FXML private TableColumn<TableModel.StudentRecordSubject, Integer> colUnits;
    @FXML private Label lblFullName, lblCourse, lblStudentNo, lblStatus, lblYearLevel, lblUnitsTaken, lblGWA;
    @FXML private TextField txtStudentSearch;
    @FXML private ComboBox<String> cmbSchoolYear, cmbSemester;
    @FXML private AnchorPane anchorPaneFilter, btnPrintPreview;

    private TableUtils t = new TableUtils();
    private final CommonUtils c = new CommonUtils();
    private String strStudentNo = "", strSy = "", strSemester = "";

    @FXML
    private void initialize(){
        // set active sy and sem
        strSy = DBMethodsSySem.getActiveSy();
        strSemester = DBMethodsSySem.getActiveSem();

        // Populate school year and semester comboboxes
        DBCommonMethods.populateComboBox(cmbSchoolYear, "sy", "sy");
        DBCommonMethods.populateComboBox(cmbSemester, "semester", "semester");

        // Set up columns
        colSubjectCode.setCellValueFactory(cellData -> cellData.getValue().subjectCodeProperty());

        t.setupTextWrapping(colDescription);
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        colUnits.setCellValueFactory(cellData -> cellData.getValue().unitsProperty().asObject());

        colGrade.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());

        colRemark.setCellValueFactory(cellData -> cellData.getValue().remarkProperty());

        colorCodeGradeRemarkColumns();

        // Populate the TableView
        tblVwStudentRecords.getColumns().forEach(column -> column.setReorderable(false));
        tblVwStudentRecords.setFocusModel(null);
    }

    private int updateTableWithFilter(String strSy, String strSemester, String strStudentNo) {

        ObservableList<TableModel.StudentRecordSubject> listFilteredResults = DBMethodsStudentRecordsMod.getStudentRecords(
                strSy, strSemester, strStudentNo);

        TableUtils.populateTable(tblVwStudentRecords, listFilteredResults);

        // set units taken and gwa
        Pair<String, String> result = DBMethodsStudentRecordsMod.getGwaAndUnitsTaken(strSy, strSemester, strStudentNo);
        lblGWA.setText(result.getKey());   // set lbl to GWA
        lblUnitsTaken.setText(result.getValue());   // set lbl to units taken
        return listFilteredResults.size();
    }

    private void colorCodeGradeRemarkColumns() {
        colRemark.setCellFactory(createColorCodingCellFactory());
        colGrade.setCellFactory(createColorCodingCellFactory());
    }

    private Callback<TableColumn<TableModel.StudentRecordSubject, String>, TableCell<TableModel.StudentRecordSubject, String>> createColorCodingCellFactory() {
        return column -> {
            return new TableCell<TableModel.StudentRecordSubject, String>() {
                private final Text text = new Text();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(text);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(column.widthProperty());
                    text.setTextAlignment(TextAlignment.CENTER);
                    text.setLineSpacing(1);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText(null);
                        setGraphic(null);
                    } else {
                        // Apply color coding based on the column
                        if ("Failed".equals(item) || "5.00".equals(item)) {
                            text.setFill(Color.valueOf("#d01a1a"));  // red
                        } else if ("INC".equals(item) || "no grade".equals(item)) {
                            text.setFill(Color.valueOf("#4266f5"));  // blue
                        } else {
                            text.setFill(Color.valueOf("#0a800a"));  // green for passed
                        }

                        // Capitalize the text
                        text.setText(item.toUpperCase());
                        setGraphic(text);
                    }
                }

            };
        };
    }


    public void handleBtnSearch() {
        strStudentNo = txtStudentSearch.getText();

        TableModel.StudentGeneralInfo stdInfo = DBMethodsEnrollmentMod.getStudentGeneralInfo(strSy, strStudentNo);
        if (stdInfo != null){ // if result search is not empty
            lblFullName.setText(stdInfo.getFullName().toUpperCase());
            lblCourse.setText(stdInfo.getCourseDescription());
            lblStudentNo.setText(stdInfo.getStudentNo());
            lblStatus.setText(stdInfo.getStatus());
            lblYearLevel.setText(stdInfo.getYearLevel());

            cmbSchoolYear.setValue(strSy);
            cmbSemester.setValue(strSemester);
            updateTableWithFilter(strSy, strSemester, strStudentNo);  // load default records for current sy and sem

            anchorPaneFilter.setDisable(false);
            tblVwStudentRecords.setDisable(false);
        } else {
            JOptionPane.showMessageDialog(null, "There were no records found for \"" + strStudentNo +
                    "\"\nPlease ensure you've typed the correct student number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleApplyFilter() {
        String strFilterSy = cmbSchoolYear.getValue();
        String strFilterSemester = c.getSelectedItemOrEmptyCmbBox(cmbSemester);
        if(updateTableWithFilter(strFilterSy, strFilterSemester, strStudentNo) == 0){
            JOptionPane.showMessageDialog(
                    null,
                    "No results found.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );

        }
    }


}
