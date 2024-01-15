package com.OOP.plmares.controllers.admin_system.SubjectModuleControllers;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSubjectSchedulingMod;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Map;

public class SubjectModuleEditRecordController implements DataInitializable {
    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TextField txtSubjectCode, txtDescription;
    @FXML private Spinner<Integer> spnUnits = new Spinner<>(1, 10, 1);
    @FXML private ComboBox<String> cmbCurriculum, cmbCollegeCode, cmbActive;
    @FXML private Label errLblDescription;
    @FXML private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
    private CommonUtils c = new CommonUtils();
    private InputValidationUtils iv = new InputValidationUtils();
    private String strSubjectCode = "", strDescription = "", strCurriculum = "", strCollegeCode = "", strStatus = "";
    private int intUnits = 0;
    private boolean isInitialized = false;

    @Override
    public void initializeData(Map<String, Object> data) {
        strSubjectCode = (String) data.get("subjectCode");
        if (!isInitialized) {
            isInitialized = true;
            initialize();
        }
    }

    @FXML
    private void initialize() {
        if (isInitialized) {
            ObservableList<String> currYears = FXCollections.observableArrayList(
                    "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015",
                    "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024");
            cmbCurriculum.setItems(currYears);

            DBCommonMethods.populateComboBox(cmbCollegeCode, "college", "college_code");
            cmbCollegeCode.getItems().remove("--");

            ObservableList<String> activeStatus = FXCollections.observableArrayList("Active", "Inactive");
            cmbActive.setItems(activeStatus);

            // set original data from selected student:
            ObservableList<TableModel.SubjectModuleInfo> selectedCourse = DBMethodsSubjectSchedulingMod.getSubjects(strSubjectCode);

            strDescription = selectedCourse.get(0).getDescription();
            strCollegeCode = selectedCourse.get(0).getCollege();
            intUnits = selectedCourse.get(0).getUnits();
            strCurriculum = selectedCourse.get(0).getStrCurriculum();
            strStatus = selectedCourse.get(0).getStatus();

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
        txtSubjectCode.setText(strSubjectCode);
        txtDescription.setText(strDescription);
        spnUnits.setValueFactory(valueFactory);
        spnUnits.getValueFactory().setValue(intUnits);
        cmbCurriculum.setValue(strCurriculum);
        cmbCollegeCode.setValue(strCollegeCode);

        if (strStatus.equals("A"))
            cmbActive.setValue("Active");
        else
            cmbActive.setValue("Inactive");

        errLblDescription.setText("");
    }


    public void onClickBtnEdit(){
        // get field values
        String strSubjectCode = txtSubjectCode.getText();
        String strDescription = txtDescription.getText();
        int intUnits = spnUnits.getValue();
        String strCurriculum = cmbCurriculum.getValue();
        String strCollegeCode = cmbCollegeCode.getValue();
        String strActive = c.mapComboBoxValue(cmbActive.getValue(), "Status");

        // input validation test + edit confirmation
        if(inputValidation()){
            if(DBMethodsSubjectSchedulingMod.editSubjectModule(strSubjectCode, strDescription, intUnits,
                    strCurriculum, strCollegeCode, strActive))
                c.loadScreen("/FXML/admin_system/SubjectSchedulingModule/SubjectModule.fxml", anchorPaneContentContainer);
        }
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
