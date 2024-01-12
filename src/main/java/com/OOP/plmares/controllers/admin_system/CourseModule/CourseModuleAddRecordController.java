package com.OOP.plmares.controllers.admin_system.CourseModule;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsCourseMod;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;

public class CourseModuleAddRecordController {
    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TextField txtCourseCode, txtDescription;
    @FXML private DatePicker dtDateOpened, dtDateClosed;
    @FXML private ComboBox<String> cmbCollegeCode, cmbActive;
    @FXML private Label errLblDescription, errLblDateOpened, errLblDateClosed, errLblCourseCode, errLblCollegeCode, errLblActiveStatus;

    private CommonUtils c = new CommonUtils();

    private InputValidationUtils iv = new InputValidationUtils();

    @FXML
    private void initialize() {
        DBCommonMethods.populateComboBox(cmbCollegeCode, "college", "college_code");
        cmbCollegeCode.getItems().remove("--");

        ObservableList<String> activeStatus = FXCollections.observableArrayList(
                "Active", "Inactive");
        cmbActive.setItems(activeStatus);

    }

    public void onClickClearAll() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all the details?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        setFieldsToDefault();
    }

    public void setFieldsToDefault() {
        txtCourseCode.setText("");
        txtDescription.setText("");
        dtDateOpened.setValue(null);
        dtDateClosed.setValue(null);
        cmbCollegeCode.setValue("");
        cmbActive.setValue("");
        clearErrorLabels();
    }
    public void onClickBtnAddRecord(){
        // input validation test + edit confirmation
        if(inputValidation()){
            // get field values
            String strCourseCode = txtCourseCode.getText();
            String strDescription = txtDescription.getText();
            String strDateOpened =  c.getDateValue(dtDateOpened);
            String strDateClosed =  c.getDateValue(dtDateClosed);
            String strCollegeCode = cmbCollegeCode.getValue();
            String strActive = c.mapComboBoxValue(cmbActive.getValue(), "Status");


            if(DBMethodsCourseMod.addCourseModule(strCourseCode, strDescription, strCollegeCode,
                    strDateOpened, strDateClosed, strActive))
                c.loadScreen("/FXML/admin_system/CourseModule/CourseModule.fxml", anchorPaneContentContainer);
        }
    }

    private void clearErrorLabels() {
        errLblCourseCode.setText("");
        errLblDescription.setText("");
        errLblDateOpened.setText("");
        errLblDateClosed.setText("");
        errLblCollegeCode.setText("");
        errLblActiveStatus.setText("");
    }
    private boolean inputValidation() {
        boolean flagValid = true; // Assume all inputs are valid initially

        // Validate Course code
        if (!iv.isValidCode(txtCourseCode.getText(), "course", "course_code")) {
            errLblCourseCode.setText("Ensure code is valid and unique.");
            flagValid = false;
        } else {
            errLblCourseCode.setText("");
        }

        // Validate Description
        if (!iv.isValidDescription(txtDescription.getText())) {
            errLblDescription.setText("Enter a valid description");
            flagValid = false;
        } else {
            errLblDescription.setText("");
        }

        // Validate Date Opened
        if (!iv.isValidDate(c.getDateValue(dtDateOpened))) {
            errLblDateOpened.setText("Enter a valid date");
            flagValid = false;
        } else {
            errLblDateOpened.setText("");
        }

        // Validate Date Closed
        if (!iv.isValidDate(c.getDateValue(dtDateClosed))) {
            errLblDateClosed.setText("Enter a valid date");
            flagValid = false;
        } else {
            errLblDateClosed.setText("");
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
            JOptionPane.showMessageDialog(null, "Please recheck your inputs.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }

        return flagValid;
    }

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/CourseModule/CourseModule.fxml", anchorPaneContentContainer);
    }
}
