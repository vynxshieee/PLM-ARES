package com.OOP.plmares.controllers.student_system;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.student_system.DBMethodsUserInfoStudent;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UserInfoStudentController implements DataInitializable {
    @FXML
    AnchorPane anchorPaneAccManageContainer;
    @FXML
    private ImageView imgVwProfilePic;

    @FXML
    private Label lblLastName, lblFirstName, lblActiveStatus, lblAddress, lblBirthday, lblCourse, lblEmail, lblGender, lblMobileNum, lblRegStatus, lblStudentNo;


    @FXML
    private Button btnRemoveImg;



    CommonUtils c = new CommonUtils();
    String strPreviousBtn= "", strCurrentBtn= "", strStudentNo = "";

    @Override
    public void initializeData(Map<String, Object> data) {
        strStudentNo = (String) data.get("studentNo");
        System.out.println("UserID received for USER INFO: " + strStudentNo);

        TableModel.StudentMasterlist student = DBMethodsUserInfoStudent.getStudentDetails(strStudentNo);
        String fullName = student.getStrFullName();
        String[] names = fullName.split(", ", 2);  // Split at the first comma

        if (names.length == 2) {
            lblLastName.setText(names[0].toUpperCase());
            lblFirstName.setText(names[1].toUpperCase());
        } else {
            // Handle the case where the full name doesn't follow the expected format
            lblLastName.setText("");
            lblFirstName.setText("");
        }

        lblStudentNo.setText(student.getStrStudentNo());
        lblGender.setText(student.getStrGender());
        lblBirthday.setText(student.getStrBirthday());
        lblCourse.setText(student.getStrCourse());
        lblRegStatus.setText(student.getStrStatus());
        lblActiveStatus.setText(student.getStrActive());
        lblEmail.setText(student.getStrEmail());
        lblAddress.setText(student.getStrAddress());
        lblMobileNum.setText(student.getStrMobileNum());

        Image profileImage = DBCommonMethods.getProfilePictureFromDB("student", "profile_image", "student_no", strStudentNo);

        // Check if the profileImage is not null before setting it in the ImageView
        if (profileImage != null) {
            imgVwProfilePic.setImage(profileImage);
        } else {
            btnRemoveImg.setVisible(false);
        }
    }

    public void onClickUploadProfilePicture() {
        // Show a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        File selectedFile = fileChooser.showOpenDialog(getStage()); // Assuming getStage() returns the current stage

        if (selectedFile != null) {
            // Store the selected image in the database
            if(DBCommonMethods.storeProfilePictureInDB(selectedFile, "student", "profile_image",
                    "student_no", strStudentNo)){
                // display image if file size  is acceptable
                displayImage(selectedFile);
                btnRemoveImg.setVisible(true);
            }
        }
    }

    private void displayImage(File file) {
        try {
            // Display the selected image in the ImageView and stretch to fill
            Image image = new Image(file.toURI().toString());
            imgVwProfilePic.setImage(image);

            // Set fitWidth and fitHeight to control the size of the stretched image
            imgVwProfilePic.setFitWidth(200);
            imgVwProfilePic.setFitHeight(200);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception according to your application's requirements
        }
    }

    private Stage getStage() {
        return (Stage) imgVwProfilePic.getScene().getWindow();
    }

    public void removeImageFromDB() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Remove Profile Image");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to remove your current profile picture?");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            DBCommonMethods.removeProfileImage("student", "profile_image", "student_no", strStudentNo);
            Image imgDefault = new Image("file:src/main/resources/images/blankPicture.png");
            btnRemoveImg.setVisible(false);
            imgVwProfilePic.setImage(imgDefault);
        }
    }

    public void onClickBtnChangePasswordMode() {
        strCurrentBtn = "change pass";
        if(!strPreviousBtn.equals(strCurrentBtn)){
            Map<String, Object> data = new HashMap<>();
            data.put("studentNo", strStudentNo);
            c.loadScreen("/FXML/student_system/ChangePassword.fxml", anchorPaneAccManageContainer, data);
        }
        strPreviousBtn = "change pass";
    }

    public void onClickBtnSuggestInfoMode() {
        strCurrentBtn = "suggest info";
        if(!strPreviousBtn.equals(strCurrentBtn)){
            Map<String, Object> data = new HashMap<>();
            data.put("studentNo", strStudentNo);
            c.loadScreen("/FXML/student_system/SuggestInfo.fxml", anchorPaneAccManageContainer, data);
        }
        strPreviousBtn = "suggest info";
    }


}
