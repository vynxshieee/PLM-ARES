package com.OOP.plmares.controllers.admin_system.CollegeModule;

import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsCollegeMod;
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

public class CollegeModuleController {
    @FXML private AnchorPane anchorPaneContentContainer, btnEdit, btnDelete, btnClear;
    @FXML private TableView<TableModel.CollegeModule> tblVwCollegeModule;
    @FXML private TableColumn<TableModel.CollegeModule, String> colCollegeCode, colDescription, colDateOpened, colDateClosed, colStatus;
    @FXML private Label lblCollegeCode, lblDescription, lblDateOpened, lblDateClosed, lblActive;
    @FXML private TextField txtCollegeSearch;

    private TableUtils t = new TableUtils();
    private CommonUtils c = new CommonUtils();

    @FXML
    private void initialize() {
        colCollegeCode.setCellValueFactory(cellData -> cellData.getValue().strCollegeCodeProperty());

        t.setupTextWrapping(colDescription);
        colDescription.setCellValueFactory(cellData -> cellData.getValue().strDescriptionProperty());

        colDateOpened.setCellValueFactory(cellData -> cellData.getValue().strDateOpenedProperty());
        colDateClosed.setCellValueFactory(cellData -> cellData.getValue().strDateClosedProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());

        updateTableWithFilter("");
        tblVwCollegeModule.getColumns().forEach(column -> column.setReorderable(false));
        TableUtils.setTableClickListener(tblVwCollegeModule, this::updateLabels);
    }

    private void updateLabels(TableModel.CollegeModule collegeInfo) {

        lblCollegeCode.setText(collegeInfo.getStrCollegeCode());
        lblDescription.setText(collegeInfo.getStrDescription());
        lblDateOpened.setText(collegeInfo.getStrDateOpened());
        lblDateClosed.setText(collegeInfo.getStrDateClosed());
        lblActive.setText(collegeInfo.getStrStatus());

        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
    }

    private void updateTableWithFilter(String strSearchTerm) {
        ObservableList<TableModel.CollegeModule> listFilteredResults = DBMethodsCollegeMod.getColleges(strSearchTerm);
        TableUtils.populateTable(tblVwCollegeModule, listFilteredResults);
    }

    public void handleSearch() {
        updateTableWithFilter(txtCollegeSearch.getText());
    }
    public void handleBtnClear() {
        updateTableWithFilter("");
        clearAllFields();
    }

    public void clearAllFields() {
        txtCollegeSearch.setText("");
        lblCollegeCode.setText("< >");
        lblDescription.setText("< >");
        lblDateOpened.setText("< >");
        lblDateClosed.setText("< >");
        lblActive.setText("< >");

        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
    }

    public void onClickBtnAdd(){
        c.loadScreen("/FXML/admin_system/CollegeModule/CollegeModuleAddRecord.fxml", anchorPaneContentContainer);
    }

    @FXML
    public void onClickBtnEdit(){
        Map<String, Object> data = new HashMap<>();
        data.put("collegeCode", lblCollegeCode.getText());
        c.loadScreen("/FXML/admin_system/CollegeModule/CollegeModuleEditRecord.fxml", anchorPaneContentContainer, data);
    }


    public void onClickBtnDelete(){
        if(DBMethodsCollegeMod.deleteCollegeModule(lblCollegeCode.getText())){
            updateTableWithFilter("");
            clearAllFields();
        }
    }
}
