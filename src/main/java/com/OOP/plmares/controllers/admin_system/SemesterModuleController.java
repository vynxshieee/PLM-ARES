package com.OOP.plmares.controllers.admin_system;

import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class SemesterModuleController {
    @FXML private TableView<TableModel.SemesterModuleInfo> tblVwSemester;
    @FXML private TableColumn<TableModel.SemesterModuleInfo, String> colSemester, colStatus, colAction, colEdit, colDelete;
    @FXML private AnchorPane anchorPaneTitleEdit, anchorPaneEditContainer;
    @FXML private Label lblSemester;
    @FXML private TextField txtAddSemester, txtEditSemester;

    @FXML
    private void initialize() {
        // Set up columns
        colSemester.setCellValueFactory(cellData -> cellData.getValue().strSemesterProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());
        colAction.setCellFactory(param -> createBtnActionCell());
        colEdit.setCellFactory(param -> createBtnEditCell());
        colDelete.setCellFactory(param -> createBtnDeleteCell());
        // Populate the TableView
        tblVwSemester.setFocusModel(null);
        tblVwSemester.getColumns().forEach(column -> column.setReorderable(false));
        updateTable();
    }

    private TableCell<TableModel.SemesterModuleInfo, String> createBtnActionCell() {
        return new TableCell<TableModel.SemesterModuleInfo, String>() {
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
                    TableModel.SemesterModuleInfo selectedData = getTableView().getItems().get(getIndex());

                    String strSemester = selectedData.getStrSemester();

                    System.out.println("Semester: " + strSemester);

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Open Semester");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Opening \"" + strSemester + "\" will close other semesters.\nContinue?");

                    ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

                    if (result == ButtonType.OK) {
                        DBMethodsSySem.openSySemMethod("semester", "semester", strSemester);
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
                    // Access the SemesterModuleInfo object associated with the current row
                    TableModel.SemesterModuleInfo selectedData = getTableView().getItems().get(getIndex());

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

    private TableCell<TableModel.SemesterModuleInfo, String> createBtnEditCell() {
        return new TableCell<TableModel.SemesterModuleInfo, String>() {
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
                    TableModel.SemesterModuleInfo selectedData = getTableView().getItems().get(getIndex());

                    String strSemester = selectedData.getStrSemester();

                    anchorPaneTitleEdit.setDisable(false);
                    anchorPaneEditContainer.setDisable(false);
                    lblSemester.setText(strSemester);
                    txtEditSemester.setText(strSemester);

                });

            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Access the SemesterModuleInfo object associated with the current row
                    TableModel.SemesterModuleInfo selectedData = getTableView().getItems().get(getIndex());
                    setGraphic(btnAction);
                }
            }
        };
    }

    private TableCell<TableModel.SemesterModuleInfo, String> createBtnDeleteCell() {
        return new TableCell<TableModel.SemesterModuleInfo, String>() {
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
                    TableModel.SemesterModuleInfo selectedData = getTableView().getItems().get(getIndex());

                    String strSemester = selectedData.getStrSemester();

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Delete Semester");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Deleting \"" + strSemester + "\" will also delete records with the same semester.\nContinue?");

                    ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

                    if (result == ButtonType.OK) {
                        DBMethodsSySem.deleteSemester(strSemester);
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
                    // Access the SemesterModuleInfo object associated with the current row
                    TableModel.SemesterModuleInfo selectedData = getTableView().getItems().get(getIndex());
                    setGraphic(btnAction);
                }
            }
        };
    }

    public void handleAddBtn() {
        String strSemester = txtAddSemester.getText();
        if(!validateSemester(strSemester))
            return;
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Add Semester");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to add semester: \"" + strSemester + "\"?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            DBMethodsSySem.addSemesterWithStrSy(strSemester);
        }
        updateTable();
        resetAllEditFields();
    }

    public void handleEditSaveChanges(){
        if(!validateSemester(txtEditSemester.getText()))
            return;
        // Prompt the user for confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Edit Semester");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Editing \"" + lblSemester.getText() + "\" may affect records with the same semester.\nContinue?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            System.out.println("semester TO BE EDITED " + lblSemester.getText());
            System.out.println("semester edit text: " + txtEditSemester.getText());
            DBMethodsSySem.editSemester(lblSemester.getText(), txtEditSemester.getText());
        }

        resetAllEditFields();
        updateTable();
    }

    private boolean validateSemester(String strSemester) {
        if (strSemester.length() != 1 || !Character.isLetterOrDigit(strSemester.charAt(0))) {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Error");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Semester should only consist of one character, which can be a numeral or a letter.\nPlease recheck your inputs");
            warningAlert.showAndWait();
            return false;
        }
        return true;
    }


    public void handleEditCancel(){
        anchorPaneTitleEdit.setDisable(true);
        anchorPaneEditContainer.setDisable(true);
        lblSemester.setText("< >");
    }

    private void resetAllEditFields() {
        anchorPaneTitleEdit.setDisable(true);
        anchorPaneEditContainer.setDisable(true);
        lblSemester.setText("< >");
        txtAddSemester.setText("");
        txtEditSemester.setText("");
        updateTable();
    }

    private void updateTable() {
        ObservableList<TableModel.SemesterModuleInfo> updatedResults = DBMethodsSySem.getAllSemester();
        TableUtils.populateTable(tblVwSemester, updatedResults);
    }
}

