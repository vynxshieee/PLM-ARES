package com.OOP.plmares.controllers.admin_system.CourseModule;

import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsCourseMod;
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

public class CourseModuleController {
    @FXML private AnchorPane anchorPaneContentContainer, btnEdit, btnDelete, btnClear;
    @FXML private TableView<TableModel.CourseModule> tblVwCourseModule;
    @FXML private TableColumn<TableModel.CourseModule, String> colCourseCode, colDescription, colCollegeCode, colDateOpened, colDateClosed, colStatus;
    @FXML private Label lblCourseCode, lblDescription, lblCollegeCode, lblDateOpened, lblDateClosed, lblActive;
    @FXML private TextField txtCourseSearch;


    private final TableUtils t = new TableUtils();
    private final CommonUtils c = new CommonUtils();

    @FXML
    private void initialize() {
        colCourseCode.setCellValueFactory(cellData -> cellData.getValue().strCourseCodeProperty());

        t.setupTextWrapping(colDescription);
        colDescription.setCellValueFactory(cellData -> cellData.getValue().strDescriptionProperty());

        colCollegeCode.setCellValueFactory(cellData -> cellData.getValue().strCollegeCodeProperty());
        colDateOpened.setCellValueFactory(cellData -> cellData.getValue().strDateOpenedProperty());
        colDateClosed.setCellValueFactory(cellData -> cellData.getValue().strDateClosedProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().strStatusProperty());

        updateTableWithFilter("");
        tblVwCourseModule.getColumns().forEach(column -> column.setReorderable(false));
        TableUtils.setTableClickListener(tblVwCourseModule, this::updateLabels);
    }

    private void updateLabels(TableModel.CourseModule courseInfo) {

        lblCourseCode.setText(courseInfo.getStrCourseCode());
        lblDescription.setText(courseInfo.getStrDescription());
        lblCollegeCode.setText(courseInfo.getStrCollegeCode());
        lblDateOpened.setText(courseInfo.getStrDateOpened());
        lblDateClosed.setText(courseInfo.getStrDateClosed());
        lblActive.setText(courseInfo.getStrStatus());

        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
    }

    private void updateTableWithFilter(String strSearchTerm) {
        ObservableList<TableModel.CourseModule> listFilteredResults = DBMethodsCourseMod.getCourses(strSearchTerm);
        TableUtils.populateTable(tblVwCourseModule, listFilteredResults);
    }

    public void handleSearch() {
        updateTableWithFilter(txtCourseSearch.getText());
    }
    public void handleBtnClear() {
        updateTableWithFilter("");
        clearAllFields();
    }

    public void clearAllFields() {
        txtCourseSearch.setText("");
        lblCourseCode.setText("< >");
        lblDescription.setText("< >");
        lblCollegeCode.setText("< >");
        lblDateOpened.setText("< >");
        lblDateClosed.setText("< >");
        lblActive.setText("< >");

        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
    }

    public void onClickBtnAdd(){
        c.loadScreen("/FXML/admin_system/CourseModule/CourseModuleAddRecord.fxml", anchorPaneContentContainer);
    }

    @FXML
    public void onClickBtnEdit(){
        Map<String, Object> data = new HashMap<>();
        data.put("courseCode", lblCourseCode.getText());
        c.loadScreen("/FXML/admin_system/CourseModule/CourseModuleEditRecord.fxml", anchorPaneContentContainer, data);
    }


    public void onClickBtnDelete(){
        if(DBMethodsCourseMod.deleteCourseModule(lblCourseCode.getText())){
            updateTableWithFilter("");
            clearAllFields();
        }
    }
}
