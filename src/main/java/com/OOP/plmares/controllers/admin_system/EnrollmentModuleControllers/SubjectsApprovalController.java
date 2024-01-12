package com.OOP.plmares.controllers.admin_system.EnrollmentModuleControllers;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEnrollmentMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.MailSenderUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.OOP.plmares.controllers.tableUtils.DBCommonMethods.fetchNameData;


public class SubjectsApprovalController implements DataInitializable {

    @FXML private AnchorPane anchorPaneContentContainer, btnApproveAll, anchorPaneGreenStatus, anchorPaneRedStatus;
    @FXML private TableView<TableModel.ApprovalSubjectInfo> tblVwSubjectsApproval;
    @FXML private TableColumn<TableModel.ApprovalSubjectInfo, String> colSubjectCode, colDescription, colSection, colDayTimeModality, colCollege, colStatus, colAction;
    @FXML private TableColumn<TableModel.ApprovalSubjectInfo, Integer> colUnits, colSlots, colQueue;
    @FXML private Label lblFullName, lblSyTitle, lblSemesterTitle, lblStudentNo, lblUnitsCount, lblTotalUnitsAllowed;

    private String strSy = "", strSemester = "", strStudentNo = "";
    private CommonUtils c = new CommonUtils();
    private TableUtils t = new TableUtils();
    private boolean isInitialized = false;
    private int intTotalUnits = 0, intTotalUnitsAllowed = 27;
    @Override
    public void initializeData(Map<String, Object> data) {
        strStudentNo = (String) data.get("studentNo");
        lblStudentNo.setText((String) data.get("studentNo"));
        lblFullName.setText((String) data.get("fullName"));
        if (!isInitialized) {
            isInitialized = true;
            initialize();
        }
    }


    @FXML
    private void initialize() {
        if (isInitialized) {
            // Set up active sy and sem
            strSy = DBMethodsSySem.getActiveSy();
            strSemester = DBMethodsSySem.getActiveSem();
            lblSyTitle.setText(strSy);
            lblSemesterTitle.setText(strSemester);

            // set max unit count
            lblTotalUnitsAllowed.setText("/" + intTotalUnitsAllowed);

            // Set up columns
            colSubjectCode.setCellValueFactory(cellData -> cellData.getValue().subjectCodeProperty());

            t.setupTextWrapping(colDescription);
            colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

            colSection.setCellValueFactory(cellData -> cellData.getValue().sectionProperty());

            t.setupTextWrappingWithLineBreaks(colDayTimeModality);
            colDayTimeModality.setCellValueFactory(cellData -> cellData.getValue().dayTimeModalityProperty());

            colUnits.setCellValueFactory(cellData -> cellData.getValue().unitsProperty().asObject());

            colCollege.setCellValueFactory(cellData -> cellData.getValue().collegeProperty());

            colSlots.setCellValueFactory(cellData -> cellData.getValue().slotsProperty().asObject());

            colQueue.setCellValueFactory(cellData -> cellData.getValue().queueProperty().asObject());

            colStatus.setCellValueFactory(cellData -> cellData.getValue().remarkProperty());
            colorCodeStatusColumn();

            colAction.setCellFactory(param -> createBtnActionCell());

            // Populate the TableView
            tblVwSubjectsApproval.getColumns().forEach(column -> column.setReorderable(false));
            tblVwSubjectsApproval.setFocusModel(null);
            updateTableWithSearchResults(strStudentNo);
        }
    }

    private TableCell<TableModel.ApprovalSubjectInfo, String> createBtnActionCell() {
        return new TableCell<TableModel.ApprovalSubjectInfo, String>() {
            private final Button btnAction = new Button("Remove");

            {
                btnAction.setStyle(
                        "-fx-background-radius: 15;" +
                                "-fx-border-radius: 15;" +
                                "-fx-background-color: #e84848;" +
                                "-fx-font-family: \"Raleway SemiBold\";" +
                                "-fx-font-size: 12;" +
                                "-fx-text-fill: white;" +
                                "-fx-cursor: hand;" +
                                "-fx-pref-height: 7"
                );

                btnAction.setOnAction(event -> {
                    TableModel.ApprovalSubjectInfo selectedData = getTableView().getItems().get(getIndex());

                    String strSubjectCode = selectedData.getSubjectCode();
                    String strSection = selectedData.getSection();

                    System.out.println("Subject Code: " + strSubjectCode);
                    System.out.println("Section: " + strSection);

                    if(DBMethodsEnrollmentMod.removePendingSubjectApplication(strSy, strSemester, strStudentNo, strSubjectCode, strSection)){
                        updateTableWithSearchResults(strStudentNo);
                        countTotalUnits();
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
                    TableModel.ApprovalSubjectInfo selectedData = getTableView().getItems().get(getIndex());
                    setGraphic(btnAction);

                    // Check if colstatus is equal to "pending"
                    if ("pending".equals(selectedData.getRemark().toLowerCase())) {
                        btnAction.setDisable(false);
                        setGraphic(btnAction);
                    } else {
                        btnAction.setDisable(true);
                        setGraphic(btnAction);

                    }
                }
            }
        };
    }


    private void colorCodeStatusColumn() {
        colStatus.setCellFactory(column -> {
            return new TableCell<TableModel.ApprovalSubjectInfo, String>() {
                private final Text text = new Text();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(text);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(column.widthProperty());
                    text.setTextAlignment(TextAlignment.CENTER);
                    text.setLineSpacing(1);
                    text.setStyle("-fx-font-uppercase: true;");
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText(null);
                        setGraphic(null);
                    } else {
                        // Apply color coding based on the remark
                        if ("pending".equals(item)) {
                            text.setFill(Color.valueOf("#cc7c04"));
                        } else {
                            text.setFill(Color.valueOf("#288a07"));
                        }
                        text.setText(item);
                        setGraphic(text);

                    }
                }
            };
        });
    }

    private int updateTableWithSearchResults(String stdNumber) {
        ObservableList<TableModel.ApprovalSubjectInfo> subjectApprovalList =
                DBMethodsEnrollmentMod.getSubjectApprovalsInfo(strSy, strSemester, stdNumber);

        TableUtils.populateTable(tblVwSubjectsApproval, subjectApprovalList);

        // Check if there is at least one "pending" status
        Boolean isPendingExists = subjectApprovalList.stream().anyMatch(info -> "pending".equalsIgnoreCase(info.getRemark()));

        System.out.println("Size of subjectApprovalList: " + subjectApprovalList.size());
        System.out.println("Is pending exists? " + isPendingExists);

        // Update button state based on the condition
        if(btnApproveAll != null)
            btnApproveAll.setDisable(countTotalUnits() >= intTotalUnitsAllowed || !isPendingExists);

        // set visibility of status design
        anchorPaneGreenStatus.setVisible(!isPendingExists);
        anchorPaneRedStatus.setVisible(isPendingExists);

        lblUnitsCount.setText(countTotalUnits() + "");

        return subjectApprovalList.size();
    }

    private int countTotalUnits() {
        intTotalUnits = 0;
        ObservableList<TableModel.ApprovalSubjectInfo> subjectApprovalList =
                DBMethodsEnrollmentMod.getSubjectApprovalsInfo(strSy, strSemester, strStudentNo);

        for (TableModel.ApprovalSubjectInfo subjectInfo : subjectApprovalList) {
            intTotalUnits += subjectInfo.getUnits();
        }

        return intTotalUnits;
    }

    @FXML
    private void onClickBtnApproveAll() {
        // Display a confirmation dialog to ensure the user wants to approve all subjects
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Approval");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to approve all subjects for:\n" + lblFullName.getText() + " [ " + lblStudentNo.getText() + " ]?");
        confirmationAlert.initStyle(StageStyle.UTILITY);

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            // Get the list of subject approvals for the student
            ObservableList<TableModel.ApprovalSubjectInfo> subjectApprovalList =
                    DBMethodsEnrollmentMod.getSubjectApprovalsInfo(strSy, strSemester, strStudentNo);

            // List to store approved subjects for sending in the email
            List<String> approvedSubjects = new ArrayList<>();

            // Iterate through the subject approvals and approve those with a "pending" remark
            for (TableModel.ApprovalSubjectInfo subjectInfo : subjectApprovalList) {
                String strSubjectCode = subjectInfo.getSubjectCode();
                String strBlockNo = subjectInfo.getSection();
                if (subjectInfo.getRemark().equals("pending")) {
                    DBMethodsEnrollmentMod.approveSubjectApplication(strSy, strSemester, strStudentNo, strSubjectCode, strBlockNo);
                    // Add the subject code and description to the list of approved subjects
                    approvedSubjects.add("[" + strSubjectCode + "] " + subjectInfo.getDescription());
                }
            }

            // Send email notification with the list of approved subjects
            sendEmailNotification(approvedSubjects);

            // Refresh the table after approving all subjects
            updateTableWithSearchResults(strStudentNo);
        }
    }

    private void sendEmailNotification(List<String> approvedSubjects) {
        String strToEmail = "plmictodemo@gmail.com"; // replace with the recipient's email

        String strSubject = "[" + strStudentNo + "] SUBJECTS APPROVAL NOTIFICATION";

        String strStdName = fetchNameData("vwStudentMasterlist", "student_no", "full_name", strStudentNo);

        StringBuilder strEmailContent = new StringBuilder("Good day, " + strStdName.toUpperCase() + "!\n\nThe following subjects have been approved by ICTO:\n\n   ");

        // Display each approved subject in the format [CODE] Description
        int intCount = 1;
        for (String approvedSubject : approvedSubjects) {
            strEmailContent.append(intCount).append(".)  ").append(approvedSubject).append("\n   ");
            intCount++;
        }

        strEmailContent.append("\n\nPlease proceed to the next step of your enrollment. Thank you.");

        MailSenderUtils.sendMail(strToEmail, strSubject, strEmailContent.toString());
    }



    public void onClickBtnViewPendingSchedule() {
        Map<String, Object> data = new HashMap<>();
        data.put("studentNo", lblStudentNo.getText());
        data.put("fullName", lblFullName.getText().toUpperCase());
        data.put("prevModule", "APPROVALS");
        c.loadScreen("/FXML/admin_system/EnrollmentModule/ViewStudentSchedule.fxml", anchorPaneContentContainer, data);
    }


    public void onClickBtnGoBack(){
        Map<String, Object> data = new HashMap<>();
        data.put("studentNo", lblStudentNo.getText());
        data.put("fullName", lblFullName.getText().toUpperCase());
        c.loadScreen("/FXML/admin_system/EnrollmentModule/ApprovalsConsole.fxml", anchorPaneContentContainer, data);
    }
}
