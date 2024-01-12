package com.OOP.plmares.controllers.admin_system.StudentMasterlistControllers;
import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsStudentMasterlistMod;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;


public class StudentMasterlistEditRecordController implements DataInitializable {

    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TextField txtLastName, txtFirstName, txtStudentNo, txtPLMEmail, txtMobileNum;
    @FXML private TextArea txtAreaAddress;
    @FXML private ComboBox<String> cmbGender, cmbActive, cmbCourse, cmbRegistration;
    @FXML private DatePicker dateBirthday;
    @FXML private Label errLblLastName, errLblFirstName, errLblMobileNumber, errLblAddress, errLblBirthday;

    private CommonUtils c = new CommonUtils();
    private InputValidationUtils iv = new InputValidationUtils();

    private String strStudentNo = "", strLastName = "", strFirstName = "", strPLMEmail = "", strMobileNum = "", strBirthday = "",
            strGender = "", strActive = "", strAddress = "" , strCourse = "", strStatus = "";
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
    private void initialize() {
        if (isInitialized) {
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

            // set original data from selected student:
            ObservableList<TableModel.StudentMasterlist> selectedStudent = DBMethodsStudentMasterlistMod.getStudentMasterlist(strStudentNo);

            // split full name
            String fullName = selectedStudent.get(0).getStrFullName();
            String[] nameParts = fullName.split(",\\s*");

            if (nameParts.length >= 2) {
                strLastName = nameParts[0];
                strFirstName = nameParts[1];
            }

            strPLMEmail = selectedStudent.get(0).getStrEmail();
            strMobileNum = selectedStudent.get(0).getStrMobileNum();
            strBirthday = selectedStudent.get(0).getStrBirthday();
            strGender = selectedStudent.get(0).getStrGender();
            strActive = selectedStudent.get(0).getStrActive();
            strAddress = selectedStudent.get(0).getStrAddress();
            strCourse = selectedStudent.get(0).getStrCourse();
            strStatus = selectedStudent.get(0).getStrStatus();

            // set default original values on field
            setFieldsToDefault();
        }
    }

    public void onClickRevertToOriginal() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to revert the details back to the original?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            return;
        }

        setFieldsToDefault();
    }

    public void setFieldsToDefault() {
        // Revert the details back to the original values
        txtLastName.setText(strLastName);
        txtFirstName.setText(strFirstName);
        txtStudentNo.setText(strStudentNo);
        txtPLMEmail.setText(strPLMEmail);
        txtMobileNum.setText(strMobileNum);

        LocalDate birthday = LocalDate.parse(strBirthday);
        dateBirthday.setValue(birthday);

        cmbGender.setValue(strGender);

        if (strActive.equals("Y"))
            cmbActive.setValue("Active");
        else
            cmbActive.setValue("Inactive");

        txtAreaAddress.setText(strAddress);
        cmbCourse.setValue(strCourse);
        cmbRegistration.setValue(strStatus);
        clearErrorLabels();
    }



    public void onClickBtnEditRecord(){
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

            System.out.println("gender: " + strGender);
            System.out.println("active status: " + strActive);
            System.out.println("reg status: " + strStatus);

            // input validation test + edit confirmation

            String strPLMEmail = handleBtnGenerateEmail();   // get updated plm email
            if(DBMethodsStudentMasterlistMod.editStudentMasterlist(strStudentNo, strLastName, strFirstName, strPLMEmail, strMobileNum, strBirthday,
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
