package com.OOP.plmares.controllers.admin_system;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsClasslistMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsGradeEntryMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.util.Optional;
import java.util.logging.Logger;

public class GradeEntryController {

    @FXML private TableView<TableModel.GradeEntryStudent> tblVwGradeEntry;
    @FXML private TableColumn<TableModel.GradeEntryStudent, String> colStudentNo, colFullName, colRemark;
    @FXML private TableColumn<TableModel.GradeEntryStudent, Double> colGrade;
    @FXML private ComboBox<String> cmbSchoolYear, cmbSemester;
    @FXML private TextField txtSubjectCode, txtSection;
    @FXML private Label lblSchoolYear, lblSemester, lblSubjectCode, lblFaculty, lblSection, lblDescription, lblStudentCount;
    @FXML private Button btnPrintPreview, btnRevert, btnSave;
    private static final Logger LOGGER = Logger.getLogger(GradeEntryController.class.getName());

    private String strSy, strSemester;

    @FXML
    private void initialize() {
        // Set up active sy and sem
        strSy = DBMethodsSySem.getActiveSy();
        strSemester = DBMethodsSySem.getActiveSem();
        cmbSchoolYear.setValue(strSy);
        cmbSemester.setValue(strSemester);

        DBCommonMethods.populateComboBox(cmbSchoolYear, "sy", "sy");
        cmbSchoolYear.getItems().remove("--");

        DBCommonMethods.populateComboBox(cmbSemester, "semester", "semester");
        cmbSemester.getItems().remove("--");

        // Set up columns
        tblVwGradeEntry.getColumns().forEach(column -> column.setReorderable(false));
        tblVwGradeEntry.setFocusModel(null);
        colStudentNo.setCellValueFactory(cellData -> cellData.getValue().strStudentNoProperty());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().strFullNameProperty());
        colRemark.setCellValueFactory(cellData -> cellData.getValue().strRemarkProperty());
        setupRemarkColumn();

        colGrade.setCellValueFactory(cellData -> cellData.getValue().dblGradeProperty().asObject());
        setupGradeColumn();

        colGrade.setEditable(false);
    }


    private int updateTableWithSearchResults(String strSy, String strSemester, String strSubjectCode, String strSection) {
        ObservableList<TableModel.GradeEntryStudent> gradeRecordList =
                DBMethodsGradeEntryMod.getGradeRecords(strSy, strSemester,  strSubjectCode, strSection);

        TableUtils.populateTable(tblVwGradeEntry, gradeRecordList);

        return gradeRecordList.size();
    }

    private void setupGradeColumn() {
        colGrade.setCellFactory(column -> {
            return new TableCell<TableModel.GradeEntryStudent, Double>() {
                private final ComboBox<Double> comboBox = new ComboBox<>();

                {
                    // Set the values for the ComboBox
                    comboBox.getItems().addAll(0.0, 1.00, 1.25, 1.50, 1.75, 2.00, 2.25, 2.50, 2.75, 3.00, 5.00);

                    // Add event handlers to commit the edit on Enter and cancel on Escape
                    comboBox.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.ENTER) {
                            commitEdit(comboBox.getValue());
                        } else if (event.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    });

                    // Set converter for double values
                    comboBox.valueProperty().bindBidirectional(itemProperty());

                    // Customize the ComboBox style
                    comboBox.getStyleClass().add("combo-box");
                    comboBox.setFocusTraversable(false);
                }



                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        TableModel.GradeEntryStudent gradeEntry = (TableModel.GradeEntryStudent) getTableRow().getItem();

                        // Create a new ComboBox for each row
                        ComboBox<Double> comboBox = new ComboBox<>();
                        comboBox.getItems().addAll(0.0, 1.00, 1.25, 1.50, 1.75, 2.00, 2.25, 2.50, 2.75, 3.00, 5.00);

                        // Set the initial value from getGradeRecords
                        comboBox.setValue(item);
                        setGraphic(comboBox);
                        comboBox.setFocusTraversable(false);
                        // Add listener to the ComboBox valueProperty
                        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue != null) {
                                // Update colRemark based on the selected value in the ComboBox
                                if (Double.compare(newValue, 5.0) == 0) {
                                    gradeEntry.setStrRemark("FAILED");
                                } else if (Double.compare(newValue, 0.0) == 0) {
                                    gradeEntry.setStrRemark("INC");
                                } else {
                                    gradeEntry.setStrRemark("PASSED");
                                }

                                // Update the dblGradeProperty in the GradeEntryStudent model
                                gradeEntry.dblGradeProperty().set(newValue);
                            }
                        });

                        // Add event handlers to commit the edit on Enter and cancel on Escape
                        comboBox.setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.ENTER) {
                                commitEdit(comboBox.getValue());
                            } else if (event.getCode() == KeyCode.ESCAPE) {
                                cancelEdit();
                            }
                        });
                    }

                }



                @Override
                public void startEdit() {
                    super.startEdit();
                    comboBox.setEditable(true);
                }

                @Override
                public void cancelEdit() {
                    super.cancelEdit();
                    comboBox.setEditable(false);
                }

                @Override
                public void commitEdit(Double newValue) {
                    super.commitEdit(newValue);
                    comboBox.setEditable(false);
                }
            };
        });
    }

    private void setupRemarkColumn() {
        colRemark.setCellValueFactory(cellData -> cellData.getValue().strRemarkProperty());

        colRemark.setCellFactory(column -> {
            return new TableCell<TableModel.GradeEntryStudent, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); // Clear any previous styling
                    } else {
                        setText(item);

                        // Capitalize the remark value
                        String capitalizedRemark = item.toUpperCase();

                        // Set color codes based on the capitalized remark value
                        switch (capitalizedRemark) {
                            case "FAILED":
                                setStyle("-fx-text-fill: red;");
                                break;
                            case "INC":
                                setStyle("-fx-text-fill: #0051ff;");
                                break;
                            case "PASSED":
                                setStyle("-fx-text-fill: green;");
                                break;
                            default:
                                setStyle(""); // Default styling
                        }
                    }
                }
            };
        });
    }

    public void handleLoadData(){
        String strSy = cmbSchoolYear.getValue().toString();
        String strSemester = cmbSemester.getValue().toString();
        String strSubjectCode = txtSubjectCode.getText();
        String strSection = txtSection.getText();


        int intStudentCount = updateTableWithSearchResults(strSy, strSemester, strSubjectCode, strSection);

        System.out.println("Result count: " + intStudentCount);

        if(intStudentCount != 0){
            TableModel.SubjectSectionClasslist gradeRecordList =
                    DBMethodsClasslistMod.getClassDetails(strSy, strSemester,  strSubjectCode, strSection);

            lblSchoolYear.setText(gradeRecordList.getStrSy());
            lblSemester.setText(gradeRecordList.getStrSemester());
            lblSubjectCode.setText(gradeRecordList.getSubjectCode());
            lblDescription.setText(gradeRecordList.getDescription());
            lblFaculty.setText(gradeRecordList.getFaculty());
            lblSection.setText(gradeRecordList.getSection());
            lblDescription.setText(gradeRecordList.getDescription());

            lblStudentCount.setText(intStudentCount + "");
            btnRevert.setDisable(false);
            btnSave.setDisable(false);

        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("No Matching Records");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("No matching records found!");
            errorAlert.showAndWait();
        }
    }

    public void handleClearAll(){
        updateTableWithSearchResults("", "", "", "");

        txtSection.setText("");
        txtSubjectCode.setText("");
        cmbSchoolYear.setValue(strSy);
        cmbSemester.setValue(strSemester);

        lblSchoolYear.setText("< >");
        lblSemester.setText("< >");
        lblSubjectCode.setText("< >");
        lblDescription.setText("< >");
        lblFaculty.setText("< >");
        lblSection.setText("< >");
        lblDescription.setText("< >");

        lblStudentCount.setText("< >");

        btnRevert.setDisable(true);
        btnSave.setDisable(true);
    }

    public void handleRevertToOriginal() {
        String strSy = lblSchoolYear.getText();
        String strSemester = lblSemester.getText();
        String strSubjectCode = lblSubjectCode.getText();
        String strSection = lblSection.getText();

        // Confirm with a JavaFX Alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to revert to the original values?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed, update the table with original values
            updateTableWithSearchResults(strSy, strSemester, strSubjectCode, strSection);
        } else {
            System.out.println("User canceled the revert operation.");
        }
    }



    public void handleSaveChanges() {
        String strSy = lblSchoolYear.getText();
        String strSemester = lblSemester.getText();
        String strSubjectCode = lblSubjectCode.getText();
        String strSection = lblSection.getText();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to commit changes for " + strSubjectCode + " [" + strSection + "]?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed, update the grades
            ObservableList<TableModel.GradeEntryStudent> gradeRecords = tblVwGradeEntry.getItems();

            for (TableModel.GradeEntryStudent gradeEntry : gradeRecords) {
                String strStudentNo = gradeEntry.getStrStudentNo();
                Double dblGrade = gradeEntry.getDblGrade();

                DBMethodsGradeEntryMod.updateGrades(strSy, strSemester, strStudentNo, strSubjectCode, strSection, dblGrade);
            }
        }
    }


}
