package com.OOP.plmares.controllers.utilities;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class NavigationUtils {
    // ----- 1. This is to change the style of the dual anchorPanes (buttons) to a pressed state for the menu
    public void changeOverallMenuBtnToPressedState (AnchorPane btnButton, AnchorPane btnIcon, Label lblLabel, ImageView imgIcon, String strCurrentMenu, String strPreviousMenu){
        if(!strCurrentMenu.equals(strPreviousMenu)) {
            changeBtnToPressedState(btnButton);
            changeBtnIconToPressedState(btnIcon);
            changeBtnLabelToPressedState(lblLabel);
            changeImageIconToPressedState(imgIcon);
        }
    }
    public void changeBtnToPressedState(AnchorPane anchorPane) {
        anchorPane.getStyleClass().add("side-button-pressed");
    }
    public void changeUserInfoBtnToPressedState(AnchorPane anchorPane) {
        anchorPane.getStyleClass().add("info-user-pressed");
    }
    public void changeBtnIconToPressedState(AnchorPane anchorPane) {
        anchorPane.getStyleClass().add("side-button-icon-pressed");
    }
    public void changeBtnLabelToPressedState(Label label) {
        label.getStyleClass().add("label-side-menu-pressed");
    }
    public void changeImageIconToPressedState(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1);
        colorAdjust.setContrast(1);
        imageView.setEffect(colorAdjust);
    }

    // ----- 2. This is to reset the style of the dual anchorPanes (buttons) to default
    public void resetOverallMenuBtnToDefault(AnchorPane btnButton, AnchorPane btnIcon, Label lblLabel, ImageView imgIcon) {
        resetBtnToDefault(btnButton);
        resetBtnIconToDefault(btnIcon);
        resetBtnLabelToDefault(lblLabel);
        resetImageIconToDefault(imgIcon);
    }
    public void resetBtnToDefault(AnchorPane anchorPane) {
        anchorPane.getStyleClass().remove("side-button-pressed");
    }
    public void resetUserInfoBtnToDefault(AnchorPane anchorPane) {
        anchorPane.getStyleClass().remove("info-user-pressed");
    }
    public void resetBtnIconToDefault(AnchorPane anchorPane) {
        anchorPane.getStyleClass().remove("side-button-icon-pressed");
    }
    public void resetBtnLabelToDefault(Label label) {
        label.getStyleClass().remove("label-side-menu-pressed");
    }
    public void resetImageIconToDefault(ImageView imageView) {
        imageView.setEffect(null);
    }

    // ----- 3. Methods for change in CSS style when hovered, both on mouse enter and exit
    // required to be set since the hover method in CSS will still work despite the button being in pressed state

    public void handleSideMenuMouseEnter(AnchorPane button, AnchorPane icon, String strPreviousMenu, String menuName) {
        if (!strPreviousMenu.equals(menuName)) {
            button.getStyleClass().add("side-button-hover");
            icon.getStyleClass().add("side-button-icon-hover");
        }
    }
    public void handleSideMenuMouseExit(AnchorPane button, AnchorPane icon) {
        button.getStyleClass().remove("side-button-hover");
        icon.getStyleClass().remove("side-button-icon-hover");
    }


    // ----- 4. Methods for side navigation on hover

    public boolean onMouseEnterSideNavigation(Boolean flagIsOpenSideMenu, Boolean flagIsLockedSideMenu, AnchorPane anchorPaneSideButtons) {  // navigation should open on hover if it's not locked
        if(!flagIsOpenSideMenu && !flagIsLockedSideMenu){
            sideMenuOpenTransition(anchorPaneSideButtons);
            return true;
        }
        return false;
    }

    public boolean onMouseExitSideNavigation(Boolean flagIsOpenSideMenu, Boolean flagIsLockedSideMenu, AnchorPane anchorPaneSideButtons) { // navigation should open on hover if it's not locked
        if(flagIsOpenSideMenu && !flagIsLockedSideMenu){
            sideMenuCloseTransition(anchorPaneSideButtons);
            return false;
        }
        return true;
    }


    // ----- 5. Methods for side navigation transition for open and close

    public void sideMenuOpenTransition(AnchorPane anchorPaneSideButtons) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.35), anchorPaneSideButtons);
        transition.setToX(285);
        transition.play();
    }
    public void sideMenuCloseTransition(AnchorPane anchorPaneSideButtons) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.35), anchorPaneSideButtons);
        transition.setToX(0);
        transition.play();
    }
}
