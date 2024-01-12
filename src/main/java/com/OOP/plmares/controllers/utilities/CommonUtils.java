package com.OOP.plmares.controllers.utilities;

import com.OOP.plmares.controllers.DataInitializable;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.swing.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;


public class CommonUtils {

    private final InputValidationUtils iv = new InputValidationUtils();
    private final InputValidationUtils.PasswordValidator pv = new InputValidationUtils.PasswordValidator();



    // ----- 1. dynamically adjust labels based on content
    public void adjustLabelSizeWithText(Label lblSelectedLabel, String strText) {
        // Create a Text node with the new text
        Text textNode = new Text(strText);

        // Set the font of the Text node to match the label
        textNode.setFont(lblSelectedLabel.getFont());

        // Measure the width and height of the text
        double textWidth = textNode.getBoundsInLocal().getWidth();
        double textHeight = textNode.getBoundsInLocal().getHeight();

        // Set the label's width and height based on the text content
        lblSelectedLabel.setMinWidth(textWidth);
        lblSelectedLabel.setMinHeight(textHeight);
        lblSelectedLabel.setText(strText);
    }

    // ----- 2. load an fxml file as a child node
    public void loadScreen(String fxmlFile, AnchorPane anchorPaneContentContainer) { // load an fxml file as a child node
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            anchorPaneContentContainer.getChildren().clear();
            anchorPaneContentContainer.getChildren().add(root);
            applyFadeInTransition(anchorPaneContentContainer, root);
        } catch (IOException e) {
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }


    // ------ 3. (Overload) load an fxml file as a child node with data passing
    public void loadScreen(String fxmlFile, AnchorPane anchorPaneContentContainer, Map<String, Object> data) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

            Parent root = fxmlLoader.load();

            Platform.runLater(() -> {
                anchorPaneContentContainer.getChildren().clear();
                anchorPaneContentContainer.getChildren().add(root);

                Object controller = fxmlLoader.getController();
                if (controller instanceof DataInitializable) {
                    ((DataInitializable) controller).initializeData(data);
                }

                applyFadeInTransition(anchorPaneContentContainer, root);
            });
        } catch (IOException e) {
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }


    // ------- 4. Fade in transition for scene change
    private void applyFadeInTransition(AnchorPane anchorPane, Parent newContent) {
        // Set the new content to be transparent and slightly below the anchorPane
        newContent.setOpacity(0.0);
        newContent.setTranslateY(anchorPane.getHeight() * 0.2);

        // Apply fade-in transition
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), newContent);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Apply translate-down and then translate-up transition
        TranslateTransition translateDown = new TranslateTransition(Duration.seconds(0.25), newContent);
        translateDown.setToY(anchorPane.getHeight() * 0.1);

        TranslateTransition translateUp = new TranslateTransition(Duration.seconds(0.25), newContent);
        translateUp.setToY(0);

        fadeIn.setOnFinished(event -> {
            // Clear the current content and add the new content
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(newContent);
        });

        // Start both translate transitions and then fade-in
        translateDown.play();
        translateDown.setOnFinished(e -> {
            translateUp.play();
            fadeIn.play();
        });
    }


    // ------ 5.) Center a stage when loading with fade in transition

    public static void fadeInAndMoveUpAndCenterStage(Stage stage, Parent root) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Set the initial state
        root.setOpacity(0.0);
        root.setTranslateY(screenBounds.getHeight() * 0.2);

        // Center the root initially
        centerStage(stage);

        // Apply fade-in transition
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Apply translate-up transition
        TranslateTransition translateUp = new TranslateTransition(Duration.seconds(0.3), root);
        translateUp.setToY(0);

        // Combine both transitions into a ParallelTransition
        ParallelTransition parallelTransition = new ParallelTransition(fadeIn, translateUp);

        parallelTransition.setOnFinished(event -> {
            // Clear the current content and add the new content
            stage.getScene().setRoot(root);
        });

        // Start the parallel transition
        parallelTransition.play();
    }

    // ------ 5.5) centers the stage. is used in the fadein transition
    private static void centerStage(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }


    // ------- 6. Get selected item of a combobox to avoid null pointer exception
    public String getSelectedItemOrEmptyCmbBox(ComboBox<String> comboBox) {
        if (comboBox.getSelectionModel().getSelectedItem() != null
                && !comboBox.getSelectionModel().getSelectedItem().equals("--")) {
            return comboBox.getSelectionModel().getSelectedItem().toString();
        }
        return "";
    }

    // ------ 6. Get selected item of a Schedule combobox to avoid null pointer exception and return search filter equivalent

    public String getSelectedItemOrEmptyScheduleCmbBox(ComboBox<String> comboBox) {
        String selectedItem = comboBox.getSelectionModel().getSelectedItem();

        if (selectedItem != null && !selectedItem.equals("--")) {
            switch (selectedItem) {
                case "Monday":
                    return "M ";
                case "Tuesday":
                    return "T ";
                case "Wednesday":
                    return "W ";
                case "Thursday":
                    return "Th ";
                case "Friday":
                    return "F ";
                case "Saturday":
                    return "S ";
                case "Sunday":
                    return "Su ";
                default:
                    return "";
            }
        }

        return "";
    }


    // ------ 7. return database-appropriate values for comboboxes

    public static String mapComboBoxValue(String comboBoxValue, String type) {
        if ("Gender".equalsIgnoreCase(type)) {
            return "Female".equalsIgnoreCase(comboBoxValue) ? "F" : "Male".equalsIgnoreCase(comboBoxValue) ? "M" : null;
        } else if ("Status".equalsIgnoreCase(type)) {
            return "Active".equalsIgnoreCase(comboBoxValue) ? "A" : "Inactive".equalsIgnoreCase(comboBoxValue) ? "I" : null;
        } else if ("RegistrationStatus".equalsIgnoreCase(type)) {
            return "Regular".equalsIgnoreCase(comboBoxValue) ? "R" : "Irregular".equalsIgnoreCase(comboBoxValue) ? "I" : null;
        } else if ("Day".equalsIgnoreCase(type)) {
            switch (comboBoxValue) {
                case "Monday":
                    return "M";
                case "Tuesday":
                    return "T";
                case "Wednesday":
                    return "W";
                case "Thursday":
                    return "Th";
                case "Friday":
                    return "F";
                case "Saturday":
                    return "S";
                case "Sunday":
                    return "Su";
                default:
                    return "";
            }
        } else {
            return null; // Handle other types if needed
        }
    }



    // ------ 7. plm email generator for students
    public static String generatePLMEmail(String strLastName, String strFirstName, String strStudentNumber) {
        // Extract initials from the first name
        String initials = strFirstName.substring(0, 1);
        int spaceIndex = strFirstName.indexOf(' ');
        if (spaceIndex != -1) {
            initials += strFirstName.substring(spaceIndex + 1, spaceIndex + 2);
        }

        // Get the first 4 characters of the student number
        String studentNumberPrefix = strStudentNumber.substring(0, Math.min(4, strStudentNumber.length()));

        // Combine components to form the PLM email
        return initials.toLowerCase() + strLastName.toLowerCase() + studentNumberPrefix + "@plm.edu.ph";
    }

    // ------ 8. plm email generator for faculty
    public static String generatePLMEmailFaculty(String strLastName, String strFirstName) {
        // Extract initials from the first name
        String initials = strFirstName.substring(0, 1);
        int spaceIndex = strFirstName.indexOf(' ');
        if (spaceIndex != -1) {
            initials += strFirstName.substring(spaceIndex + 1, spaceIndex + 2);
        }

        // Combine components to form the PLM email
        return initials.toLowerCase() + strLastName.toLowerCase() + "@plm.edu.ph";
    }

    // ------ 9. get value for date pickers, returns an empty string if null
    public String getDateValue(DatePicker datePicker) {
        return datePicker.getValue() != null ? datePicker.getValue().toString() : "";
    }

    // ------ 10. return day name from abbreviation
    public static String getDayFromAbbreviation(String strAbbreviatedDay) {
        switch (strAbbreviatedDay) {
            case "M":
                return "Monday";
            case "T":
                return "Tuesday";
            case "W":
                return "Wednesday";
            case "Th":
                return "Thursday";
            case "F":
                return "Friday";
            case "S":
                return "Saturday";
            case "Su":
                return "Sunday";
            default:
                return "Invalid Day";
        }
    }

    // ------ 11. extract employee info with format (E001) Lastname, Fullname
    public static Pair<String, String> extractEmployeeInfo(String input) {

        if(input == null)
            return new Pair<>("", "");

        String strEmployeeNumber = null;
        String strFullName = null;

        // Check if the input string contains "(E001)"
        int endIndex = input.indexOf(')');
        if (endIndex != -1) {
            // Extract employee number
            strEmployeeNumber = input.substring(input.indexOf('(') + 1, endIndex);

            // Extract full name
            strFullName = input.substring(endIndex + 1).trim();
        }

        return new Pair<>(strEmployeeNumber, strFullName);
    }


    // ------ 12. extract individual time parts for editing
    public static String[] extractTimeParts(String strTime) {
        String[] timeParts = new String[4];

        // Split the input string based on the dash ("-")
        String[] parts = strTime.split("-");
        if (parts.length == 2) {
            // Extract start time
            String[] startTime = parts[0].split(":");
            if (startTime.length == 2) {
                timeParts[0] = startTime[0].trim(); // Start Hour
                timeParts[1] = startTime[1].trim(); // Start Minute
            }

            // Extract end time
            String[] endTime = parts[1].split(":");
            if (endTime.length == 2) {
                timeParts[2] = endTime[0].trim();   // End Hour
                timeParts[3] = endTime[1].trim();   // End Minute
            }
        }

        return timeParts;
    }

    // ------ 13.) password generator for new inputs
    private static final String strUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String strLowerCase= "abcdefghijklmnopqrstuvwxyz";
    private static final String strDigits = "0123456789";
    private static final String strSpecialCharacters = "!@#$%^&*()?";

    public static String generateRandomPassword() {
        int intPasswordLength = 9;
        String strAllCharacters = strUpperCase + strLowerCase + strDigits + strSpecialCharacters;
        SecureRandom randomGenerator  = new SecureRandom();
        StringBuilder strPassword = new StringBuilder();

        for (int i = 0; i < intPasswordLength; i++) {
            int intRandomIndex = randomGenerator.nextInt(strAllCharacters.length());
            strPassword.append(strAllCharacters.charAt(intRandomIndex));
        }

        return strPassword.toString();
    }


    // ------- 14.) toggle password visibility on click of label
    public void togglePasswordVisibility(Label showLabel, TextField textField, PasswordField passField) {
        String currentState = showLabel.getText();

        if (currentState.equals("Show")) {
            textField.setText(passField.getText());
            textField.setVisible(true);
            passField.setVisible(false);
            showLabel.setText("Hide");
        } else {
            passField.setText(textField.getText());
            textField.setVisible(false);
            passField.setVisible(true);
            showLabel.setText("Show");
        }
    }

    // ------- 15.) get password value based on text visibility
    public String getPassword(TextField textField, PasswordField passField) {
        return passField.isVisible() ? passField.getText() : textField.getText();
    }

    // ------- 16.) validate the three passwords (Current, new, confirm)
    public boolean validatePasswords(String strUserID, String currentPassword, String newPassword, String confirmNewPassword,
                                     Label errLblCurrentPass, Label errLblNewPass, Label errLblConfirmPass) {
        boolean flagValid = true; // Assume all inputs are valid initially

        // Validate current password
        if (!iv.isCorrectCurrentPassword(strUserID, currentPassword) || currentPassword.isEmpty()) {
            errLblCurrentPass.setText("Incorrect password");
            flagValid = false;
            System.out.println("Current password: " + currentPassword + "\n is valid: " + flagValid);
        } else {
            errLblCurrentPass.setText("");
        }

        // Validate new password
        if (!pv.isValidPassword(newPassword)) {
            errLblNewPass.setText("Invalid new password");
            flagValid = false;
        } else {
            errLblNewPass.setText("");
        }

        // Validate confirm new password
        if (!confirmNewPassword.equals(newPassword)) {
            errLblConfirmPass.setText("Doesn't match new password");
            flagValid = false;
        } else {
            errLblConfirmPass.setText("");
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


    // ------- 17.) Format date to string
    public String formatDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, uuuu");
        return date.format(formatter);
    }


}
