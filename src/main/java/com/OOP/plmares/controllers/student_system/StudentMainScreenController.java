package com.OOP.plmares.controllers.student_system;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.GlobalSchedule;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.NavigationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

import static com.OOP.plmares.controllers.tableUtils.DBCommonMethods.fetchNameData;
import static com.OOP.plmares.controllers.utilities.CommonUtils.fadeInAndMoveUpAndCenterStage;
import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class StudentMainScreenController implements DataInitializable {

    @FXML
    private AnchorPane btnDashboardIcon, btnDashboard, btnEnlistmentIcon, btnEnlistment, btnRegistrationIcon, btnRegistration,
            btnGradesIcon, btnGrades, btnScheduleIcon, btnSchedule, btnSubjectsCatalogIcon, btnSubjectsCatalog,
            btnCurrCatalogIcon, btnCurrCatalog, btnLogoutIcon, btnLogout, btnUserInfo, btnTuition, btnTuitionIcon,

            anchorPaneSideButtons, anchorPaneContentContainer;

    @FXML
    private Label lblDashboard, lblEnlistment, lblRegistration, lblGrades, lblSchedule,
            lblSubjectsCatalog, lblCurrCatalog, lblLogout, lblUserInfo, lblTuition;

    @FXML
    private ImageView imgDashboardIcon, imgEnlistmentIcon, imgRegistrationIcon, imgGradesIcon, imgScheduleIcon,
            imgSubjectsCatalogIcon, imgCurrCatalogIcon, imgLogoutIcon, imgUserInfo, imgTuitionIcon;

    private final CommonUtils c = new CommonUtils();
    private final NavigationUtils n = new NavigationUtils();
    private final GlobalSchedule globalSchedule = new GlobalSchedule();
    private String strPreviousMenu = "", strStudentNo = "", stdName = "";
    private Boolean flagIsOpenSideMenu = false, flagIsLockedSideMenu = false, sideMenuButtonDoubleClick = false;

    private Map<String, Object> data = new HashMap<>();

    @Override
    public void initializeData(Map<String, Object> data) {
        strStudentNo = (String) data.get("userID");
        System.out.println("UserID received for USER INFO: " + strStudentNo);
        stdName = fetchNameData("student", "student_no", "firstname", strStudentNo);
        c.adjustLabelSizeWithText(lblUserInfo, stdName.toUpperCase());

        if (LocalDate.now().isAfter(globalSchedule.getDtStartDate()) || LocalDate.now().isBefore(globalSchedule.getDtEndDate())) {   // check enrollment date
            btnEnlistment.setDisable(false);
            btnEnlistmentIcon.setDisable(false);
        } else {
            btnEnlistment.setDisable(true);
            btnEnlistmentIcon.setDisable(true);
        }
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
    private void resetPrevMenuBtnToDefault (String strPreviousMenu){
        switch (strPreviousMenu){
            case "dashboard":
                n.resetOverallMenuBtnToDefault(btnDashboard, btnDashboardIcon, lblDashboard, imgDashboardIcon);
                break;
            case "enlistment":
                n.resetOverallMenuBtnToDefault(btnEnlistment, btnEnlistmentIcon, lblEnlistment, imgEnlistmentIcon);
                break;
            case "tuition":
                n.resetOverallMenuBtnToDefault(btnTuition, btnTuitionIcon, lblTuition, imgTuitionIcon);
                break;
            case "registration":
                n.resetOverallMenuBtnToDefault(btnRegistration, btnRegistrationIcon, lblRegistration, imgRegistrationIcon);
                break;
            case "grades":
                n.resetOverallMenuBtnToDefault(btnGrades, btnGradesIcon, lblGrades, imgGradesIcon);
                break;
            case "schedule":
                n.resetOverallMenuBtnToDefault(btnSchedule, btnScheduleIcon, lblSchedule, imgScheduleIcon);
                break;
            case "subjects catalog":
                n.resetOverallMenuBtnToDefault(btnSubjectsCatalog, btnSubjectsCatalogIcon, lblSubjectsCatalog, imgSubjectsCatalogIcon);
                break;
            case "curriculums catalog":
                n.resetOverallMenuBtnToDefault(btnCurrCatalog, btnCurrCatalogIcon, lblCurrCatalog, imgCurrCatalogIcon);
                break;
            case "logout":
                n.resetOverallMenuBtnToDefault(btnLogout, btnLogoutIcon, lblLogout, imgLogoutIcon);
                break;
            case "user info":
                n.resetUserInfoBtnToDefault(btnUserInfo);
                break;
        }
    }


    // ------ menu buttons on click functions ------

    // load fxml files with the student number on click
    public void onClickNavigationButton(AnchorPane mainButton, AnchorPane icon, Label label, ImageView imageIcon, String fxmlPath, String currentMenu) {
        onPressButton(mainButton, icon, label, imageIcon, currentMenu);
        if (!sideMenuButtonDoubleClick) {
            Map<String, Object> data = new HashMap<>();
            data.put("studentNo", strStudentNo);
            data.put("studentName", stdName);
            c.loadScreen(fxmlPath, anchorPaneContentContainer, data);
        }
    }

    public void onClickBtnDashboard() {
        onClickNavigationButton(btnDashboard, btnDashboardIcon, lblDashboard, imgDashboardIcon, "/FXML/student_system/Dashboard.fxml", "dashboard");
    }

    public void onClickBtnEnlistment() {
        onClickNavigationButton(btnEnlistment, btnEnlistmentIcon, lblEnlistment, imgEnlistmentIcon, "/FXML/student_system/Enlistment.fxml", "enlistment");
    }

    public void onClickBtnTuition() {
        onClickNavigationButton(btnTuition, btnTuitionIcon, lblTuition, imgTuitionIcon, "/FXML/student_system/Tuition.fxml", "tuition");
    }

    public void onClickBtnRegistration() {
        onClickNavigationButton(btnRegistration, btnRegistrationIcon, lblRegistration, imgRegistrationIcon, "/FXML/student_system/Registration.fxml", "registration");
    }


    public void onClickBtnGrades() {
        onClickNavigationButton(btnGrades, btnGradesIcon, lblGrades, imgGradesIcon, "/FXML/student_system/Grades.fxml", "grades");
    }

    public void onClickBtnSchedule() {
        onClickNavigationButton(btnSchedule, btnScheduleIcon, lblSchedule, imgScheduleIcon, "/FXML/student_system/Schedule.fxml", "schedule");
    }

    public void onClickBtnSubjectsCatalog() {
        onClickNavigationButton(btnSubjectsCatalog, btnSubjectsCatalogIcon, lblSubjectsCatalog, imgSubjectsCatalogIcon, "/FXML/student_system/SubjectsCatalog.fxml", "subjects catalog");
    }

    public void onClickBtnCurrCatalog() {
        onClickNavigationButton(btnCurrCatalog, btnCurrCatalogIcon, lblCurrCatalog, imgCurrCatalogIcon, "/FXML/student_system/CurriculumsCatalog.fxml", "curriculums catalog");
    }

    public void onClickBtnUserInfo() {
        onClickNavigationButton(btnUserInfo, btnUserInfo, lblUserInfo, imgUserInfo, "/FXML/student_system/UserInfoStudent.fxml", "user info");
    }


    public void onClickBtnLogout(MouseEvent e) {

        if(!sideMenuButtonDoubleClick){
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
                    Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
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
    }




    // ------ menu buttons on hover functions ------

    public void onMouseEnteredDashboard() {
        n.handleSideMenuMouseEnter(btnDashboard, btnDashboardIcon, strPreviousMenu, "dashboard");
    }

    public void onMouseExitDashboard() {
        n.handleSideMenuMouseExit(btnDashboard, btnDashboardIcon);
    }

    public void onMouseEnteredEnlistment() {
        n.handleSideMenuMouseEnter(btnEnlistment, btnEnlistmentIcon, strPreviousMenu, "enlistment");
    }

    public void onMouseExitEnlistment() {
        n.handleSideMenuMouseExit(btnEnlistment, btnEnlistmentIcon);
    }

    public void onMouseEnteredTuition() {
        n.handleSideMenuMouseEnter(btnTuition, btnTuitionIcon, strPreviousMenu, "tuition");
    }

    public void onMouseExitTuition() {
        n.handleSideMenuMouseExit(btnTuition, btnTuitionIcon);
    }

    public void onMouseEnteredRegistration() {
        n.handleSideMenuMouseEnter(btnRegistration, btnRegistrationIcon, strPreviousMenu, "registration");
    }

    public void onMouseExitRegistration() {
        n.handleSideMenuMouseExit(btnRegistration, btnRegistrationIcon);
    }

    public void onMouseEnteredGrades() {
        n.handleSideMenuMouseEnter(btnGrades, btnGradesIcon, strPreviousMenu, "grades");
    }

    public void onMouseExitGrades() {
        n.handleSideMenuMouseExit(btnGrades, btnGradesIcon);
    }

    public void onMouseEnteredSchedule() {
        n.handleSideMenuMouseEnter(btnSchedule, btnScheduleIcon, strPreviousMenu, "schedule");
    }

    public void onMouseExitSchedule() {
        n.handleSideMenuMouseExit(btnSchedule, btnScheduleIcon);
    }

    public void onMouseEnteredCoursesManagement() {
        n.handleSideMenuMouseEnter(btnSubjectsCatalog, btnSubjectsCatalogIcon, strPreviousMenu, "subjects catalog");
    }

    public void onMouseExitCoursesManagement() {
        n.handleSideMenuMouseExit(btnSubjectsCatalog, btnSubjectsCatalogIcon);
    }

    public void onMouseEnteredCurriculumManagement() {
        n.handleSideMenuMouseEnter(btnCurrCatalog, btnCurrCatalogIcon, strPreviousMenu, "curriculums catalog");
    }

    public void onMouseExitCurriculumManagement() {
        n.handleSideMenuMouseExit(btnCurrCatalog, btnCurrCatalogIcon);
    }

    public void onMouseEnteredLogout() {
        n.handleSideMenuMouseEnter(btnLogout, btnLogoutIcon, strPreviousMenu, "logout");
    }

    public void onMouseExitLogout() {
        n.handleSideMenuMouseExit(btnLogout, btnLogoutIcon);
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
        if(!flagIsLockedSideMenu){  // if menu is closed, open it
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
        if(flagIsLockedSideMenu){ // close the menu by clicking the sub main container when the side navigation is locked
            n.sideMenuCloseTransition(anchorPaneSideButtons);
            flagIsLockedSideMenu = false;
            flagIsOpenSideMenu = false;
        }
    }
}

