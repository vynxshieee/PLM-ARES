package com.OOP.plmares.controllers.admin_system.EmployeeMasterlistControllers;

import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEmployeeMasterlist;
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

public class EmployeeMasterlistController {
    @FXML private AnchorPane anchorPaneContentContainer, btnEdit, btnDelete;
    @FXML private TableView<TableModel.EmployeeMasterlist> tblVwEmployeeMasterlist;
    @FXML private TableColumn<TableModel.EmployeeMasterlist, String> colEmployeeID, colFullName, colEmail, colGender, colMobileNum, colAddress, colBirthday, colActive;
    @FXML private Label lblLastName, lblFirstName, lblEmployeeID, lblGender, lblBirthday, lblMobileNum, lblEmail, lblAddress, lblActive;
    @FXML private TextField txtEmployeeSearch;

    private TableUtils t = new TableUtils();
    private CommonUtils c = new CommonUtils();

    @FXML
    private void initialize() {
        // Set up columns for Enrollment Request table
        colEmployeeID.setCellValueFactory(cellData -> cellData.getValue().strEmployeeIDProperty());

        t.setupTextWrapping(colFullName);
        colFullName.setCellValueFactory(cellData -> cellData.getValue().strFullNameProperty());

        colGender.setCellValueFactory(cellData -> cellData.getValue().strGenderProperty());
        colBirthday.setCellValueFactory(cellData -> cellData.getValue().strBirthdayProperty());
        colMobileNum.setCellValueFactory(cellData -> cellData.getValue().strMobileNumProperty());

        t.setupTextWrapping(colAddress);
        colAddress.setCellValueFactory(cellData -> cellData.getValue().strAddressProperty());

        t.setupTextWrapping(colEmail);
        colEmail.setCellValueFactory(cellData -> cellData.getValue().strEmailProperty());
        colActive.setCellValueFactory(cellData -> cellData.getValue().strActiveProperty());

        // Populate the TableView for Enrollment Request
        updateTableWithFilter("");
        tblVwEmployeeMasterlist.getColumns().forEach(column -> column.setReorderable(false));
        TableUtils.setTableClickListener(tblVwEmployeeMasterlist, this::updateLabels);
    }

    private void updateLabels(TableModel.EmployeeMasterlist employeeInfo) {
        String fullName = employeeInfo.getStrFullName();
        String[] names = fullName.split(", ", 2);  // Split at the first comma

        if (names.length == 2) {
            lblLastName.setText(names[0].toUpperCase());
            lblFirstName.setText(names[1].toUpperCase());
        } else {
            // Handle the case where the full name doesn't follow the expected format
            lblLastName.setText("");
            lblFirstName.setText("");
        }

        // Rest of the labels
        lblEmployeeID.setText(employeeInfo.getStrEmployeeID());
        lblGender.setText(employeeInfo.getStrGender().toUpperCase());
        lblBirthday.setText(employeeInfo.getStrBirthday());
        lblMobileNum.setText(employeeInfo.getStrMobileNum());
        lblEmail.setText(employeeInfo.getStrEmail());
        lblAddress.setText(employeeInfo.getStrAddress());
        lblActive.setText(employeeInfo.getStrActive().toUpperCase());

        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
    }

    private void updateTableWithFilter(String strStudentSearch) {

        ObservableList<TableModel.EmployeeMasterlist> listFilteredResults = DBMethodsEmployeeMasterlist.getEmployeeMasterlist(strStudentSearch);

        System.out.println("fetched results: " + listFilteredResults.size());

        TableUtils.populateTable(tblVwEmployeeMasterlist, listFilteredResults);


    }

    public void handleSearch() {
        updateTableWithFilter(txtEmployeeSearch.getText());
    }

    public void handleBtnClear() {
        updateTableWithFilter("");
        clearAllFields();
    }

    public void clearAllFields() {
        // Revert the details back to the original values
        txtEmployeeSearch.setText("");
        lblFirstName.setText("< >");
        lblLastName.setText("< >");
        lblEmployeeID.setText("< >");
        lblGender.setText("< >");
        lblBirthday.setText("< >");
        lblMobileNum.setText("< >");
        lblEmail.setText("< >");
        lblAddress.setText("< >");
        lblActive.setText("< >");
    }

    public void onClickBtnAddANewEmployee(){
        c.loadScreen("/FXML/admin_system/EmployeeMasterlist/EmployeeMasterlistAddRecord.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnEdit(){
        Map<String, Object> data = new HashMap<>();
        data.put("employeeID", lblEmployeeID.getText());
        c.loadScreen("/FXML/admin_system/EmployeeMasterlist/EmployeeMasterlistEditRecord.fxml", anchorPaneContentContainer, data);
    }

    public void onClickBtnDelete(){
        if(DBMethodsEmployeeMasterlist.deleteEmployeeMasterlist(lblEmployeeID.getText())) {
            updateTableWithFilter("");
            clearAllFields();
            showSuccessDialog();
        }
    }
}
