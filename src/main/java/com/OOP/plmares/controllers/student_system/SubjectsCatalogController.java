package com.OOP.plmares.controllers.student_system;

import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSubjectSchedulingMod;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SubjectsCatalogController {
    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private TableView<TableModel.SubjectModuleInfo> tblVwSubjectModule;
    @FXML private TableColumn<TableModel.SubjectModuleInfo, String> colSubjectCode, colDescription, colCurriculum, colCollegeCode, colStatus;
    @FXML private TableColumn<TableModel.SubjectModuleInfo, Integer> colUnits;
    @FXML private Label lblSubjectCode, lblDescription, lblUnits, lblCurriculum, lblCollegeCode, lblStatus;
    @FXML private TextField txtSubjectSearch;
    @FXML private AnchorPane btnEdit, btnDelete, btnClear;

    private TableUtils t = new TableUtils();
    private CommonUtils c = new CommonUtils();

    @FXML
    private void initialize() {
        colSubjectCode.setCellValueFactory(cellData -> cellData.getValue().subjectCodeProperty());

        t.setupTextWrapping(colDescription);
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colUnits.setCellValueFactory(cellData -> cellData.getValue().unitsProperty().asObject());
        colCurriculum.setCellValueFactory(cellData -> cellData.getValue().strCurriculumProperty());
        colCollegeCode.setCellValueFactory(cellData -> cellData.getValue().collegeProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        updateTableWithFilter("");
        tblVwSubjectModule.getColumns().forEach(column -> column.setReorderable(false));
        TableUtils.setTableClickListener(tblVwSubjectModule, this::updateLabels);
    }

    private void updateLabels(TableModel.SubjectModuleInfo subjectInfo) {

        lblSubjectCode.setText(subjectInfo.getSubjectCode());
        lblDescription.setText(subjectInfo.getDescription());
        lblUnits.setText(Integer.toString(subjectInfo.getUnits()));
        lblCurriculum.setText(subjectInfo.getStrCurriculum());
        lblCollegeCode.setText(subjectInfo.getCollege());
        lblStatus.setText(subjectInfo.getStatus());
    }

    private void updateTableWithFilter(String strSearchTerm) {
        ObservableList<TableModel.SubjectModuleInfo> listFilteredResults = DBMethodsSubjectSchedulingMod.getSubjects(strSearchTerm);
        TableUtils.populateTable(tblVwSubjectModule, listFilteredResults);
    }

    public void handleBtnSearch() {
        updateTableWithFilter(txtSubjectSearch.getText());
    }
    public void handleBtnClear() {
        updateTableWithFilter("");
        clearAllFields();
    }

    public void clearAllFields() {
        txtSubjectSearch.setText("");
        lblSubjectCode.setText("< >");
        lblDescription.setText("< >");
        lblUnits.setText("< >");
        lblCurriculum.setText("< >");
        lblCollegeCode.setText("< >");
        lblStatus.setText("< >");

        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
    }
}
