package com.OOP.plmares.controllers.admin_system.EmployeeMasterlistControllers;

import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEmployeeMasterlist;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;

public class EmployeeMasterlistAddRecordController {
    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TextField txtLastName, txtFirstName, txtEmployeeID, txtPLMEmail, txtMobileNum;
    @FXML private TextArea txtAreaAddress;
    @FXML private ComboBox<String> cmbGender, cmbActive;
    @FXML private DatePicker dateBirthday;
    @FXML private Label errLblLastName, errLblFirstName, errLblMobileNumber, errLblAddress, errLblBirthday, errLblGender, errLblActive;

    private CommonUtils c = new CommonUtils();
    private InputValidationUtils iv = new InputValidationUtils();
    private String strEmployeeID = "", strSy = "";


    @FXML
    private void initialize() {
        // set-up maximum employee ID
        strSy = DBMethodsSySem.getActiveSy();
        strEmployeeID = DBMethodsEmployeeMasterlist.getMaxEmployeeID(strSy);
        System.out.println("STUDENT NUMBER MAX: " + strEmployeeID);
        txtEmployeeID.setText(strEmployeeID);

        ObservableList<String> gender = FXCollections.observableArrayList(
                "Male", "Female");
        cmbGender.setItems(gender);

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
        // Revert the details back to the original values
        txtLastName.setText("");
        txtFirstName.setText("");
        txtPLMEmail.setText("");
        txtMobileNum.setText("");
        dateBirthday.setValue(null);
        cmbGender.setValue("");
        cmbActive.setValue("");
        txtAreaAddress.setText("");
        clearErrorLabels();
    }


    public void onClickBtnAddEmployee(){
        // input validation test + edit confirmation
        if(inputValidation()){
            // get field values
            String strLastName = txtLastName.getText();
            String strFirstName = txtFirstName.getText();
            String strMobileNum = txtMobileNum.getText();
            String strBirthday =  c.getDateValue(dateBirthday);
            String strGender = c.mapComboBoxValue(cmbGender.getValue(), "Gender");
            String strActive = c.mapComboBoxValue(cmbActive.getValue(), "Status");
            String strAddress = txtAreaAddress.getText();

            String strPLMEmail = handleBtnGenerateEmail();   // get updated plm email
            if(DBMethodsEmployeeMasterlist.addEmployeeMasterlist(strEmployeeID, strLastName, strFirstName, strPLMEmail, strMobileNum, strBirthday,
                    strGender, strActive, strAddress))
                c.loadScreen("/FXML/admin_system/EmployeeMasterlist/EmployeeMasterlist.fxml", anchorPaneContentContainer);
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


        // check the rest of the cmb box if they're empty:

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


        // Display warning and return false if at least one input is not valid
        if (!flagValid) {
            JOptionPane.showMessageDialog(null, "Please recheck your inputs.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }

        return flagValid;
    }

    public String handleBtnGenerateEmail() {  // generate updated email
        String strGeneratedEmail = c.generatePLMEmailFaculty(txtLastName.getText(), txtFirstName.getText());
        txtPLMEmail.setText(strGeneratedEmail);
        return strGeneratedEmail;
    }

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/EmployeeMasterlist/EmployeeMasterlist.fxml", anchorPaneContentContainer);
    }
}
