package com.OOP.plmares.controllers.admin_system.SubjectModuleControllers;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSubjectSchedulingMod;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class SubjectModuleAddRecordController {
    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TextField txtSubjectCode, txtDescription;
    @FXML private Spinner<Integer> spnUnits = new Spinner<>(1, 10, 1);
    @FXML private ComboBox<String> cmbCurriculum, cmbCollegeCode, cmbActive;
    @FXML private Label errLblSubjectCode, errLblDescription, errLblCurriculum, errLblCollegeCode, errLblActiveStatus;

    @FXML private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);


    private CommonUtils c = new CommonUtils();

    private InputValidationUtils iv = new InputValidationUtils();

    @FXML
    private void initialize() {
        ObservableList<String> currYears = FXCollections.observableArrayList(
                "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015",
                "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
        cmbCurriculum.setItems(currYears);

        DBCommonMethods.populateComboBox(cmbCollegeCode, "college", "college_code");
        cmbCollegeCode.getItems().remove("--");

        ObservableList<String> activeStatus = FXCollections.observableArrayList(
                "Active", "Inactive");
        cmbActive.setItems(activeStatus);

        spnUnits.setValueFactory(valueFactory);
        spnUnits.getValueFactory().setValue(1);
    }

    public void onClickClearAll() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to clear all the details?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return;
        }

        setFieldsToDefault();
    }

    public void setFieldsToDefault() {
        txtSubjectCode.setText("");
        txtDescription.setText("");
        spnUnits.setValueFactory(valueFactory);
        spnUnits.getValueFactory().setValue(1);
        cmbCurriculum.setValue("");
        cmbCollegeCode.setValue("");
        cmbActive.setValue("");
        clearErrorLabels();
    }
    public void onClickBtnAddRecord(){

        // get field values
        String strSubjectCode = txtSubjectCode.getText();
        String strDescription = txtDescription.getText();
        spnUnits.setValueFactory(valueFactory);
        int intUnits =  spnUnits.getValue();
        String strCurriculum =  cmbCurriculum.getValue();
        String strCollegeCode = cmbCollegeCode.getValue();
        String strActive = c.mapComboBoxValue(cmbActive.getValue(), "Status");

        // input validation test + edit confirmation
        if(inputValidation()){
            if(DBMethodsSubjectSchedulingMod.addSubjectModule(strSubjectCode, strDescription, intUnits,
                    strCurriculum, strCollegeCode, strActive))
                c.loadScreen("/FXML/admin_system/SubjectSchedulingModule/SubjectModule.fxml", anchorPaneContentContainer);
        }
    }

    private void clearErrorLabels() {
        errLblSubjectCode.setText("");
        errLblDescription.setText("");
        errLblCurriculum.setText("");
        errLblCollegeCode.setText("");
        errLblActiveStatus.setText("");
    }

    private boolean inputValidation() {
        boolean flagValid = true; // Assume all inputs are valid initially

        System.out.println("txtSubjectCode value: \"" + txtSubjectCode.getText().trim() + "\"");
        // Validate Course code
        if (!iv.isValidSubjectCode(txtSubjectCode.getText().trim(), "subject", "subject_code")) {
            errLblSubjectCode.setText("Ensure code is valid, unique, & less than 10 characters.");
            flagValid = false;
        } else {
            errLblSubjectCode.setText("");
        }

        // Validate Description
        if (!iv.isValidDescription(txtDescription.getText())) {
            errLblDescription.setText("Enter a valid description");
            flagValid = false;
        } else {
            errLblDescription.setText("");
        }

        // Check if curriculum code is empty
        if (cmbCurriculum.getSelectionModel().isEmpty()) {
            errLblCurriculum.setText("required *");
            flagValid = false;
        } else {
            errLblCurriculum.setText("");
        }

        // Check if college code is empty
        if (cmbCollegeCode.getSelectionModel().isEmpty()) {
            errLblCollegeCode.setText("required *");
            flagValid = false;
        } else {
            errLblCollegeCode.setText("");
        }

        // Check if active is empty
        if (cmbActive.getSelectionModel().isEmpty()) {
            errLblActiveStatus.setText("required *");
            flagValid = false;
        } else {
            errLblActiveStatus.setText("");
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

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/SubjectSchedulingModule/SubjectModule.fxml", anchorPaneContentContainer);
    }
}
