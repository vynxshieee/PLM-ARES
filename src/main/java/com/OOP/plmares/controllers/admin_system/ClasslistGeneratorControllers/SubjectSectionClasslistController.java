package com.OOP.plmares.controllers.admin_system.ClasslistGeneratorControllers;

import com.OOP.plmares.controllers.tableUtils.*;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsClasslistMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.PrintClasslistUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import javax.swing.*;

public class SubjectSectionClasslistController {

    @FXML private AnchorPane anchorPaneContentContainer, btnPrint;
    @FXML private TableView<TableModel.ClasslistEnrollees> tblVwSubjectSectionClasslist;
    @FXML private TableColumn<TableModel.ClasslistEnrollees, String> colStudentNo, colFullName, colStatus;
    @FXML private ComboBox<String> cmbSchoolYear, cmbSemester;
    @FXML private TextField txtSubjectCode, txtSection;
    @FXML private Label lblSchoolYear, lblSemester, lblSubjectCode, lblFaculty, lblSection, lblDescription, lblStudentCount;


    private final CommonUtils c = new CommonUtils();
    private final PrintClasslistUtils printUtil = new PrintClasslistUtils();
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

        // setup columns
        colStudentNo.setCellValueFactory(cellData -> cellData.getValue().strStudentNoProperty());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().strFullNameProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());

        tblVwSubjectSectionClasslist.getColumns().forEach(column -> column.setReorderable(false));
        tblVwSubjectSectionClasslist.setFocusModel(null);
    }

    private int updateTableWithSearchResults(String strSy, String strSemester, String strSubjectCode, String strSection) {
        // Call the method to get search results based on the search term
        ObservableList<TableModel.ClasslistEnrollees> subjectSectionClasslist =
                DBMethodsClasslistMod.getClasslistEnrollees(strSy, strSemester,  strSubjectCode, strSection);

        // Update the TableView with the new search results
        TableUtils.populateTable(tblVwSubjectSectionClasslist, subjectSectionClasslist);

        return subjectSectionClasslist.size();
    }

    public void handleGenerateClasslist(){
        String strSy = cmbSchoolYear.getValue().toString();
        String strSemester = cmbSemester.getValue().toString();
        String strSubjectCode = txtSubjectCode.getText();
        String strSection = txtSection.getText();


        int intStudentCount = updateTableWithSearchResults(strSy, strSemester, strSubjectCode, strSection);

        System.out.println("Result count: " + intStudentCount);

        if(intStudentCount != 0){
            TableModel.SubjectSectionClasslist classlistDetails =
                    DBMethodsClasslistMod.getClassDetails(strSy, strSemester,  strSubjectCode, strSection);

            lblSchoolYear.setText(classlistDetails.getStrSy());
            lblSemester.setText(classlistDetails.getStrSemester());
            lblSubjectCode.setText(classlistDetails.getSubjectCode());
            lblDescription.setText(classlistDetails.getDescription());
            lblFaculty.setText(classlistDetails.getFaculty());
            lblSection.setText(classlistDetails.getSection());
            lblDescription.setText(classlistDetails.getDescription());

            lblStudentCount.setText(intStudentCount + "");
            btnPrint.setDisable(false);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No matching records found!",
                    "No Matching Records",
                    JOptionPane.ERROR_MESSAGE
            );
            btnPrint.setDisable(true);
        }
    }

    public void handleClearAll(){
        updateTableWithSearchResults("", "", "", "");

        txtSection.setText("");
        txtSubjectCode.setText("");
        cmbSchoolYear.setValue(strSy);
        cmbSemester.setValue(strSemester);

        lblSchoolYear.setText("");
        lblSemester.setText("");
        lblSubjectCode.setText("");
        lblDescription.setText("");
        lblFaculty.setText("");
        lblSection.setText("");
        lblDescription.setText("");

        lblStudentCount.setText("< >");
        btnPrint.setDisable(true);
    }

    @FXML
    private void handlePrint() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(tblVwSubjectSectionClasslist.getScene().getWindow())) {
            String schoolYear = cmbSchoolYear.getValue();
            String semester = cmbSemester.getValue();
            Pair<String, String> pairSubject = new Pair<>("Subject", lblDescription.getText() + " [" + txtSection.getText() + "]");
            Node contentNode = printUtil.createPrintNode(tblVwSubjectSectionClasslist, schoolYear, semester, pairSubject);

            // Print the content
            boolean success = printUtil.printNode(printerJob, contentNode);

            if (success) {
                // End the print job
                printerJob.endJob();
            } else {
                System.out.println("Printing failed.");
            }
        }
    }
    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/ClasslistGenerator/ClasslistGenerator.fxml", anchorPaneContentContainer);
    }

}
