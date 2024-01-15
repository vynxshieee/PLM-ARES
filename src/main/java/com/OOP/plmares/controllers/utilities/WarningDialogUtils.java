package com.OOP.plmares.controllers.utilities;

import javafx.scene.control.Alert;


public class WarningDialogUtils {
        public static void showDuplicateEntryWarning() {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Duplicate Entry");
            alert.setHeaderText(null);
            alert.setContentText("Error: Duplicate entry. A record for this already exists.");

            alert.showAndWait();
        }
        public static void showGenericErrorWarning() {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred.");

            alert.showAndWait();
        }
        public static void showSuccessDialog() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Action completed successfully.");

            alert.showAndWait();
        }
}
