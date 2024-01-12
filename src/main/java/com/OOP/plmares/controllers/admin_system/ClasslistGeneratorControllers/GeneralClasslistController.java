package com.OOP.plmares.controllers.admin_system.ClasslistGeneratorControllers;

import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
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

public class GeneralClasslistController {

    @FXML
    AnchorPane anchorPaneContentContainer, btnPrint;
    @FXML
    private TableView<TableModel.ClasslistEnrollees> tblVwGeneralClasslist;
    @FXML
    private TableColumn<TableModel.ClasslistEnrollees, String> colStudentNo, colFullName, colStatus;

    @FXML
    private ComboBox<String> cmbSchoolYear, cmbSemester;

    @FXML
    private Label lblStudentCount;
    @FXML
    private TextField txtSection;


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


        DBCommonMethods.populateComboBox(cmbSemester, "semester", "semester");
        cmbSemester.getItems().remove("--");

        DBCommonMethods.populateComboBox(cmbSchoolYear, "sy", "sy");
        cmbSchoolYear.getItems().remove("--");


        // setup columns
        colStudentNo.setCellValueFactory(cellData -> cellData.getValue().strStudentNoProperty());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().strFullNameProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());

        tblVwGeneralClasslist.getColumns().forEach(column -> column.setReorderable(false));
        tblVwGeneralClasslist.setFocusModel(null);
    }

    private int updateTableWithSearchResults(String strSy, String strSemester, String strSection) {
        // Call the method to get search results based on the search term
        ObservableList<TableModel.ClasslistEnrollees> subjectSectionClasslist =
                DBMethodsClasslistMod.getGeneralClasslistEnrollees(strSy, strSemester,  strSection);

        // Update the TableView with the new search results
        TableUtils.populateTable(tblVwGeneralClasslist, subjectSectionClasslist);

        return subjectSectionClasslist.size();
    }

    public void handleGenerateClasslist(){
        String strSy = cmbSchoolYear.getValue();
        String strSemester = cmbSemester.getValue();
        String strSection = txtSection.getText();


        int intStudentCount = updateTableWithSearchResults(strSy, strSemester, strSection);

        System.out.println("Result count: " + intStudentCount);

        if(intStudentCount != 0){
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
        updateTableWithSearchResults("", "", "");

        txtSection.setText("");
        cmbSchoolYear.setValue(strSy);
        cmbSemester.setValue(strSemester);

        lblStudentCount.setText("< >");
        btnPrint.setDisable(true);
    }

    public void onClickBtnGoBack(){
        c.loadScreen("/FXML/admin_system/ClasslistGenerator/ClasslistGenerator.fxml", anchorPaneContentContainer);
    }


    @FXML
    private void handlePrint() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(tblVwGeneralClasslist.getScene().getWindow())) {
            String schoolYear = cmbSchoolYear.getValue();
            String semester = cmbSemester.getValue();

            String sectionText = txtSection.getText();
            Pair<String, String> pairCourse = new Pair<>("", "");

            if (sectionText.startsWith("CS")) {
                pairCourse = new Pair<>("Course", "Bachelor of Science in Computer Science Major in Computer Science " +
                        (sectionText.length() > 2 ? "[" + sectionText + "]" : ""));
            } else if (sectionText.startsWith("IT")) {
                pairCourse = new Pair<>("Course", "Bachelor of Science in Computer Science Major in Information Technology " +
                        (sectionText.length() > 2 ? "[" + sectionText + "]" : ""));
            }

            Node contentNode = printUtil.createPrintNode(tblVwGeneralClasslist, schoolYear, semester, pairCourse);

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


}
