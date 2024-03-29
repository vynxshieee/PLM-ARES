package com.OOP.plmares.controllers.admin_system.EnrollmentModuleControllers;

import com.OOP.plmares.controllers.GlobalSchedule;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class EnrollmentModuleController {
    @FXML
    AnchorPane anchorPaneContentContainer;
    @FXML
    private Label errLblStartDate, errLblEndDate;
    @FXML
    private DatePicker dtStartDate, dtEndDate;

    private final CommonUtils c = new CommonUtils();
    private final InputValidationUtils iv = new InputValidationUtils();
    private final GlobalSchedule globalSchedule = new GlobalSchedule();

    @FXML
    private void initialize() {
        dtStartDate.setValue(globalSchedule.getDtStartDate());
        dtEndDate.setValue(globalSchedule.getDtEndDate());
    }

    public void onClickSetSchedule() {
        String strStartDate = c.getDateValue(dtStartDate);
        String strEndDate = c.getDateValue(dtEndDate);
        Boolean flagValid = true;

        // Validate Start Date
        if (!iv.isValidDate(strStartDate)) {
            errLblStartDate.setText("Enter a valid date");
            flagValid = false;
        } else {
            errLblStartDate.setText("");
        }

        // Validate End Date
        if (!iv.isValidDate(strEndDate)) {
            errLblEndDate.setText("Enter a valid date");
            flagValid = false;
        } else {
            errLblEndDate.setText("");
        }

        if (flagValid) {
            LocalDate startDate = LocalDate.parse(strStartDate);
            LocalDate endDate = LocalDate.parse(strEndDate);

            if (endDate.isBefore(startDate)) {
                errLblEndDate.setText("End date can't be earlier than start date");
                flagValid = false;
            } else {
                errLblEndDate.setText("");
            }
        }

        if (flagValid) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to set the inputted dates as the current enrollment schedule?");

            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                // Pass the dates to global variables or perform any other necessary actions
                globalSchedule.setDtStartDate(dtStartDate.getValue());
                globalSchedule.setDtEndDate(dtEndDate.getValue());

                System.out.println("START DATE: " + strStartDate);
                System.out.println("END DATE: " + strEndDate);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Enrollment schedule set successfully.");
                successAlert.showAndWait();
            }
        }
    }
    public void onClickBtnApprovalsConsole(){
        c.loadScreen("/FXML/admin_system/EnrollmentModule/ApprovalsConsole.fxml", anchorPaneContentContainer);
    }
    public void onClickBtnSubjectSpecificEnrollees(){
        c.loadScreen("/FXML/admin_system/EnrollmentModule/SubjectSpecificEnrollees.fxml", anchorPaneContentContainer);
    }
    public void onClickBtnEnrolleesMasterlist(){
        c.loadScreen("/FXML/admin_system/EnrollmentModule/EnrolleesMasterlist.fxml", anchorPaneContentContainer);
    }
}
