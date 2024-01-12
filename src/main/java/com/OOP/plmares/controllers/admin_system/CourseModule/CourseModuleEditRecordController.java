package com.OOP.plmares.controllers.admin_system.CourseModule;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsCourseMod;
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

public class CourseModuleEditRecordController implements DataInitializable {
    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TextField txtCourseCode, txtDescription;
    @FXML private DatePicker dtDateOpened, dtDateClosed;
    @FXML private ComboBox<String> cmbCollegeCode, cmbActive;
    @FXML private Label errLblDescription, errLblDateOpened, errLblDateClosed;


    private CommonUtils c = new CommonUtils();
    private InputValidationUtils iv = new InputValidationUtils();

    private String strCourseCode = "", strDescription = "", strCollegeCode = "", strDateOpened = "", strDateClosed = "", strStatus = "";
    private boolean isInitialized = false;

    @Override
    public void initializeData(Map<String, Object> data) {

        strCourseCode = (String) data.get("courseCode");

        if (!isInitialized) {
            isInitialized = true;
            initialize();
        }
    }

    @FXML
    private void initialize() {
        if (isInitialized) {
            DBCommonMethods.populateComboBox(cmbCollegeCode, "college", "college_code");
            cmbCollegeCode.getItems().remove("--");

            ObservableList<String> activeStatus = FXCollections.observableArrayList(
                    "Active", "Inactive");
            cmbActive.setItems(activeStatus);
            
            // set original data from selected student:
            ObservableList<TableModel.CourseModule> selectedCourse = DBMethodsCourseMod.getCourses(strCourseCode);

            strDescription = selectedCourse.get(0).getStrDescription();
            strCollegeCode = selectedCourse.get(0).getStrCollegeCode();
            strDateOpened = selectedCourse.get(0).getStrDateOpened();
            strDateClosed = selectedCourse.get(0).getStrDateClosed();
            strStatus = selectedCourse.get(0).getStrStatus();

            // set default original values on field
            setFieldsToDefault();
        }
    }

    public void onClickRevertToOriginal() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to revert the details back to the original?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        setFieldsToDefault();
    }

    public void setFieldsToDefault() {
        // Revert the details back to the original values
        txtCourseCode.setText(strCourseCode);
        txtDescription.setText(strDescription);

        LocalDate dtOpened = LocalDate.parse(strDateOpened);
        dtDateOpened.setValue(dtOpened);

        LocalDate dtClosed = LocalDate.parse(strDateClosed);
        dtDateClosed.setValue(dtClosed);

        cmbCollegeCode.setValue(strCollegeCode);

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
            String strCourseCode = txtCourseCode.getText();
            String strDescription = txtDescription.getText();
            String strDateOpened = c.getDateValue(dtDateOpened);
            String strDateClosed = c.getDateValue(dtDateClosed);
            String strCollegeCode = cmbCollegeCode.getValue();
            String strActive = c.mapComboBoxValue(cmbActive.getValue(), "Status");

            if(DBMethodsCourseMod.editCourseModule(strCourseCode, strDescription, strCollegeCode,
                    strDateOpened, strDateClosed, strActive))
                c.loadScreen("/FXML/admin_system/CourseModule/CourseModule.fxml", anchorPaneContentContainer);
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
        c.loadScreen("/FXML/admin_system/CourseModule/CourseModule.fxml", anchorPaneContentContainer);
    }
}
