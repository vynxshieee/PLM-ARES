package com.OOP.plmares.controllers.student_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class CurriculumsCatalogController {
    @FXML private WebView webVwCurriculum;
    private WebEngine webEngine;
    @FXML private Label lblLoading, lblCourseTitle;
    @FXML private ComboBox<String> cmbCourse;
    @FXML
    private void initialize() {
        // Initialize the WebEngine
        webEngine = webVwCurriculum.getEngine();

        // Add a ChangeListener to the Worker state property
        webEngine.getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        // Hide the loading label when the page is fully loaded
                        lblLoading.setVisible(false);
                    } else {
                        // Show the loading label for other states
                        lblLoading.setVisible(true);
                        if (newValue == Worker.State.RUNNING) {
                            // Set loading message or text based on your requirements
                            lblLoading.setText("Loading...");
                        }
                    }
                }
        );

        DBCommonMethods.populateComboBox(cmbCourse, "vwCourseCodeDescriptionCombined", "course");
        cmbCourse.getItems().remove("--");
    }

    @FXML
    public void onBtnClick() {
        String strCourseValue = cmbCourse.getValue(); // Assuming that cmbCourse is a ComboBox<String>
        String strTitle = "";

        if (strCourseValue != null) {
            String url;

            // Load this if the value of the combobox string value starts with "(BSCS-IT)"
            if (strCourseValue.startsWith("(BSCS-IT)")) {
                url = "https://drive.google.com/file/d/1t2ckjYQhOcporAp_FXHFLESli_Iluq-u/view?usp=drive_link";
                strTitle = strCourseValue;
            }
            // Load this if the value of the combobox string value starts with "(BSCS-CS)"
            else if (strCourseValue.startsWith("(BSCS-CS)")) {
                url = "https://drive.google.com/file/d/1ju0U_zk2LJ_VgIfxWxX1J0SLRczu3pd8/view?usp=drive_link";
                strTitle = strCourseValue;
            } else {
                // If the value is neither of the two, show an error warning
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Error");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("No available curriculum file for the selected course.");
                warningAlert.showAndWait();
                return;
            }

            // Load the specified URL and title based on the course value
            webEngine.load(url);
            lblCourseTitle.setText(strTitle);
        } else {
            // Handle the case where no course is selected
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Error");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Please select a course.");
            warningAlert.showAndWait();
        }
    }
}
