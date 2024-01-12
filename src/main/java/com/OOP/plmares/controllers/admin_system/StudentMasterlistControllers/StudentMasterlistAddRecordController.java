package com.OOP.plmares.controllers.admin_system.StudentMasterlistControllers;
import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsStudentMasterlistMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;


public class StudentMasterlistAddRecordController {

    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TextField txtLastName, txtFirstName, txtStudentNo, txtPLMEmail, txtMobileNum;
    @FXML private TextArea txtAreaAddress;
    @FXML private ComboBox<String> cmbGender, cmbActive, cmbCourse, cmbRegistration;
    @FXML private DatePicker dateBirthday;
    @FXML private Label errLblLastName, errLblFirstName, errLblMobileNumber, errLblAddress, errLblBirthday, errLblGender, errLblActive, errLblCourse, errLblStatus;

    private CommonUtils c = new CommonUtils();
    private InputValidationUtils iv = new InputValidationUtils();
    private String strStudentNo = "", strSy = "";


    @FXML
    private void initialize() {
        // set-up maximum student number
        strSy = DBMethodsSySem.getActiveSy();
        strStudentNo = DBMethodsStudentMasterlistMod.getMaxStudentNo(strSy);
        System.out.println("STUDENT NUMBER MAX: " + strStudentNo);
        txtStudentNo.setText(strStudentNo);

        //populate comboboxes
        DBCommonMethods.populateComboBox(cmbCourse, "course", "course_code");
        cmbCourse.getItems().remove("--");

        ObservableList<String> gender = FXCollections.observableArrayList(
                "Male", "Female");
        cmbGender.setItems(gender);

        ObservableList<String> activeStatus = FXCollections.observableArrayList(
                "Active", "Inactive");
        cmbActive.setItems(activeStatus);

        ObservableList<String> regStatus = FXCollections.observableArrayList(
                "Regular", "Irregular");
        cmbRegistration.setItems(regStatus);

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
        // Revert the details back to the original values
        txtLastName.setText("");
        txtFirstName.setText("");
        txtPLMEmail.setText("");
        txtMobileNum.setText("");
        dateBirthday.setValue(null);
        cmbGender.setValue("");
        cmbActive.setValue("");
        txtAreaAddress.setText("");
        cmbCourse.setValue("");
        cmbRegistration.setValue("");
        clearErrorLabels();
    }



    public void onClickBtnAddRecord(){
        if(inputValidation()){
            // get field values
            String strLastName = txtLastName.getText();
            String strFirstName = txtFirstName.getText();
            String strMobileNum = txtMobileNum.getText();
            String strBirthday =  c.getDateValue(dateBirthday);
            String strGender = c.mapComboBoxValue(cmbGender.getValue(), "Gender");
            String strActive = c.mapComboBoxValue(cmbActive.getValue(), "Status");
            String strAddress = txtAreaAddress.getText();
            String strCourse = cmbCourse.getValue();
            String strStatus = c.mapComboBoxValue(cmbRegistration.getValue(), "RegistrationStatus");

            // input validation test + edit confirmation

            String strPLMEmail = handleBtnGenerateEmail();   // get updated plm email
            if(DBMethodsStudentMasterlistMod.addStudentMasterlist(strStudentNo, strLastName, strFirstName, strPLMEmail, strMobileNum, strBirthday,
                    strGender, strActive, strAddress, strCourse, strStatus))
                c.loadScreen("/FXML/admin_system/StudentMasterlist/StudentMasterlist.fxml", anchorPaneContentContainer);
        }
    }

    private void clearErrorLabels() {
        errLblLastName.setText("");
        errLblFirstName.setText("");
        errLblMobileNumber.setText("");
        errLblBirthday.setText("");
        errLblAddress.setText("");
        errLblGender.setText("");
        errLblActive.setText("");
        errLblCourse.setText("");
        errLblStatus.setText("");
    }

    private boolean inputValidation() {
        boolean flagValid = true; // Assume all inputs are valid initially

        // Validate Last Name
        if (!iv.isValidName(txtLastName.getText())) {
            errLblLastName.setText("Enter a valid name");
            flagValid = false;
        } else {
            errLblLastName.setText("");
        }

        // Validate First Name
        if (!iv.isValidName(txtFirstName.getText())) {
            errLblFirstName.setText("Enter a valid name");
            flagValid = false;
        } else {
            errLblFirstName.setText("");
        }

        // Validate Mobile Number
        if (!iv.isValidPhilippineNumber(txtMobileNum.getText())) {
            errLblMobileNumber.setText("Enter a valid number. Follow format (09XX-XXX-XXXX)");
            flagValid = false;
        } else {
            errLblMobileNumber.setText("");
        }

        // Validate Birthday
        if (!iv.isValidDate(c.getDateValue(dateBirthday))) {
            errLblBirthday.setText("Enter a valid date");
            flagValid = false;
        } else {
            errLblBirthday.setText("");
        }

        // Validate Address
        if (!iv.isValidAddress(txtAreaAddress.getText())) {
            errLblAddress.setText("Enter a valid address");
            flagValid = false;
        } else {
            errLblAddress.setText("");
        }


        // check the rest of the cmb box if theyre empty:


        if (cmbGender.getSelectionModel().isEmpty()) {
            errLblGender.setText("required *");
            flagValid = false;
        } else {
            errLblGender.setText("");
        }

        if (cmbActive.getSelectionModel().isEmpty()) {
            errLblActive.setText("required *");
            flagValid = false;
        } else {
            errLblActive.setText("");
        }

        if (cmbCourse.getSelectionModel().isEmpty()) {
            errLblCourse.setText("required *");
            flagValid = false;
        } else {
            errLblCourse.setText("");
        }

        if (cmbRegistration.getSelectionModel().isEmpty()) {
            errLblStatus.setText("required *");
            flagValid = false;
        } else {
            errLblStatus.setText("");
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

    public String handleBtnGenerateEmail() {  //generate updated email
        String strGeneratedEmail = c.generatePLMEmail(txtLastName.getText(), txtFirstName.getText(), txtStudentNo.getText());
        txtPLMEmail.setText(strGeneratedEmail);
        return strGeneratedEmail;
    }

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/StudentMasterlist/StudentMasterlist.fxml", anchorPaneContentContainer);
    }
}
