package com.OOP.plmares.controllers.admin_system;

import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;


public class SchoolYearModuleController {
    @FXML private TableView<TableModel.SchoolYearModuleInfo> tblVwSchoolYear;
    @FXML private TableColumn<TableModel.SchoolYearModuleInfo, String> colSchoolYear, colStatus, colAction, colEdit, colDelete;
    @FXML private AnchorPane anchorPaneTitleEdit, anchorPaneEditContainer;
    @FXML private Label lblSchoolYear;
    @FXML private TextField txtEditSchoolYear, txtAddSchoolYear;

    @FXML
    private void initialize() {
        // Set up columns
        colSchoolYear.setCellValueFactory(cellData -> cellData.getValue().strSyProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());
        colAction.setCellFactory(param -> createBtnActionCell());
        colEdit.setCellFactory(param -> createBtnEditCell());
        colDelete.setCellFactory(param -> createBtnDeleteCell());
        // Populate the TableView
        tblVwSchoolYear.getColumns().forEach(column -> column.setReorderable(false));
        tblVwSchoolYear.setFocusModel(null);
        updateTable();
    }

    private TableCell<TableModel.SchoolYearModuleInfo, String> createBtnActionCell() {
        return new TableCell<TableModel.SchoolYearModuleInfo, String>() {
            private final Button btnAction = new Button("Open");

            {
                btnAction.setStyle(
                        "-fx-background-radius: 15;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-color: #48b239;" +
                                "-fx-font-family: \"Raleway SemiBold\";" +
                                "-fx-font-size: 12;" +
                                "-fx-text-fill: white;" +
                                "-fx-cursor: hand;" +
                                "-fx-pref-height: 7;" +
                                "-fx-pref-width: 60"
                );

                btnAction.setOnAction(event -> {
                    TableModel.SchoolYearModuleInfo selectedData = getTableView().getItems().get(getIndex());

                    String strSy = selectedData.getStrSy();

                    System.out.println("School Year: " + strSy);

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Open School Year");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Opening \"" + strSy + "\" will close other school years.\nContinue?");

                    ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

                    if (result == ButtonType.OK) {
                        DBMethodsSySem.openSySemMethod("sy", "sy", strSy);
                    }
                    updateTable();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Access the SchoolYearModuleInfo object associated with the current row
                    TableModel.SchoolYearModuleInfo selectedData = getTableView().getItems().get(getIndex());

                    // Check if colRemark is equal to "Inactive"
                    if ("Inactive".equals(selectedData.getStrStatus())) {
                        // Enable the button
                        btnAction.setDisable(false);
                        setGraphic(btnAction);
                    } else {
                        // Disable the button
                        btnAction.setDisable(true);
                        setGraphic(btnAction);
                    }
                }
            }
        };
    }

    private TableCell<TableModel.SchoolYearModuleInfo, String> createBtnEditCell() {
        return new TableCell<TableModel.SchoolYearModuleInfo, String>() {
            private final Button btnAction = new Button("Edit");

            {
                btnAction.setStyle(
                        "-fx-background-radius: 15;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-color: #ff9900;" +
                                "-fx-font-family: \"Raleway SemiBold\";" +
                                "-fx-font-size: 12;" +
                                "-fx-text-fill: white;" +
                                "-fx-cursor: hand;" +
                                "-fx-pref-height: 7;" +
                                "-fx-pref-width: 60"
                );

                btnAction.setOnAction(event -> {
                    TableModel.SchoolYearModuleInfo selectedData = getTableView().getItems().get(getIndex());

                    String strSy = selectedData.getStrSy();

                    anchorPaneTitleEdit.setDisable(false);
                    anchorPaneEditContainer.setDisable(false);
                    lblSchoolYear.setText(strSy);
                    txtEditSchoolYear.setText(strSy);

                });

            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Access the SchoolYearModuleInfo object associated with the current row
                    TableModel.SchoolYearModuleInfo selectedData = getTableView().getItems().get(getIndex());
                    setGraphic(btnAction);
                }
            }
        };
    }

    private TableCell<TableModel.SchoolYearModuleInfo, String> createBtnDeleteCell() {
        return new TableCell<TableModel.SchoolYearModuleInfo, String>() {
            private final Button btnAction = new Button("Delete");

            {
                btnAction.setStyle(
                        "-fx-background-radius: 15;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-color: #ee5656;" +
                                "-fx-font-family: \"Raleway SemiBold\";" +
                                "-fx-font-size: 12;" +
                                "-fx-text-fill: white;" +
                                "-fx-cursor: hand;" +
                                "-fx-pref-height: 7;" +
                                "-fx-pref-width: 60"
                );

                btnAction.setOnAction(event -> {
                    TableModel.SchoolYearModuleInfo selectedData = getTableView().getItems().get(getIndex());

                    String strSy = selectedData.getStrSy();

                    // Prompt the user for confirmation
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Delete School Year");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Deleting \"" + strSy + "\" will also delete records with the same school year.\nContinue?");

                    ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

                    if (result == ButtonType.OK) {
                        DBMethodsSySem.deleteSchoolYear(strSy);
                        updateTable();
                    }
                });

            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Access the SchoolYearModuleInfo object associated with the current row
                    TableModel.SchoolYearModuleInfo selectedData = getTableView().getItems().get(getIndex());
                    setGraphic(btnAction);
                }
            }
        };
    }

    public void handleAddBtn() {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Add School Year");
        inputDialog.setHeaderText(null);
        inputDialog.setContentText("Enter School Year:");

        Optional<String> result = inputDialog.showAndWait();

        if (result.isPresent()) {
            String strSy = result.get();

            if (!validateSchoolYear(strSy)) {
                return;
            }

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Add School Year");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to add School Year: \"" + strSy + "\"?");

            ButtonType confirmationResult = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            if (confirmationResult == ButtonType.OK) {
                DBMethodsSySem.addSchoolYear(strSy);
            }

            updateTable();
            resetAllEditFields();
        }
    }

    public void handleEditSaveChanges() {
        if (!validateSchoolYear(txtEditSchoolYear.getText())) {
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Edit School Year");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Editing \"" + lblSchoolYear.getText() + "\" to \"" + txtEditSchoolYear.getText() + "\" may affect records with the same school year.\nContinue?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            DBMethodsSySem.editSchoolYear(lblSchoolYear.getText(), txtEditSchoolYear.getText());
        }

        resetAllEditFields();
        updateTable();
    }

    public void handleEditCancel() {
        resetAllEditFields();
    }

    private boolean validateSchoolYear(String strSchoolYear) {
        String schoolYearRegex = "^\\d{4}-\\d{4}$";

        if (!strSchoolYear.matches(schoolYearRegex)) {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Error");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Invalid School Year format. Please use the format YYYY-YYYY (e.g., 2020-2021).\nPlease recheck your inputs");
            warningAlert.showAndWait();
            return false;
        }

        return true;
    }

    private void resetAllEditFields() {
        anchorPaneTitleEdit.setDisable(true);
        anchorPaneEditContainer.setDisable(true);
        lblSchoolYear.setText("< >");
        txtEditSchoolYear.setText("");
        txtAddSchoolYear.setText("");
        updateTable();
    }

    private void updateTable() {
        ObservableList<TableModel.SchoolYearModuleInfo> updatedResults = DBMethodsSySem.getAllSchoolYear();
        TableUtils.populateTable(tblVwSchoolYear, updatedResults);
    }
}

