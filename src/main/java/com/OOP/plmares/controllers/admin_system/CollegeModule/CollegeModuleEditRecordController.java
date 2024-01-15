package com.OOP.plmares.controllers.admin_system.CollegeModule;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsCollegeMod;
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

public class CollegeModuleEditRecordController implements DataInitializable {
    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TextField txtCollegeCode, txtDescription;
    @FXML private DatePicker dtDateOpened, dtDateClosed;
    @FXML private ComboBox<String> cmbActive;
    @FXML private Label errLblDescription, errLblDateOpened, errLblDateClosed;
    private CommonUtils c = new CommonUtils();
    private InputValidationUtils iv = new InputValidationUtils();
    private String strCollegeCode = "", strDescription = "", strDateOpened = "", strDateClosed = "", strStatus = "";
    private boolean isInitialized = false;

    @Override
    public void initializeData(Map<String, Object> data) {

        strCollegeCode = (String) data.get("collegeCode");

        if (!isInitialized) {
            isInitialized = true;
            initialize();
        }
    }

    @FXML
    private void initialize() {
        if (isInitialized) {
            ObservableList<String> activeStatus = FXCollections.observableArrayList(
                    "Active", "Inactive");
            cmbActive.setItems(activeStatus);

            // set original data from selected student:
            ObservableList<TableModel.CollegeModule> selectedCollege = DBMethodsCollegeMod.getColleges(strCollegeCode);
            
            strCollegeCode = selectedCollege.get(0).getStrCollegeCode();
            strDescription = selectedCollege.get(0).getStrDescription();
            strDateOpened = selectedCollege.get(0).getStrDateOpened();
            strDateClosed = selectedCollege.get(0).getStrDateClosed();
            strStatus = selectedCollege.get(0).getStrStatus();

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
        inputValidation();
    }

    public void setFieldsToDefault() {
        // Revert the details back to the original values
        txtCollegeCode.setText(strCollegeCode);
        txtDescription.setText(strDescription);

        LocalDate dtOpened = LocalDate.parse(strDateOpened);
        dtDateOpened.setValue(dtOpened);

        LocalDate dtClosed = LocalDate.parse(strDateClosed);
        dtDateClosed.setValue(dtClosed);

        if (strStatus.equals("A"))
            cmbActive.setValue("Active");
        else
            cmbActive.setValue("Inactive");

        clearErrorLabels();
    }


    public void onClickBtnEdit(){
        // input validation test + edit confirmation
        if(inputValidation()){
            // get field values
            String strCollegeCode = txtCollegeCode.getText();
            String strDescription = txtDescription.getText();
            String strDateOpened = c.getDateValue(dtDateOpened);
            String strDateClosed = c.getDateValue(dtDateClosed);
            String strActive = c.mapComboBoxValue(cmbActive.getValue(), "Status");


            if(DBMethodsCollegeMod.editCollegeModule(strCollegeCode, strDescription,
                    strDateOpened, strDateClosed, strActive))
                c.loadScreen("/FXML/admin_system/CollegeModule/CollegeModule.fxml", anchorPaneContentContainer);
        }
    }

    private void clearErrorLabels() {
        errLblDescription.setText("");
        errLblDateOpened.setText("");
        errLblDateClosed.setText("");
    }

    private boolean inputValidation() {
        boolean flagValid = true; // Assume all inputs are valid initially

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

        // Display warning and return false if at least one input is not valid
        if (!flagValid) {
            JOptionPane.showMessageDialog(null, "Please recheck your inputs.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }

        return flagValid;
    }


    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/CollegeModule/CollegeModule.fxml", anchorPaneContentContainer);
    }
}
