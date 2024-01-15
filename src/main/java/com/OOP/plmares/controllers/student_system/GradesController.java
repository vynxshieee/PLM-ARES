package com.OOP.plmares.controllers.student_system;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
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

import java.util.Map;

public class GradesController implements DataInitializable  {
    @FXML private TableView<TableModel.StudentRecordSubject> tblVwStudentRecords;
    @FXML private TableColumn<TableModel.StudentRecordSubject, String> colSubjectCode, colDescription, colGrade, colRemark;
    @FXML private TableColumn<TableModel.StudentRecordSubject, Integer> colUnits;
    @FXML private Label lblFullName, lblCourse, lblStudentNo, lblStatus, lblYearLevel, lblUnitsTaken, lblGWA;
    @FXML private ComboBox<String> cmbSchoolYear, cmbSemester;
    @FXML private AnchorPane anchorPaneFilter, btnPrintPreview;
    private final TableUtils t = new TableUtils();
    private final CommonUtils c = new CommonUtils();
    private String strStudentNo = "", strSy = "", strSemester = "";
    private boolean isInitialized = false;

    @Override
    public void initializeData(Map<String, Object> data) {
        strStudentNo = (String) data.get("studentNo");
        if (!isInitialized) {
            isInitialized = true;
            initialize();
        }
    }

    @FXML
    private void initialize(){
        if (isInitialized) {
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

            // set values for student details and current sy and sem
            TableModel.StudentGeneralInfo stdInfo = DBMethodsEnrollmentMod.getStudentGeneralInfo(strSy, strStudentNo);

            lblFullName.setText(stdInfo.getFullName().toUpperCase());
            lblCourse.setText(stdInfo.getCourseDescription());
            lblStudentNo.setText(stdInfo.getStudentNo());
            lblStatus.setText(stdInfo.getStatus());
            lblYearLevel.setText(stdInfo.getYearLevel());

            cmbSchoolYear.setValue(strSy);
            cmbSemester.setValue(strSemester);
            updateTableWithFilter(strSy, strSemester, strStudentNo);
        }
    }

    private void updateTableWithFilter(String strSy, String strSemester, String strStudentNo) {
        ObservableList<TableModel.StudentRecordSubject> listFilteredResults = DBMethodsStudentRecordsMod.getStudentRecords(
                strSy, strSemester, strStudentNo);

        TableUtils.populateTable(tblVwStudentRecords, listFilteredResults);

        // set units taken and gwa
        Pair<String, String> result = DBMethodsStudentRecordsMod.getGwaAndUnitsTaken(strSy, strSemester, strStudentNo);
        lblGWA.setText(result.getKey());   // set lbl to GWA
        lblUnitsTaken.setText(result.getValue());   // set lbl to units taken
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

    public void handleApplyFilter() {
        String strFilterSy = cmbSchoolYear.getValue();
        String strFilterSemester = c.getSelectedItemOrEmptyCmbBox(cmbSemester);
        updateTableWithFilter(strFilterSy, strFilterSemester, strStudentNo);
    }
}
