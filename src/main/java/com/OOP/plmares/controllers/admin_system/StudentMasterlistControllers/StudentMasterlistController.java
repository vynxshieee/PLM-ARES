package com.OOP.plmares.controllers.admin_system.StudentMasterlistControllers;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsStudentMasterlistMod;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;

import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showSuccessDialog;

public class StudentMasterlistController {
    @FXML
    AnchorPane anchorPaneContentContainer;
    @FXML
    private TableView<TableModel.StudentMasterlist> tblVwStudentMasterlist;
    @FXML
    private TableColumn<TableModel.StudentMasterlist, String> colStudentNo, colFullName,  colGender,  colCourse, colStatus, colBirthday,  colMobileNum
                                                            , colAddress, colEmail, colActive;
    @FXML
    private Label lblLastName, lblFirstName, lblStudentNo, lblGender, lblCourse, lblStatus, lblBirthday, lblMobileNum, lblEmail, lblAddress, lblActive;
    @FXML
    private TextField txtStudentSearch;
    @FXML
    private AnchorPane btnEdit, btnDelete, btnClear;
    private final TableUtils t = new TableUtils();
    private final CommonUtils c = new CommonUtils();

    @FXML
    private void initialize() {
        // Set up columns for Enrollment Request table
        colStudentNo.setCellValueFactory(cellData -> cellData.getValue().strStudentNoProperty());
        t.setupTextWrapping(colFullName);
        colFullName.setCellValueFactory(cellData -> cellData.getValue().strFullNameProperty());
        colGender.setCellValueFactory(cellData -> cellData.getValue().strGenderProperty());
        colCourse.setCellValueFactory(cellData -> cellData.getValue().strCourseProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());
        colBirthday.setCellValueFactory(cellData -> cellData.getValue().strBirthdayProperty());
        colMobileNum.setCellValueFactory(cellData -> cellData.getValue().strMobileNumProperty());
        t.setupTextWrapping(colAddress);
        colAddress.setCellValueFactory(cellData -> cellData.getValue().strAddressProperty());
        t.setupTextWrapping(colEmail);
        colEmail.setCellValueFactory(cellData -> cellData.getValue().strEmailProperty());
        colActive.setCellValueFactory(cellData -> cellData.getValue().strActiveProperty());

        tblVwStudentMasterlist.getColumns().forEach(column -> column.setReorderable(false));
        updateTableWithFilter("");
        TableUtils.setTableClickListener(tblVwStudentMasterlist, this::updateLabels);

    }

    private void updateLabels(TableModel.StudentMasterlist studentInfo) {
        String fullName = studentInfo.getStrFullName();
        String[] names = fullName.split(", ", 2);  // Split at the first comma

        if (names.length == 2) {
            lblLastName.setText(names[0].toUpperCase());
            lblFirstName.setText(names[1].toUpperCase());
        } else {
            // Handle the case where the full name doesn't follow the expected format
            lblLastName.setText("");
            lblFirstName.setText("");
        }

        lblStudentNo.setText(studentInfo.getStrStudentNo());
        lblGender.setText(studentInfo.getStrGender().toUpperCase());
        lblCourse.setText(studentInfo.getStrCourse().toUpperCase());
        lblStatus.setText(studentInfo.getStrStatus().toUpperCase());
        lblBirthday.setText(studentInfo.getStrBirthday());
        lblMobileNum.setText(studentInfo.getStrMobileNum());
        lblEmail.setText(studentInfo.getStrEmail());
        lblAddress.setText(studentInfo.getStrAddress());
        lblActive.setText(studentInfo.getStrActive().toUpperCase());

        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
    }

    private void updateTableWithFilter(String strStudentSearch) {
        ObservableList<TableModel.StudentMasterlist> listFilteredResults = DBMethodsStudentMasterlistMod.getStudentMasterlist(strStudentSearch);
        System.out.println("fetched results: " + listFilteredResults.size());
        TableUtils.populateTable(tblVwStudentMasterlist, listFilteredResults);
    }

    public void handleSearch() {
        updateTableWithFilter(txtStudentSearch.getText());
    }
    public void handleBtnClear() {
        updateTableWithFilter("");
        clearAllFields();
    }
    public void onClickBtnAddANewStudent(){
        c.loadScreen("/FXML/admin_system/StudentMasterlist/StudentMasterlistAddRecord.fxml", anchorPaneContentContainer);
    }
    public void onClickBtnEdit(){
        Map<String, Object> data = new HashMap<>();
        data.put("studentNo", lblStudentNo.getText());
        c.loadScreen("/FXML/admin_system/StudentMasterlist/StudentMasterlistEditRecord.fxml", anchorPaneContentContainer, data);
    }
    public void clearAllFields() {
        // Revert the details back to the original values
        txtStudentSearch.setText("");
        lblFirstName.setText("< >");
        lblLastName.setText("< >");
        lblStudentNo.setText("< >");
        lblGender.setText("< >");
        lblCourse.setText("< >");
        lblStatus.setText("< >");
        lblBirthday.setText("< >");
        lblMobileNum.setText("< >");
        lblEmail.setText("< >");
        lblAddress.setText("< >");
        lblActive.setText("< >");
    }
    public void onClickBtnDelete(){
        if(DBMethodsStudentMasterlistMod.deleteStudentMasterlist(lblStudentNo.getText())){
            updateTableWithFilter("");
            clearAllFields();
            showSuccessDialog();
        }
    }
}
