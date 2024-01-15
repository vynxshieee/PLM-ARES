package com.OOP.plmares.controllers.student_system;

import com.OOP.plmares.controllers.GlobalSchedule;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

public class DashboardController {
    @FXML private Hyperlink hyperlinkTutorial, hyperlinkFeedbackForm;
    @FXML private Label lblAnnouncementDesc, lblStartDate, lblEndDate, lblStartDateValue, lblEndDateValue, lblCurrDateValue;
    @FXML private AnchorPane anchorPaneAnnounceContainer;
    private final GlobalSchedule globalSchedule = new GlobalSchedule();
    private final CommonUtils c = new CommonUtils();

    @FXML
    private void initialize(){
        // set current time and enrollment schedule
        String strStartDate = c.formatDateToString(globalSchedule.getDtStartDate());
        String strEndDate = c.formatDateToString(globalSchedule.getDtEndDate());
        String strCurrentDate = c.formatDateToString(LocalDate.now());
        lblStartDateValue.setText(strStartDate);
        lblEndDateValue.setText(strEndDate);
        lblCurrDateValue.setText(strCurrentDate);

        if (LocalDate.now().isBefore(globalSchedule.getDtStartDate()) || LocalDate.now().isAfter(globalSchedule.getDtEndDate())) {   // check enrollment date
            lblAnnouncementDesc.setText("The enrollment period has concluded. Please refer to the attached deadline: ");
            changeAnnouncementColor(false);
        } else {
            lblAnnouncementDesc.setText("You are STILL ELIGIBLE to enroll this term WITHIN the following period: ");
            changeAnnouncementColor(true);
        }
    }

    private void changeAnnouncementColor(Boolean boolEligibility) {
        String removeLabelStyleClass, addLabelStyleClass, removeContainerStyleClass, addContainerStyleClass;
        if(boolEligibility){
            removeContainerStyleClass = "rounded-red-containers";
            addContainerStyleClass = "rounded-green-containers";
            removeLabelStyleClass = "label-announcement-red";
            addLabelStyleClass = "label-announcement-green";
        } else {
            removeContainerStyleClass = "rounded-green-containers";
            addContainerStyleClass = "rounded-red-containers";
            removeLabelStyleClass = "label-announcement-green";
            addLabelStyleClass = "label-announcement-red";
        }

        // remove red style class
        anchorPaneAnnounceContainer.getStyleClass().remove(removeContainerStyleClass);
        lblStartDateValue.getStyleClass().remove(removeLabelStyleClass);
        lblEndDateValue.getStyleClass().remove(removeLabelStyleClass);
        lblAnnouncementDesc.getStyleClass().remove(removeLabelStyleClass);
        lblStartDate.getStyleClass().remove(removeLabelStyleClass + "-bold");
        lblEndDate.getStyleClass().remove(removeLabelStyleClass+ "-bold");

        // change color to green
        anchorPaneAnnounceContainer.getStyleClass().add(addContainerStyleClass);
        lblStartDateValue.getStyleClass().add(addLabelStyleClass);
        lblEndDateValue.getStyleClass().add(addLabelStyleClass);
        lblAnnouncementDesc.getStyleClass().add(addLabelStyleClass);
        lblStartDate.getStyleClass().add(addLabelStyleClass+ "-bold");
        lblEndDate.getStyleClass().add(addLabelStyleClass+ "-bold");
    }


    public void handleVisitPLMWebsite() throws URISyntaxException, IOException { // open PLM's official website
        String strURL = "https://www.plm.edu.ph/";
        Desktop.getDesktop().browse(new URI(strURL));
    }
    public void handleTutorialLink() throws URISyntaxException, IOException { // open YT video of PLM ARES tutorial
        String strURL = "https://www.youtube.com/watch?v=yPf9vKW04cs&ab_channel=PracticalMultiverse";
        Desktop.getDesktop().browse(new URI(strURL));
    }

    public void handleFeedbackForm() throws URISyntaxException, IOException { // open PLM ARES Gforms
        String strURL = "https://forms.gle/VcCxJAy7hoeSxWKh8";
        Desktop.getDesktop().browse(new URI(strURL));
    }
}
