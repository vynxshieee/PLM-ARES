package com.OOP.plmares.controllers.admin_system;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.NavigationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.OOP.plmares.controllers.tableUtils.DBCommonMethods.fetchNameData;
import static com.OOP.plmares.controllers.utilities.CommonUtils.fadeInAndMoveUpAndCenterStage;
import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class AdminMainScreenController implements DataInitializable {

    @FXML
    private AnchorPane anchorPaneMainContainer,

            btnApplicationIcon, btnApplication, btnEnrollmentHistIcon, btnEnrollmentHist, btnStudentMasterlistIcon, btnStudentMasterlist,
            btnStudentRecordsIcon, btnStudentRecords, btnEnrollmentIcon, btnEnrollment, btnEmployeeMasterlistIcon, btnEmployeeMasterlist,
            btnClasslistGeneratorIcon, btnClasslistGenerator, btnGradeEntryIcon, btnGradeEntry, btnSubjectModuleIcon, btnSubjectModule,
            btnCourseModuleIcon, btnCourseModule, btnCollegeModuleIcon, btnCollegeModule, btnSemesterModuleIcon, btnSemesterModule,
            btnSchoolYearModuleIcon, btnSchoolYearModule, btnUserInfo,


            anchorPaneSideButtons, anchorPaneContentContainer;

    @FXML
    private Label lblApplication, lblEnrollmentHist, lblStudentMasterlist, lblStudentRecords, lblEnrollment, lblEmployeeMasterlist,
    lblClasslistGenerator, lblGradeEntry, lblSubjectModule, lblCourseModule, lblCollegeModule, lblSemesterModule, lblSchoolYearModule,
            lblUserInfo;

    @FXML
    private ImageView imgApplicationIcon, imgEnrollmentHistIcon, imgStudentMasterlistIcon, imgStudentRecordsIcon, imgEnrollmentIcon,
            imgEmployeeMasterlistIcon, imgClasslistGeneratorIcon, imgGradeEntryIcon, imgSubjectModuleIcon,
            imgCourseModuleIcon, imgCollegeModuleIcon, imgSemesterModuleIcon, imgSchoolYearModuleIcon, imgUserInfo;

    private final CommonUtils c = new CommonUtils();
    private final NavigationUtils n = new NavigationUtils();
    private String strPreviousMenu = "", strEmployeeID;
    private Boolean flagIsOpenSideMenu = false, flagIsLockedSideMenu = false, sideMenuButtonDoubleClick = false;
    private Stage stage;

    @Override
    public void initializeData(Map<String, Object> data) {
        strEmployeeID = (String) data.get("userID");
        String stdName = fetchNameData("employee", "employee_id", "firstname", strEmployeeID.toUpperCase());
        c.adjustLabelSizeWithText(lblUserInfo, stdName.toUpperCase());
    }


    // change button (anchorPane) styles on press
    public void onPressButton(AnchorPane selectedButton, AnchorPane selectedIcon, Label selectedLabel, ImageView selectedImageView, String strCurrentMenu) {
        sideMenuButtonDoubleClick = false;
        if(strCurrentMenu.equals("user info")) {
            n.changeUserInfoBtnToPressedState(selectedButton);  // user info has a different style
        } else {
            n.changeOverallMenuBtnToPressedState(selectedButton, selectedIcon, selectedLabel, selectedImageView, strCurrentMenu, strPreviousMenu);
        }

        if(!strCurrentMenu.equals(strPreviousMenu)) {
            resetPrevMenuBtnToDefault(strPreviousMenu);
        } else {
            sideMenuButtonDoubleClick = true;
        }

        strPreviousMenu = strCurrentMenu;
    }


    // reset the previous button to default so that it won't stay pressed after another is clicked

    private void resetPrevMenuBtnToDefault(String strPreviousMenu) {
        switch (strPreviousMenu) {
            case "application":
                n.resetOverallMenuBtnToDefault(btnApplication, btnApplicationIcon, lblApplication, imgApplicationIcon);
                break;
            case "enrollment":
                n.resetOverallMenuBtnToDefault(btnEnrollment, btnEnrollmentIcon, lblEnrollment, imgEnrollmentIcon);
                break;
            case "enrollment hist":
                n.resetOverallMenuBtnToDefault(btnEnrollmentHist, btnEnrollmentHistIcon, lblEnrollmentHist, imgEnrollmentHistIcon);
                break;
            case "student masterlist":
                n.resetOverallMenuBtnToDefault(btnStudentMasterlist, btnStudentMasterlistIcon, lblStudentMasterlist, imgStudentMasterlistIcon);
                break;
            case "student records":
                n.resetOverallMenuBtnToDefault(btnStudentRecords, btnStudentRecordsIcon, lblStudentRecords, imgStudentRecordsIcon);
                break;
            case "employee masterlist":
                n.resetOverallMenuBtnToDefault(btnEmployeeMasterlist, btnEmployeeMasterlistIcon, lblEmployeeMasterlist, imgEmployeeMasterlistIcon);
                break;
            case "classlist generator":
                n.resetOverallMenuBtnToDefault(btnClasslistGenerator, btnClasslistGeneratorIcon, lblClasslistGenerator, imgClasslistGeneratorIcon);
                break;
            case "grade entry":
                n.resetOverallMenuBtnToDefault(btnGradeEntry, btnGradeEntryIcon, lblGradeEntry, imgGradeEntryIcon);
                break;
            case "subject module":
                n.resetOverallMenuBtnToDefault(btnSubjectModule, btnSubjectModuleIcon, lblSubjectModule, imgSubjectModuleIcon);
                break;
            case "course module":
                n.resetOverallMenuBtnToDefault(btnCourseModule, btnCourseModuleIcon, lblCourseModule, imgCourseModuleIcon);
                break;
            case "college module":
                n.resetOverallMenuBtnToDefault(btnCollegeModule, btnCollegeModuleIcon, lblCollegeModule, imgCollegeModuleIcon);
                break;
            case "semester module":
                n.resetOverallMenuBtnToDefault(btnSemesterModule, btnSemesterModuleIcon, lblSemesterModule, imgSemesterModuleIcon);
                break;
            case "school year module":
                n.resetOverallMenuBtnToDefault(btnSchoolYearModule, btnSchoolYearModuleIcon, lblSchoolYearModule, imgSchoolYearModuleIcon);
                break;
            case "user info":
                n.resetUserInfoBtnToDefault(btnUserInfo);
                break;
        }
    }


    // ------ menu buttons on click functions ------
    public void onClickBtnApplication() {
        onPressButton(btnApplication, btnApplicationIcon, lblApplication, imgApplicationIcon, "application");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/ApplicationModule/ApplicationModule.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnEnrollment() {
        onPressButton(btnEnrollment, btnEnrollmentIcon, lblEnrollment, imgEnrollmentIcon, "enrollment");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/EnrollmentModule/EnrollmentModule.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnEnrollmentHist() {
        onPressButton(btnEnrollmentHist, btnEnrollmentHistIcon, lblEnrollmentHist, imgEnrollmentHistIcon,  "enrollment hist");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/EnrollmentHistory.fxml", anchorPaneContentContainer);
    }

    public void onClickbtnStudentMasterlist() {
        onPressButton(btnStudentMasterlist, btnStudentMasterlistIcon, lblStudentMasterlist, imgStudentMasterlistIcon, "student masterlist");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/StudentMasterlist/StudentMasterlist.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnStudentRecords() {
        onPressButton(btnStudentRecords, btnStudentRecordsIcon, lblStudentRecords, imgStudentRecordsIcon, "student records");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/StudentRecords.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnEmployeeMasterlist() {
        onPressButton(btnEmployeeMasterlist, btnEmployeeMasterlistIcon, lblEmployeeMasterlist, imgEmployeeMasterlistIcon, "employee masterlist");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/EmployeeMasterlist/EmployeeMasterlist.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnClasslistGenerator() {
        onPressButton(btnClasslistGenerator, btnClasslistGeneratorIcon, lblClasslistGenerator, imgClasslistGeneratorIcon, "classlist generator");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/ClasslistGenerator/ClasslistGenerator.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnGradeEntry() {
        onPressButton(btnGradeEntry, btnGradeEntryIcon, lblGradeEntry, imgGradeEntryIcon, "grade entry");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/GradeEntry.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnSubjectModule() {
        onPressButton(btnSubjectModule, btnSubjectModuleIcon, lblSubjectModule, imgSubjectModuleIcon, "subject module");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/SubjectSchedulingModule/SubjectSchedulingModule.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnCourseModule() {
        onPressButton(btnCourseModule, btnCourseModuleIcon, lblCourseModule, imgCourseModuleIcon, "course module");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/CourseModule/CourseModule.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnCollegeModule() {
        onPressButton(btnCollegeModule, btnCollegeModuleIcon, lblCollegeModule, imgCollegeModuleIcon, "college module");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/CollegeModule/CollegeModule.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnSemesterModule() {
        onPressButton(btnSemesterModule, btnSemesterModuleIcon, lblSemesterModule, imgSemesterModuleIcon, "semester module");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/SemesterModule.fxml", anchorPaneContentContainer);
    }
    public void onClickBtnSchoolYearModule() {
        onPressButton(btnSchoolYearModule, btnSchoolYearModuleIcon, lblSchoolYearModule, imgSchoolYearModuleIcon, "school year module");
        if (!sideMenuButtonDoubleClick)
            c.loadScreen("/FXML/admin_system/SchoolYearModule.fxml", anchorPaneContentContainer);
    }

    public void onClickBtnUserInfo() {
        onPressButton(btnUserInfo, btnUserInfo, lblUserInfo, imgUserInfo, "user info");
        if(!sideMenuButtonDoubleClick){
            Map<String, Object> data = new HashMap<>();
            data.put("employeeID", strEmployeeID);
            c.loadScreen("/FXML/admin_system/UserInfoAdmin.fxml", anchorPaneContentContainer, data);
        }
    }

    public void onClickBtnLogout(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            // If the user confirms, load the Login screen
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));

                // Get the current stage
                stage = (Stage) ((Node)e.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                fadeInAndMoveUpAndCenterStage(stage, root);
                stage.show();
            } catch (IOException ex){
                ex.printStackTrace();
                showGenericErrorWarning();
            }
        }
    }




    // ------ menu buttons on hover functions ------

    public void onMouseEnteredApplication() {
        n.handleSideMenuMouseEnter(btnApplication, btnApplicationIcon, strPreviousMenu, "application");
    }
    public void onMouseExitApplication() {
        n.handleSideMenuMouseExit(btnApplication, btnApplicationIcon);
    }

    public void onMouseEnteredEnrollment() {
        n.handleSideMenuMouseEnter(btnEnrollment, btnEnrollmentIcon, strPreviousMenu, "enrollment");
    }
    public void onMouseExitEnrollment() {
        n.handleSideMenuMouseExit(btnEnrollment, btnEnrollmentIcon);
    }


    public void onMouseEnteredEnrollmentHist() {
        n.handleSideMenuMouseEnter(btnEnrollmentHist, btnEnrollmentHistIcon, strPreviousMenu, "enrollment hist");
    }
    public void onMouseExitEnrollmentHist() {
        n.handleSideMenuMouseExit(btnEnrollmentHist, btnEnrollmentHistIcon);
    }


    public void onMouseEnteredStudentMasterlist() {
        n.handleSideMenuMouseEnter(btnStudentMasterlist, btnStudentMasterlistIcon, strPreviousMenu, "student masterlist");
    }
    public void onMouseExitStudentMasterlist() {
        n.handleSideMenuMouseExit(btnStudentMasterlist, btnStudentMasterlistIcon);
    }


    public void onMouseEnteredStudentRecords() {
        n.handleSideMenuMouseEnter(btnStudentRecords, btnStudentRecordsIcon, strPreviousMenu, "student records");
    }
    public void onMouseExitStudentRecords() {
        n.handleSideMenuMouseExit(btnStudentRecords, btnStudentRecordsIcon);
    }


    public void onMouseEnteredEmployeeMasterlist() {
        n.handleSideMenuMouseEnter(btnEmployeeMasterlist, btnEmployeeMasterlistIcon, strPreviousMenu, "employee masterlist");
    }
    public void onMouseExitEmployeeMasterlist() {
        n.handleSideMenuMouseExit(btnEmployeeMasterlist, btnEmployeeMasterlistIcon);
    }


    public void onMouseEnteredClasslistGenerator() {
        n.handleSideMenuMouseEnter(btnClasslistGenerator, btnClasslistGeneratorIcon, strPreviousMenu, "classlist generator");
    }
    public void onMouseExitClasslistGenerator() {
        n.handleSideMenuMouseExit(btnClasslistGenerator, btnClasslistGeneratorIcon);
    }


    public void onMouseEnteredGradeEntry() {
        n.handleSideMenuMouseEnter(btnGradeEntry, btnGradeEntryIcon, strPreviousMenu, "grade entry");
    }
    public void onMouseExitGradeEntry() {
        n.handleSideMenuMouseExit(btnGradeEntry, btnGradeEntryIcon);
    }


    public void onMouseEnteredSubjectModule() {
        n.handleSideMenuMouseEnter(btnSubjectModule, btnSubjectModuleIcon, strPreviousMenu, "subject module");
    }
    public void onMouseExitSubjectModule() {
        n.handleSideMenuMouseExit(btnSubjectModule, btnSubjectModuleIcon);
    }


    public void onMouseEnteredCourseModule() {
        n.handleSideMenuMouseEnter(btnCourseModule, btnCourseModuleIcon, strPreviousMenu, "course module");
    }
    public void onMouseExitCourseModule() {
        n.handleSideMenuMouseExit(btnCourseModule, btnCourseModuleIcon);
    }


    public void onMouseEnteredCollegeModule() {
        n.handleSideMenuMouseEnter(btnCollegeModule, btnCollegeModuleIcon, strPreviousMenu, "college module");
    }
    public void onMouseCollegeModule() {
        n.handleSideMenuMouseExit(btnCollegeModule, btnCollegeModuleIcon);
    }


    public void onMouseEnteredSemesterModule() {
        n.handleSideMenuMouseEnter(btnSemesterModule, btnSemesterModuleIcon, strPreviousMenu, "semester module");
    }
    public void onMouseExitSemesterModule() {
        n.handleSideMenuMouseExit(btnSemesterModule, btnSemesterModuleIcon);
    }

    public void onMouseEnteredSchoolYearModule() {
        n.handleSideMenuMouseEnter(btnSchoolYearModule, btnSchoolYearModuleIcon, strPreviousMenu, "school year module");
    }
    public void onMouseExitSchoolYearModule() {
        n.handleSideMenuMouseExit(btnSchoolYearModule, btnSchoolYearModuleIcon);
    }

    public void onMouseEnteredUserInfo() {
        if (!strPreviousMenu.equals("user info")) {
            btnUserInfo.getStyleClass().add("side-button-hover");
        }
    }

    public void onMouseExitUserInfo() {
        btnUserInfo.getStyleClass().remove("side-button-hover");
    }


    // ------  side navigation movement (hover, lock) functions ------


    public void onMouseEnterSideNavigation() {  // navigation should open on hover if it's not locked
        flagIsOpenSideMenu = n.onMouseEnterSideNavigation(flagIsOpenSideMenu, flagIsLockedSideMenu, anchorPaneSideButtons);
    }

    public void onMouseExitSideNavigation() { // navigation should open on hover if it's not locked
        flagIsOpenSideMenu = n.onMouseExitSideNavigation(flagIsOpenSideMenu, flagIsLockedSideMenu, anchorPaneSideButtons);
    }

    public void onPressBtnMenu() {  // to lock the side navigation on the press of the menu button
        if (!flagIsLockedSideMenu) {  // if menu is closed, open it
            n.sideMenuOpenTransition(anchorPaneSideButtons);
            flagIsLockedSideMenu = true;
            flagIsOpenSideMenu = true;
        } else { // if menu is open, close it
            n.sideMenuCloseTransition(anchorPaneSideButtons);
            flagIsLockedSideMenu = false;
            flagIsOpenSideMenu = false;
        }
    }

    public void onClickContentContainer() {
        if (flagIsLockedSideMenu) { // close the menu by clicking the sub main container when the side navigation is locked
            n.sideMenuCloseTransition(anchorPaneSideButtons);
            flagIsLockedSideMenu = false;
            flagIsOpenSideMenu = false;
        }
    }
}