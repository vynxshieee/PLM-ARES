package com.OOP.plmares.controllers.admin_system.ClasslistGeneratorControllers;

import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ClasslistGeneratorController {
    @FXML
    AnchorPane anchorPaneContentContainer;
    CommonUtils c = new CommonUtils();
    public void onClickBtnSubjectSectionClasslist(){
        c.loadScreen("/FXML/admin_system/ClasslistGenerator/SubjectSectionClasslist.fxml", anchorPaneContentContainer);
    }
    public void onClickBtnGeneralMasterlist(){
        c.loadScreen("/FXML/admin_system/ClasslistGenerator/GeneralClasslist.fxml", anchorPaneContentContainer);
    }
}
