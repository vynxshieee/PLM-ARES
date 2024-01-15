package com.OOP.plmares.controllers.admin_system.ApplicationModuleControllers;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.*;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsApplicationMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEnrollmentMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;
import java.util.Map;

public class SubjectsApplicationController implements DataInitializable {

    @FXML
    private TableView<TableModel.GeneralSubjectInfo> tblVwSubjectApplicationInfo;
    @FXML
    private TableColumn<TableModel.GeneralSubjectInfo, String> colSubjectCode, colDescription,  colSection, colDayTimeModality, colFaculty,  colUnits, colCollege,
                                                                colClassCount, colSlots, colQueue, colRemark, colAction;
    @FXML
    AnchorPane anchorPaneContentContainer;

    @FXML
    private Label lblStudentNo,  lblFullName, lblSyTitle, lblSemesterTitle;
    @FXML
    private ComboBox cmbCollege, cmbSchedule,  cmbRemark;
    @FXML
    private Button btnApplyFilter;
    @FXML
    private TextField txtSubjSearch, txtSecSearch;

    private final CommonUtils c = new CommonUtils();
    private final TableUtils t = new TableUtils();
    private String strSy = "", strSemester = "",
            strStudentNo = "", strCourse = "", strStatus = "", strUnitsEnrolled = "";

    private boolean isInitialized = false;

    @Override
    public void initializeData(Map<String, Object> data) {
        lblStudentNo.setText((String) data.get("studentNo"));

        // store data so it won't be lost going back to the prev controller
        strStudentNo = (String) data.get("studentNo");
        strCourse = (String) data.get("course");
        strStatus = (String) data.get("status");
        strUnitsEnrolled = (String) data.get("unitsEnrolled");
        if (!isInitialized) {
            isInitialized = true;
            initialize();
        }
    }

    @FXML
    private void initialize() {
        if (isInitialized) {
            // Set up active sy and sem
            strSy = DBMethodsSySem.getActiveSy();
            strSemester = DBMethodsSySem.getActiveSem();
            lblSyTitle.setText(strSy);
            lblSemesterTitle.setText(strSemester);

            // setup fullname
            TableModel.StudentGeneralInfo stdInfo = DBMethodsEnrollmentMod.getStudentGeneralInfo(strSy, strStudentNo);
            lblFullName.setText(stdInfo.getFullName().toUpperCase());

            // populate college combobox
            DBCommonMethods.populateComboBox(cmbCollege, "college", "college_code");

            // populate schedule combobox
            ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
                    "--", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
            cmbSchedule.setItems(daysOfWeek);

            // populate remark combobox
            ObservableList<String> remarks = FXCollections.observableArrayList(
                    "--", "No Restrictions");
            cmbRemark.setItems(remarks);


            // Set up columns
            colSubjectCode.setCellValueFactory(cellData -> cellData.getValue().subjectCodeProperty());

            t.setupTextWrapping(colDescription);
            colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

            colSection.setCellValueFactory(cellData -> cellData.getValue().sectionProperty());

            t.setupTextWrappingWithLineBreaks(colDayTimeModality);
            colDayTimeModality.setCellValueFactory(cellData -> cellData.getValue().dayTimeModalityProperty());

            colUnits.setCellValueFactory(cellData -> cellData.getValue().unitsProperty().asObject().asString());

            t.setupTextWrapping(colFaculty);
            colFaculty.setCellValueFactory(cellData -> cellData.getValue().facultyProperty());

            colCollege.setCellValueFactory(cellData -> cellData.getValue().collegeProperty());

            colClassCount.setCellValueFactory(cellData -> cellData.getValue().classCountProperty().asObject().asString());

            colSlots.setCellValueFactory(cellData -> cellData.getValue().slotsProperty().asObject().asString());

            colQueue.setCellValueFactory(cellData -> cellData.getValue().queueProperty().asObject().asString());

            t.setupTextWrapping(colRemark);
            colRemark.setCellValueFactory(cellData -> cellData.getValue().remarkProperty());
            colorCodeRemarkColumn();

            colAction.setCellFactory(param -> createBtnActionCell());

            // Populate the TableView
            tblVwSubjectApplicationInfo.getColumns().forEach(column -> column.setReorderable(false));
            tblVwSubjectApplicationInfo.setFocusModel(null);
            updateTableWithFilter("","","", "", "");
        }
    }


    private TableCell<TableModel.GeneralSubjectInfo, String> createBtnActionCell() {
        return new TableCell<TableModel.GeneralSubjectInfo, String>() {
            private final Button btnAction = new Button("Apply");

            {
                btnAction.setStyle(
                        "-fx-background-radius: 15;" +
                                "-fx-border-radius: 15;" +
                                "-fx-background-color: #48b239;" +
                                "-fx-font-family: \"Raleway SemiBold\";" +
                                "-fx-font-size: 12;" +
                                "-fx-text-fill: white;" +
                                "-fx-cursor: hand;" +
                                "-fx-pref-height: 7"
                );

                btnAction.setOnAction(event -> {
                    TableModel.GeneralSubjectInfo selectedData = getTableView().getItems().get(getIndex());

                    String strSubjectCode = selectedData.getSubjectCode();
                    String strSection = selectedData.getSection();

                    System.out.println("Subject Code: " + strSubjectCode);
                    System.out.println("Section: " + strSection);

                    if(DBMethodsApplicationMod.addApplicationPendingSchedule(strSy, strSemester, strStudentNo, strSubjectCode, strSection)){
                        updateTableWithFilter("","","", "", "");
                    }

                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Access the GeneralSubjectInfo object associated with the current row
                    TableModel.GeneralSubjectInfo selectedData = getTableView().getItems().get(getIndex());

                    // Check if colRemark is equal to "No Restrictions"
                    if ("No Restrictions".equals(selectedData.getRemark())) {
                        // Enable the button
                        btnAction.setDisable(false);
                        setGraphic(btnAction);
                    } else {
                        // Disable the button
                        btnAction.setDisable(true);
                        setGraphic(btnAction);
                    }
                }
            }
        };
    }


    private void colorCodeRemarkColumn() {
        colRemark.setCellFactory(column -> {
            return new TableCell<TableModel.GeneralSubjectInfo, String>() {
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
                        // Apply color coding based on the remark
                        if ("No Restrictions".equals(item)) {
                            text.setFill(Color.valueOf("#0a800a"));  // green
                        } else if ("Limited Availability".equals(item)) {
                            text.setFill(Color.valueOf("#ff9900"));  // yellow
                        } else {
                            text.setFill(Color.valueOf("#d01a1a")); // red
                        }

                        text.setText(item);
                        setGraphic(text);
                    }
                }
            };
        });
    }




    public void handleApplyFilter() {
        String strSubjSearchTerm = txtSubjSearch.getText().toUpperCase();
        String strSecSearchTerm = txtSecSearch.getText().toUpperCase();
        String strSchedule = c.getSelectedItemOrEmptyScheduleCmbBox(cmbSchedule);
        String strCollegeCode = c.getSelectedItemOrEmptyCmbBox(cmbCollege);
        String strRemark = c.getSelectedItemOrEmptyCmbBox(cmbRemark);

        updateTableWithFilter(strSubjSearchTerm, strSecSearchTerm, strSchedule, strCollegeCode, strRemark);
    }


    private void updateTableWithFilter(String strSubjSearchTerm, String strSecSearchTerm, String strSchedule, String strCollegeCode, String strRemark) {
        System.out.println(strSubjSearchTerm + strSecSearchTerm + strSchedule + strCollegeCode + strRemark);
        ObservableList<TableModel.GeneralSubjectInfo> listFilteredResults = DBMethodsApplicationMod.getSubjectApplicationInfo(
                strSy, strSemester, strStudentNo, strSubjSearchTerm,
                strSecSearchTerm, strSchedule, strCollegeCode,
                strRemark);

        System.out.println("fetched results: " + listFilteredResults.size());

        TableUtils.populateTable(tblVwSubjectApplicationInfo, listFilteredResults);
    }

    public void handleClearSubjectSearch(){
        txtSubjSearch.setText("");
    }

    public void handleClearSectionSearch(){
        txtSecSearch.setText("");
    }

    public void handleClearAll(){
        txtSubjSearch.setText("");
        txtSecSearch.setText("");
        cmbCollege.getSelectionModel().clearSelection();
        cmbSchedule.getSelectionModel().clearSelection();
        cmbRemark.getSelectionModel().clearSelection();
        updateTableWithFilter("","","", "", "");
    }

    public void onClickBtnGoBack(){
        Map<String, Object> data = new HashMap<>();
        data.put("studentNo", lblStudentNo.getText());
        data.put("fullName", lblFullName.getText().toUpperCase());
        data.put("course", strCourse);
        data.put("status", strStatus);
        data.put("unitsEnrolled", strUnitsEnrolled);
        c.loadScreen("/FXML/admin_system/ApplicationModule/ApplicationModule.fxml", anchorPaneContentContainer, data);
    }

}

