package com.OOP.plmares.controllers.admin_system.SubjectModuleControllers;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SubjectSchedulingController {
    @FXML AnchorPane anchorPaneContentContainer;
    @FXML private Label errLblMaxCount;
    @FXML private TextField txtMaxCount;
    CommonUtils c = new CommonUtils();

    public void onClickModifyClassCount() {
        // Input validation for txtMaxCount. Only allow numerals ranging from 1-100
        String maxCountText = txtMaxCount.getText();
        if (!maxCountText.matches("\\d{1,3}") || Integer.parseInt(maxCountText) < 1 || Integer.parseInt(maxCountText) > 100) {
            errLblMaxCount.setText("Invalid input.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to modify the maximum class count to " + maxCountText + "?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            try {
                // Parse the max count
                int intMaxCount = Integer.parseInt(maxCountText);

                // Replace the "20" in your SQL query with the intMaxCount
                String modifiedQuery = "CREATE OR REPLACE VIEW vwSubjectSectionClassCount AS\n" +
                        "SELECT \n" +
                        "    sss.sy,\n" +
                        "    sss.semester,\n" +
                        "    sss.subject_code,\n" +
                        "    sss.college_code,\n" +
                        "    sss.block_no,\n" +
                        "    sss.day,\n" +
                        "    sss.time,\n" +
                        "    sss.type,\n" +
                        "    sss.sequence_no,\n" +
                        "    sss.room,\n" +
                        "    sss.faculty_id,\n" +
                        "    COALESCE(COUNT(DISTINCT e.student_no), 0) AS class_count,\n" +
                        "    COALESCE(" + intMaxCount + " - COUNT(DISTINCT e.student_no), " + intMaxCount + ") AS slots\n" +
                        "FROM vwSySemSubjectSection sss\n" +
                        "LEFT JOIN enrollment e\n" +
                        "    ON sss.sy = e.sy\n" +
                        "    AND sss.semester = e.semester\n" +
                        "    AND sss.subject_code = e.subject_code\n" +
                        "    AND sss.college_code = e.college_code\n" +
                        "    AND sss.block_no = e.block_no\n" +
                        "    AND COALESCE(e.status, '') = 'approved'\n" +
                        "GROUP BY \n" +
                        "    sss.sy,\n" +
                        "    sss.semester,\n" +
                        "    sss.subject_code,\n" +
                        "    sss.college_code,\n" +
                        "    sss.block_no,\n" +
                        "    sss.day,\n" +
                        "    sss.time,\n" +
                        "    sss.type,\n" +
                        "    sss.sequence_no,\n" +
                        "    sss.room,\n" +
                        "    sss.faculty_id;";

                // Execute the modified query
                DBCommonMethods.executeQuery(modifiedQuery);

                // Display a success message (you can replace this with your actual UI update)
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Maximum class count modified successfully.");

                successAlert.showAndWait();

                // Clear error label
                errLblMaxCount.setText("");
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Handle the exception according to your application's requirements
            }
        } else {
            // User canceled the operation, clear error label
            errLblMaxCount.setText("");
        }
    }
    public void onClickBtnSubjectModule(){
        c.loadScreen("/FXML/admin_system/SubjectSchedulingModule/SubjectModule.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnSchedulingModule(){
        c.loadScreen("/FXML/admin_system/SubjectSchedulingModule/ScheduleModule.fxml", anchorPaneContentContainer);
    }
}
