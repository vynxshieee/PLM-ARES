package com.OOP.plmares.controllers.admin_system.SubjectModuleControllers;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSubjectSchedulingMod;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showSuccessDialog;

public class ScheduleModuleController {
    @FXML private TableView<TableModel.ScheduleModule> tblVwScheduleInfo;
    @FXML private TableColumn<TableModel.ScheduleModule, String> colSubjectCode, colDescription, colSemester, colCollege, colSection, colDay,
                                                            colTime, colRoom, colModality, colSequence, colFaculty;
    @FXML
    private TableView<TableModel.SubjectOptions> tblVwSubjectInfo;
    @FXML
    private TableColumn<TableModel.SubjectOptions, String> colSubjectCodeDetails, colDescriptionDetails;
    @FXML
    private TableView<TableModel.FacultyOptions> tblVwFacultyInfo;
    @FXML
    private TableColumn<TableModel.FacultyOptions, String> colFacultyIDDetails, colFacultyNameDetails;
    @FXML
    private ScrollPane scrollPaneAddMode;
    @FXML
    private TextField txtSubjectSearch, txtEmployeeSearch, txtSecSearch, txtSubjectCode, txtDescription,
                    txtCollegeCode, txtSemester, txtSequence, txtSection, txtRoom,
                    txtFacultyID,  txtFacultyName,  txtStartHour, txtStartMin, txtEndHour, txtEndMin, txtSubjectSearchDetails, txtEmployeeSearchDetails,

                    // adding elements
                    txtSubjectCodeAdd, txtDescriptionAdd, txtSectionAdd, txtFacultyIDAdd, txtFacultyNameAdd,
                        txtRoomSeq1, txtRoomSeq2, txtRoomSeq3,
                        txtStartHourSeq1, txtStartMinSeq1, txtEndHourSeq1, txtEndMinSeq1,
                        txtStartHourSeq2, txtStartMinSeq2, txtEndHourSeq2, txtEndMinSeq2,
                        txtStartHourSeq3, txtStartMinSeq3, txtEndHourSeq3, txtEndMinSeq3;
    @FXML
    private ComboBox<String> cmbScheduleFilter, cmbCollegeFilter, cmbCollege, cmbDay, cmbModality, cmbSemester,
                            cmbDaySeq1, cmbDaySeq2, cmbDaySeq3,
                             cmbModalitySeq1, cmbModalitySeq2, cmbModalitySeq3;
    @FXML
    private CheckBox chkSeq1, chkSeq2, chkSeq3;
    @FXML
    private AnchorPane
            // anchor panes designed as buttons
            btnFilter, btnRevertDetails, btnDelete, btnEdit, btnAdd, btnCancel, btnSubjectSearchDetails,
            btnFacultySearchDetails, btnDeleteCommit, btnEditCommit,
            // main containers
            anchorPaneSchedDetails, anchorPaneContentContainer,
            // add mode containers
            anchorPaneSeq1, anchorPaneSeq2, anchorPaneSeq3;
    @FXML
    private Label lblSubjectTitle, btnClearSubjectDetails, btnClearFacultyDetails, btnChangeFacultyAssign,
                 errLblRoom, errLblTime, lblChooseSubject, lblChooseFaculty,
                errLblSubject, errLblCollegeCode, errLblSemester, errLblSectionAdd, errLblFaculty,
                errLblDaySeq1, errLblModalitySeq1, errLblRoomSeq1, errLblTimeSeq1,
                errLblDaySeq2, errLblModalitySeq2, errLblRoomSeq2, errLblTimeSeq2,
                errLblDaySeq3, errLblModalitySeq3, errLblRoomSeq3, errLblTimeSeq3;
    private final CommonUtils c = new CommonUtils();
    private final TableUtils t = new TableUtils();
    private final InputValidationUtils iv = new InputValidationUtils();

    private String strSubjectCode = "", strDescription = "", strCollegeDetails = "", strSemester = "", strSequence = "",
            strSection = "", strDay = "", strModality = "", strRoom = "", strFacultyID = "", strFacultyName = "", strStartHour = "",
            strStartMin = "", strEndHour = "", strEndMin = "", strMode = "";

    private ObservableList<TableModel.ScheduleModule> mainTableFilteredList;
    private ObservableList<TableModel.SubjectOptions> subjectPickerList;
    private  ObservableList<TableModel.FacultyOptions> facultyPickerList;


    @FXML
    private void initialize() {

        // populate schedule combobox
        ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
                "--", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        cmbScheduleFilter.setItems(daysOfWeek);

        cmbDay.setItems(daysOfWeek);
        cmbDay.getItems().remove("--");

        cmbDaySeq1.setItems(daysOfWeek);
        cmbDaySeq1.getItems().remove("--");

        cmbDaySeq2.setItems(daysOfWeek);
        cmbDaySeq2.getItems().remove("--");

        cmbDaySeq3.setItems(daysOfWeek);
        cmbDaySeq3.getItems().remove("--");

        // populate semester combobox for ADD MODE
        DBCommonMethods.populateComboBox(cmbSemester, "semester", "semester");
        cmbSemester.getItems().remove("--");

        // populate college combobox for ADD MODE
        DBCommonMethods.populateComboBox(cmbCollegeFilter, "college", "college_code");
        DBCommonMethods.populateComboBox(cmbCollege, "college", "college_code");
        cmbCollege.getItems().remove("--");

        // populate sequence combobox
        ObservableList<String> modalities = FXCollections.observableArrayList(
                "OL", "F2F");
        cmbModality.setItems(modalities);
        cmbModalitySeq1.setItems(modalities);
        cmbModalitySeq2.setItems(modalities);
        cmbModalitySeq3.setItems(modalities);

        // Set up columns for main schedule table
        colSubjectCode.setCellValueFactory(cellData -> cellData.getValue().subjectCodeProperty());
        t.setupTextWrapping(colDescription);
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colSemester.setCellValueFactory(cellData -> cellData.getValue().strSemesterProperty());
        colCollege.setCellValueFactory(cellData -> cellData.getValue().collegeProperty());
        colSection.setCellValueFactory(cellData -> cellData.getValue().sectionProperty());
        colDay.setCellValueFactory(cellData -> cellData.getValue().strDayProperty());
        colTime.setCellValueFactory(cellData -> cellData.getValue().strTimeProperty());
        colRoom.setCellValueFactory(cellData -> cellData.getValue().strRoomProperty());
        colModality.setCellValueFactory(cellData -> cellData.getValue().strTypeProperty());
        colSequence.setCellValueFactory(cellData -> cellData.getValue().strSequenceNumberProperty());
        t.setupTextWrapping(colFaculty);
        colFaculty.setCellValueFactory(cellData -> cellData.getValue().facultyProperty());

        // Set up columns for subject picker table
        colSubjectCodeDetails.setCellValueFactory(cellData -> cellData.getValue().subjectCodeProperty());
        t.setupTextWrapping(colDescriptionDetails);
        colDescriptionDetails.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        // Set up columns for subject picker table
        colFacultyIDDetails.setCellValueFactory(cellData -> cellData.getValue().strEmployeeIDProperty());
        t.setupTextWrapping(colFacultyNameDetails);
        colFacultyNameDetails.setCellValueFactory(cellData -> cellData.getValue().strFullNameProperty());

        // Populate all Tables
        updateMainTableWithFilter("","","", "", "");

        // Attach the table row click listener for all Tables
        tblVwScheduleInfo.getColumns().forEach(column -> column.setReorderable(false));
        tblVwSubjectInfo.getColumns().forEach(column -> column.setReorderable(false));
        tblVwFacultyInfo.getColumns().forEach(column -> column.setReorderable(false));

        TableUtils.setTableClickListener(tblVwScheduleInfo, this::updateLabelsMainTable);
        // set edit delete revert btn visibility to false
        btnDeleteCommit.setVisible(false);
        btnEditCommit.setVisible(false);
        btnRevertDetails.setVisible(false);
        scrollPaneAddMode.setVisible(false);
        scrollPaneAddMode.setFocusTraversable(false);
    }

    /* Table methods */

    private void updateLabelsMainTable(TableModel.ScheduleModule scheduleInfo) {
        if(!strMode.isEmpty()){
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Disabled Selection");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("You are still in " + strMode + " mode. Schedule selection is disabled.");

            warningAlert.showAndWait();
        } else {
            strSubjectCode = scheduleInfo.getSubjectCode();
            strDescription = scheduleInfo.getDescription();
            strCollegeDetails = scheduleInfo.getCollege();
            strSemester = scheduleInfo.getStrSemester();
            strSequence = scheduleInfo.getStrSequenceNumber();
            strSection = scheduleInfo.getSection();
            strDay = c.getDayFromAbbreviation(scheduleInfo.getStrDay());
            strModality = scheduleInfo.getStrType();
            strRoom = scheduleInfo.getStrRoom();
            strFacultyID = c.extractEmployeeInfo(scheduleInfo.getFaculty()).getKey();  // get id from employee info extractor
            strFacultyName = c.extractEmployeeInfo(scheduleInfo.getFaculty()).getValue(); // get id from employee info extractor
            strStartHour = c.extractTimeParts(scheduleInfo.getStrTime())[0]; // get start hour
            strStartMin = c.extractTimeParts(scheduleInfo.getStrTime())[1]; // get start minute
            strEndHour = c.extractTimeParts(scheduleInfo.getStrTime())[2]; // get end hour
            strEndMin = c.extractTimeParts(scheduleInfo.getStrTime())[3]; // get end minute

            lblSubjectTitle.setText("(" + strSubjectCode + ") " + strDescription);
            txtSubjectCode.setText(strSubjectCode);
            txtDescription.setText(strDescription);
            txtCollegeCode.setText(strCollegeDetails);
            txtSemester.setText(strSemester);
            txtSequence.setText(strSequence);
            txtSection.setText(strSection);
            cmbDay.setValue(strDay);
            cmbModality.setValue(strModality);
            txtRoom.setText(strRoom);
            txtFacultyID.setText(strFacultyID);
            txtFacultyName.setText(strFacultyName);
            txtStartHour.setText(strStartHour);
            txtStartMin.setText(strStartMin);
            txtEndHour.setText(strEndHour);
            txtEndMin.setText(strEndMin);

            // anchorPaneSchedDetails.setDisable(false);
            btnEdit.setDisable(false);
            btnDelete.setDisable(false);
        }

    }
    private void updateLabelsSubjectPicker(TableModel.SubjectOptions subjectOption) {
        txtSubjectCodeAdd.setText(subjectOption.getSubjectCode());
        txtDescriptionAdd.setText(subjectOption.getDescription());
    }
    private void updateLabelsFacultyPicker(TableModel.FacultyOptions subjectOption) {
        if (strMode.equals("EDIT")){
            txtFacultyID.setText(subjectOption.getStrEmployeeID());
            txtFacultyName.setText(subjectOption.getStrFullName());
        } else {
            txtFacultyIDAdd.setText(subjectOption.getStrEmployeeID());
            txtFacultyNameAdd.setText(subjectOption.getStrFullName());
        }
    }

    private void updateMainTableWithFilter(String strSubjectSearchTerm, String strFacultySearchTerm, String strSectionSearchTerm,
                                           String strDay, String strCollegeCode) {
        mainTableFilteredList = DBMethodsSubjectSchedulingMod.getSchedules(
                strSubjectSearchTerm, strFacultySearchTerm, strSectionSearchTerm,
                strDay, strCollegeCode);

        System.out.println("fetched results: " + mainTableFilteredList.size());

        TableUtils.populateTable(tblVwScheduleInfo, mainTableFilteredList);
    }

    private void updateSubjectPickerTableWithFilter(String strSubjectSearchTerm) {
        subjectPickerList = DBMethodsSubjectSchedulingMod.getSubjectOptions(
                strSubjectSearchTerm);
        System.out.println("fetched results: " + subjectPickerList.size());
        TableUtils.populateTable(tblVwSubjectInfo, subjectPickerList);
    }

    private void updateFacultyPickerTableWithFilter(String strFacultySearchTerm) {
        facultyPickerList = DBMethodsSubjectSchedulingMod.getEmployeeOptions(
                strFacultySearchTerm);
        System.out.println("fetched results: " + facultyPickerList.size());
        TableUtils.populateTable(tblVwFacultyInfo, facultyPickerList);
    }

    /* Button Mode methods */

    public void handleDeleteMode() {
        strMode = "DELETE";
        // table shouldn't be focusable
        tblVwScheduleInfo.setFocusModel(null);
        // disable table listener
        TableUtils.removeTableClickListener(tblVwScheduleInfo);

        btnEdit.setDisable(true);
        btnAdd.setDisable(true);
        btnDeleteCommit.setVisible(true);
        btnCancel.setDisable(false);

        // disable fields
        cmbDay.setDisable(true);
        cmbModality.setDisable(true);
        txtRoom.setDisable(true);
        txtStartHour.setDisable(true);
        txtStartMin.setDisable(true);
        txtEndHour.setDisable(true);
        txtEndMin.setDisable(true);
        btnChangeFacultyAssign.setDisable(true);
        anchorPaneSchedDetails.setDisable(false);
        anchorPaneSchedDetails.getStyleClass().remove("custom-border-white-container");
        anchorPaneSchedDetails.getStyleClass().add("custom-delete-container");
    }

    public void handleEditMode() {
        strMode = "EDIT";
        // table shouldn't be focusable and filter should not work
        tblVwScheduleInfo.setFocusModel(null);
        // disable table listener
        TableUtils.removeTableClickListener(tblVwScheduleInfo);

        btnDelete.setDisable(true);
        btnAdd.setDisable(true);
        btnEditCommit.setVisible(true);
        btnRevertDetails.setVisible(true);
        cmbDay.setDisable(false);
        cmbModality.setDisable(false);
        btnCancel.setDisable(false);
        txtRoom.setDisable(false);
        txtStartHour.setDisable(false);
        txtStartMin.setDisable(false);
        txtEndHour.setDisable(false);
        txtEndMin.setDisable(false);
        btnChangeFacultyAssign.setDisable(false);
        anchorPaneSchedDetails.setDisable(false);

        anchorPaneSchedDetails.getStyleClass().remove("custom-border-white-container");
        anchorPaneSchedDetails.getStyleClass().add("custom-edit-container");
    }

    public void handleAddMode() {
        strMode = "ADD";
        scrollPaneAddMode.setVisible(true);

        // table shouldn't be focusable
        tblVwScheduleInfo.setFocusModel(null);
        // disable table listener
        TableUtils.removeTableClickListener(tblVwScheduleInfo);

        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        btnCancel.setDisable(false);
        anchorPaneSchedDetails.setDisable(false);
        anchorPaneSchedDetails.getStyleClass().remove("custom-border-white-container");
        anchorPaneSchedDetails.getStyleClass().add("custom-add-container");
    }

    public void handleCancelFromModes() {
        if(!strMode.isEmpty()){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to cancel this process?");

            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.CANCEL) {
                return;
            }
        }

        strMode = "temp";
        if(strMode.equals("temp")){
            handleRevertToOriginal();
        }

        strMode = "";

        // make table focusable again
        TableView.TableViewFocusModel<TableModel.ScheduleModule> newFocusModel = new TableView.TableViewFocusModel<>(tblVwScheduleInfo);
        tblVwScheduleInfo.setFocusModel(newFocusModel);

        // enable mode buttons and make specific buttons invisible
        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
        btnAdd.setDisable(false);
        btnEditCommit.setVisible(false);
        btnDeleteCommit.setVisible(false);
        btnRevertDetails.setVisible(false);
        btnCancel.setDisable(true);
        anchorPaneSchedDetails.setDisable(true);

        // revert original table listener
        TableUtils.setTableClickListener(tblVwScheduleInfo, this::updateLabelsMainTable);

        // remove picker table items and disable them
        if(subjectPickerList != null){
            subjectPickerList.clear();
            tblVwSubjectInfo.setItems(subjectPickerList);
        }
        tblVwSubjectInfo.setDisable(true);
        btnClearSubjectDetails.setDisable(true);
        txtSubjectSearchDetails.setDisable(true);
        btnSubjectSearchDetails.setDisable(true);

        if(facultyPickerList != null){
            facultyPickerList.clear();
            tblVwFacultyInfo.setItems(facultyPickerList);
        }
        tblVwFacultyInfo.setDisable(true);
        btnClearFacultyDetails.setDisable(true);
        txtEmployeeSearchDetails.setDisable(true);
        btnFacultySearchDetails.setDisable(true);

        scrollPaneAddMode.setVisible(false);
        lblSubjectTitle.setText("< >");
        anchorPaneSchedDetails.getStyleClass().remove("custom-add-container");
        anchorPaneSchedDetails.getStyleClass().remove("custom-edit-container");
        anchorPaneSchedDetails.getStyleClass().remove("custom-delete-container");

        anchorPaneSchedDetails.getStyleClass().add("custom-border-white-container");

        // reset all fields:
        clearAllFieldsOnDelete();
        handleClearAll();
        txtEmployeeSearchDetails.setText("");
        txtSubjectSearchDetails.setText("");
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
    }



    /* Inside button modes methods */
    public void handleChooseSubjectAssignment(){
        // populate subject picker and enable click listener
        updateSubjectPickerTableWithFilter("");
        TableUtils.setTableClickListener(tblVwSubjectInfo, this::updateLabelsSubjectPicker);

        tblVwSubjectInfo.setDisable(false);
        btnClearSubjectDetails.setDisable(false);
        txtSubjectSearchDetails.setDisable(false);
        btnSubjectSearchDetails.setDisable(false);
    }
    public void handleChooseFacultyAssignment(){
        if (strMode.equals("EDIT")){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to change the current faculty assignment?\n" +
                    "This action will affect existing records.");

            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            if (result != ButtonType.OK) {
                return;
            }
        }

        // populate subject picker and enable click listener
        updateFacultyPickerTableWithFilter("");
        TableUtils.setTableClickListener(tblVwFacultyInfo, this::updateLabelsFacultyPicker);

        tblVwFacultyInfo.setDisable(false);
        btnClearFacultyDetails.setDisable(false);
        txtEmployeeSearchDetails.setDisable(false);
        btnFacultySearchDetails.setDisable(false);
    }
    public void handleRevertToOriginal() {
        if(strMode.equals("EDIT")){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to revert the details back to the original?");

            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            if (result != ButtonType.OK) {
                return;
            }
        }

        txtSubjectCode.setText(strSubjectCode);
        txtDescription.setText(strDescription);
        txtCollegeCode.setText(strCollegeDetails);
        txtSemester.setText(strSemester);
        txtSequence.setText(strSequence);
        txtSection.setText(strSection);
        cmbDay.setValue(strDay);
        cmbModality.setValue(strModality);
        txtRoom.setText(strRoom);
        txtFacultyID.setText(strFacultyID);
        txtFacultyName.setText(strFacultyName);
        txtStartHour.setText(strStartHour);
        txtStartMin.setText(strStartMin);
        txtEndHour.setText(strEndHour);
        txtEndMin.setText(strEndMin);

        resetErrorLabelsEdit();
    }



    /* Add mode button methods */

    public void handleCheckSeq1() {
        boolean isChecked = chkSeq1.isSelected();

        if(!isChecked){  // clear fields
            anchorPaneSeq1.setDisable(true);
            cmbDaySeq1.setValue("");
            cmbModalitySeq1.setValue("");
            txtRoomSeq1.setText("");
            txtStartHourSeq1.setText("");
            txtStartMinSeq1.setText("");
            txtEndHourSeq1.setText("");
            txtEndMinSeq1.setText("");

            // set color to grey
            anchorPaneSeq1.getStyleClass().remove("rounded-bordered-green-container");
            anchorPaneSeq1.getStyleClass().add("rounded-bordered-white-container");
        } else {
            anchorPaneSeq1.setDisable(false);
            // set color to green
            anchorPaneSeq1.getStyleClass().remove("rounded-bordered-white-container");
            anchorPaneSeq1.getStyleClass().add("rounded-bordered-green-container");
        }
    }

    public void handleCheckSeq2() {
        boolean isChecked = chkSeq2.isSelected();

        if(!isChecked){  // clear fields
            anchorPaneSeq2.setDisable(true);
            cmbDaySeq2.setValue("");
            cmbModalitySeq2.setValue("");
            txtRoomSeq2.setText("");
            txtStartHourSeq2.setText("");
            txtStartMinSeq2.setText("");
            txtEndHourSeq2.setText("");
            txtEndMinSeq2.setText("");

            // set color to grey
            anchorPaneSeq2.getStyleClass().remove("rounded-bordered-green-container");
            anchorPaneSeq2.getStyleClass().add("rounded-bordered-white-container");
        } else {
            anchorPaneSeq2.setDisable(false);
            // set color to green
            anchorPaneSeq2.getStyleClass().remove("rounded-bordered-white-container");
            anchorPaneSeq2.getStyleClass().add("rounded-bordered-green-container");
        }
    }

    public void handleCheckSeq3() {
        boolean isChecked = chkSeq3.isSelected();

        if(!isChecked){ // clear fields
            anchorPaneSeq3.setDisable(true);
            cmbDaySeq3.setValue("");
            cmbModalitySeq3.setValue("");
            txtRoomSeq3.setText("");
            txtStartHourSeq3.setText("");
            txtStartMinSeq3.setText("");
            txtEndHourSeq3.setText("");
            txtEndMinSeq3.setText("");
            anchorPaneSeq3.getStyleClass().remove("rounded-bordered-green-container");
            anchorPaneSeq3.getStyleClass().add("rounded-bordered-white-container");
        } else {
            anchorPaneSeq3.setDisable(false);
            // set color to green
            anchorPaneSeq3.getStyleClass().remove("rounded-bordered-white-container");
            anchorPaneSeq3.getStyleClass().add("rounded-bordered-green-container");
        }
    }

    public void handleClearAll() {
        if(strMode.equals("ADD")){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to clear all details?");

            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            if (result != ButtonType.OK) {
                return;
            }
        }
        txtSubjectCodeAdd.setText("");
        txtDescriptionAdd.setText("");
        txtSectionAdd.setText("");
        txtFacultyIDAdd.setText("");
        txtFacultyNameAdd.setText("");
        cmbCollege.setValue("");
        cmbSemester.setValue("");

        chkSeq1.setSelected(false);
        chkSeq2.setSelected(false);
        chkSeq3.setSelected(false);
        handleCheckSeq1();
        handleCheckSeq2();
        handleCheckSeq3();

        anchorPaneSeq1.setDisable(true);
        anchorPaneSeq2.setDisable(true);
        anchorPaneSeq3.setDisable(true);

        resetErrorLabelsAdd();
    }


    /* Database add, edit, delete methods */
    public void handleEditCommit(){
        if(inputValidation()){
            strSubjectCode = txtSubjectCode.getText();
            strCollegeDetails = txtCollegeCode.getText();
            strSemester = txtSemester.getText();
            strSequence = txtSequence.getText();
            strSection = txtSection.getText();
            strDay = c.mapComboBoxValue(cmbDay.getValue(), "Day");
            strModality = cmbModality.getValue();
            strRoom = txtRoom.getText();
            strFacultyID = txtFacultyID.getText();
            String strTime = txtStartHour.getText() + ":" + txtStartMin.getText() + "-" + txtEndHour.getText() + ":" + txtEndMin.getText();

            if(DBMethodsSubjectSchedulingMod.editScheduleModule(strSubjectCode, strCollegeDetails, strSemester, strSequence,
                    strSection, strDay, strModality, strRoom, strTime, strFacultyID)){
                strMode = "";

                handleCancelFromModes();
                updateMainTableWithFilter("","","", "", "");
                showSuccessDialog();
            }
        }
    }
    public void handleAddCommit(){
        if(inputValidationAdd()){
            Boolean flagSuccess = true;

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to add the inputted information in the schedule records?");

            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            if (result != ButtonType.OK) {
                return;
            }

            String strSubjectCode = txtSubjectCodeAdd.getText();
            String strCollegeDetails = cmbCollege.getValue();
            String strSemester = cmbSemester.getValue();
            String strSection = txtSectionAdd.getText();
            String strFacultyID = txtFacultyIDAdd.getText();

            // Sequence 1 Details + INSERT
            if(chkSeq1.isSelected()){
                String strSequence = "1";
                String strDay = c.mapComboBoxValue(cmbDaySeq1.getValue(), "Day");
                String strModality = cmbModalitySeq1.getValue();
                String strRoom = txtRoomSeq1.getText();
                String strTime = txtStartHourSeq1.getText() + ":" + txtStartMinSeq1.getText() + "-" + txtEndHourSeq1.getText() + ":" + txtEndMinSeq1.getText();

                flagSuccess = DBMethodsSubjectSchedulingMod.addScheduleModule(strSubjectCode, strCollegeDetails, strSemester, strSequence,
                        strSection, strDay, strModality, strRoom, strTime, strFacultyID);
            }

            // Sequence 2 Details + INSERT
            if(chkSeq2.isSelected()){
                String strSequence = "2";
                String strDay = c.mapComboBoxValue(cmbDaySeq2.getValue(), "Day");
                String strModality = cmbModalitySeq2.getValue();
                String strRoom = txtRoomSeq2.getText();
                String strTime = txtStartHourSeq2.getText() + ":" + txtStartMinSeq2.getText() + "-" + txtEndHourSeq2.getText() + ":" + txtEndMinSeq2.getText();

                flagSuccess = DBMethodsSubjectSchedulingMod.addScheduleModule(strSubjectCode, strCollegeDetails, strSemester, strSequence,
                        strSection, strDay, strModality, strRoom, strTime, strFacultyID);
            }

            // Sequence 3 Details + INSERT
            if(chkSeq3.isSelected()){
                String strSequence = "3";
                String strDay = c.mapComboBoxValue(cmbDaySeq3.getValue(), "Day");
                String strModality = cmbModalitySeq3.getValue();
                String strRoom = txtRoomSeq3.getText();
                String strTime = txtStartHourSeq3.getText() + ":" + txtStartMinSeq3.getText() + "-" + txtEndHourSeq3.getText() + ":" + txtEndMinSeq3.getText();

                flagSuccess = DBMethodsSubjectSchedulingMod.addScheduleModule(strSubjectCode, strCollegeDetails, strSemester, strSequence,
                        strSection, strDay, strModality, strRoom, strTime, strFacultyID);
            }

            if(flagSuccess){
                strMode = "";
                handleCancelFromModes();
                updateMainTableWithFilter("","","", "", "");
                showSuccessDialog();
            }

        }
    }
    public void handleDeleteCommit(){
        strSubjectCode = txtSubjectCode.getText();
        strCollegeDetails = txtCollegeCode.getText();
        strSemester = txtSemester.getText();
        strSequence = txtSequence.getText();
        strSection = txtSection.getText();

        if(DBMethodsSubjectSchedulingMod.deleteScheduleModule(strSubjectCode, strCollegeDetails, strSemester, strSequence, strSection)){
            strMode = "";
            strSubjectCode = "";
            strCollegeDetails = "";
            strSemester = "";
            strSequence = "";
            strSection = "";
            clearAllFieldsOnDelete();
            handleCancelFromModes();
            updateMainTableWithFilter("","","", "", "");
            showSuccessDialog();
        }
    }

    private void clearAllFieldsOnDelete(){
        txtSubjectCode.setText("");
        txtDescription.setText("");
        txtCollegeCode.setText("");
        txtSemester.setText("");
        txtSequence.setText("");
        txtSection.setText("");
        cmbDay.setValue("");
        cmbModality.setValue("");
        txtRoom.setText("");
        txtStartHour.setText("");
        txtStartMin.setText("");
        txtEndHour.setText("");
        txtEndMin.setText("");
        txtFacultyID.setText("");
        txtFacultyName.setText("");
    }

    /* Database search methods */
    // Filter methods
    public void clearSubjectSearch() {
        txtSubjectSearch.setText("");
    }
    public void clearFacultySearch() {
        txtEmployeeSearch.setText("");
    }
    public void clearSectionSearch() {
        txtSecSearch.setText("");
    }
    public void clearAllFilterSearch() {
        txtSubjectSearch.setText("");
        txtEmployeeSearch.setText("");
        txtSecSearch.setText("");
        cmbScheduleFilter.setValue("");
        cmbCollegeFilter.setValue("");

        updateMainTableWithFilter("", "", "", "", "");
    }

    public void handleFilterSearch() {
        String strSubjectSearchTerm = txtSubjectSearch.getText();
        String strFacultySearchTerm = txtEmployeeSearch.getText();
        String strSectionSearchTerm = txtSecSearch.getText();
        String strDay = c.getSelectedItemOrEmptyScheduleCmbBox(cmbScheduleFilter).trim();
        String strCollegeCode = c.getSelectedItemOrEmptyCmbBox(cmbCollegeFilter);
        System.out.println("\"" + strDay + "\"");
        updateMainTableWithFilter(strSubjectSearchTerm, strFacultySearchTerm, strSectionSearchTerm, strDay, strCollegeCode);
    }

    // Subject Search methods
    public void clearSubjectDetailsSearch() {
        txtSubjectSearchDetails.setText("");
    }
    public void handleSubjectDetailsSearch() {
        updateSubjectPickerTableWithFilter(txtSubjectSearchDetails.getText());
    }

    // Faculty Search methods
    public void clearFacultyDetailsSearch() {
        txtEmployeeSearchDetails.setText("");
    }

    public void handleFacultyDetailsSearch() {
        updateFacultyPickerTableWithFilter(txtEmployeeSearchDetails.getText());
    }


    /* Input validation */
    private boolean inputValidation() {
        boolean flagValid = true; // Assume all inputs are valid initially

        // Validate room
        if (!iv.isValidSecRoomCode(txtRoom.getText())) {
            errLblRoom.setText("Enter a valid room");
            flagValid = false;
        } else {
            errLblRoom.setText("");
        }

        // Validate time
        String strTime = txtStartHour.getText() + ":" + txtStartMin.getText() + "-" + txtEndHour.getText() + ":" + txtEndMin.getText();
        System.out.println("TIME: " + strTime);
        if (!iv.isValidTimeRange(strTime)) {
            errLblTime.setText("Enter a valid time");
            flagValid = false;
        } else {
            errLblTime.setText("");
        }

        // Display warning and return false if at least one input is not valid
        if (!flagValid) {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Invalid Input");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Please recheck your inputs.");

            warningAlert.showAndWait();
        }

        return flagValid;
    }

    private boolean inputValidationAdd() {
        boolean flagValid = true; // Assume all inputs are valid initially

        /* Check default requirements and sequence 1 inputs */
        // Validate subject code
        if (txtSubjectCodeAdd.getText().isEmpty()) {
            errLblSubject.setText("Required *");
            flagValid = false;
        } else {
            errLblSubject.setText("");
        }

        // Validate faculty id
        if (txtFacultyIDAdd.getText().isEmpty()) {
            errLblFaculty.setText("Required *");
            flagValid = false;
        } else {
            errLblFaculty.setText("");
        }

        // Validate college code
        if (cmbCollege.getSelectionModel().isEmpty()) {
            errLblCollegeCode.setText("required *");
            flagValid = false;
        } else {
            errLblCollegeCode.setText("");
        }

        // Validate semester
        if (cmbSemester.getSelectionModel().isEmpty()) {
            errLblSemester.setText("required *");
            flagValid = false;
        } else {
            errLblSemester.setText("");
        }

        // Validate section
        if (!iv.isValidSecRoomCode(txtFacultyIDAdd.getText())) {
            errLblSectionAdd.setText("Input a valid section");
            flagValid = false;
        } else {
            errLblSectionAdd.setText("");
        }


        /* Sequence 1 details */
        if(chkSeq1.isSelected()) {
            // Validate Seq1 Day
            if (cmbDaySeq1.getSelectionModel().isEmpty()) {
                errLblDaySeq1.setText("required *");
                flagValid = false;
            } else {
                errLblDaySeq1.setText("");
            }

            // Validate Seq1 Modality
            if (cmbModalitySeq1.getSelectionModel().isEmpty()) {
                errLblModalitySeq1.setText("required *");
                flagValid = false;
            } else {
                errLblModalitySeq1.setText("");
            }

            // Validate Seq1 Room
            if (!iv.isValidSecRoomCode(txtRoomSeq1.getText())) {
                errLblRoomSeq1.setText("Input a valid section");
                flagValid = false;
            } else {
                errLblRoomSeq1.setText("");
            }

            // Validate Seq1 Time
            String strTime1 = txtStartHourSeq1.getText() + ":" + txtStartMinSeq1.getText() + "-" + txtEndHourSeq1.getText() + ":" + txtEndMinSeq1.getText();
            System.out.println("TIME: " + strTime1);
            if (!iv.isValidTimeRange(strTime1)) {
                errLblTimeSeq1.setText("Enter a valid time");
                flagValid = false;
            } else {
                errLblTimeSeq1.setText("");
            }
        }

        /* Sequence 2 details */
        if(chkSeq2.isSelected()) {
            // Validate Seq2 Day
            if (cmbDaySeq2.getSelectionModel().isEmpty()) {
                errLblDaySeq2.setText("required *");
                flagValid = false;
            } else {
                errLblDaySeq2.setText("");
            }

            // Validate Seq2 Modality
            if (cmbModalitySeq2.getSelectionModel().isEmpty()) {
                errLblModalitySeq2.setText("required *");
                flagValid = false;
            } else {
                errLblModalitySeq2.setText("");
            }

            // Validate Seq2 Room
            if (!iv.isValidSecRoomCode(txtRoomSeq2.getText())) {
                errLblRoomSeq2.setText("Input a valid section");
                flagValid = false;
            } else {
                errLblRoomSeq2.setText("");
            }

            // Validate Seq2 Time
            String strTime2 = txtStartHourSeq2.getText() + ":" + txtStartMinSeq2.getText() + "-" + txtEndHourSeq2.getText() + ":" + txtEndMinSeq2.getText();
            System.out.println("TIME: " + strTime2);
            if (!iv.isValidTimeRange(strTime2)) {
                errLblTimeSeq2.setText("Enter a valid time");
                flagValid = false;
            } else {
                errLblTimeSeq2.setText("");
            }
        }

        /* Sequence 3 details */
        if(chkSeq3.isSelected()){
            // Validate Seq3 Day
            if (cmbDaySeq3.getSelectionModel().isEmpty()) {
                errLblDaySeq3.setText("required *");
                flagValid = false;
            } else {
                errLblDaySeq3.setText("");
            }

            // Validate Seq3 Modality
            if (cmbModalitySeq3.getSelectionModel().isEmpty()) {
                errLblModalitySeq3.setText("required *");
                flagValid = false;
            } else {
                errLblModalitySeq3.setText("");
            }

            // Validate Seq3 Room
            if (!iv.isValidSecRoomCode(txtRoomSeq3.getText())) {
                errLblRoomSeq3.setText("Input a valid section");
                flagValid = false;
            } else {
                errLblRoomSeq3.setText("");
            }

            // Validate Seq3 Time
            String strTime3 = txtStartHourSeq3.getText() + ":" + txtStartMinSeq3.getText() + "-" + txtEndHourSeq3.getText() + ":" + txtEndMinSeq3.getText();
            System.out.println("TIME: " + strTime3);
            if (!iv.isValidTimeRange(strTime3)) {
                errLblTimeSeq3.setText("Enter a valid time");
                flagValid = false;
            } else {
                errLblTimeSeq3.setText("");
            }
        }



        // Display warning and return false if at least one input is not valid
        if (!flagValid) {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Invalid Input");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Please recheck your inputs.");

            warningAlert.showAndWait();
        }

        return flagValid;
    }

    private void resetErrorLabelsEdit() {
        errLblRoom.setText("");
        errLblTime.setText("");
    }

    private void resetErrorLabelsAdd() {
        errLblSubject.setText("");
        errLblCollegeCode.setText("");
        errLblSemester.setText("");
        errLblSectionAdd.setText("");
        errLblFaculty.setText("");

        errLblDaySeq1.setText("");
        errLblModalitySeq1.setText("");
        errLblRoomSeq1.setText("");
        errLblTimeSeq1.setText("");

        errLblDaySeq2.setText("");
        errLblModalitySeq2.setText("");
        errLblRoomSeq2.setText("");
        errLblTimeSeq2.setText("");

        errLblDaySeq3.setText("");
        errLblModalitySeq3.setText("");
        errLblRoomSeq3.setText("");
        errLblTimeSeq3.setText("");
    }

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/SubjectSchedulingModule/SubjectSchedulingModule.fxml", anchorPaneContentContainer);
    }
}


