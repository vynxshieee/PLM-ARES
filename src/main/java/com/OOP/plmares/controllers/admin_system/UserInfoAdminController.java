package com.OOP.plmares.controllers.admin_system;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.DBCommonMethods;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsUserInfoAdmin;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.InputValidationUtils;
import com.OOP.plmares.controllers.utilities.WarningDialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.util.Map;

public class UserInfoAdminController implements DataInitializable {
    @FXML
    AnchorPane anchorPaneAccManageContainer;
    @FXML
    private ImageView imgVwProfilePic;

    @FXML
    private Label lblEmployeeID, lblLastName, lblFirstName, lblActiveStatus, lblAddress, lblBirthday, lblEmail, lblMobileNum, lblGender,
            errLblCurrentPass, errLblNewPass, errLblConfirmPass,
            lblShowCurrPass, lblShowNewPass, lblShowConfirmNewPass;

    @FXML
    private Button btnRemoveImg;
    @FXML
    private TextField txtCurrPassword, txtNewPassword, txtConfirmNewPassword;
    @FXML
    private PasswordField passCurrPassword, passNewPassword, passConfirmNewPassword;

    private final CommonUtils c = new CommonUtils();
    private final InputValidationUtils iv = new InputValidationUtils();
    private final InputValidationUtils.PasswordValidator pv = new InputValidationUtils.PasswordValidator();
    private String strEmployeeID = "";

    @Override
    public void initializeData(Map<String, Object> data) {
        strEmployeeID = (String) data.get("employeeID");
        System.out.println("UserID received for USER INFO: " + strEmployeeID);

        TableModel.EmployeeMasterlist employee = DBMethodsUserInfoAdmin.getEmployeeDetails(strEmployeeID);
        String fullName = employee.getStrFullName();
        String[] names = fullName.split(", ", 2);  // Split at the first comma

        if (names.length == 2) {
            lblLastName.setText(names[0].toUpperCase());
            lblFirstName.setText(names[1].toUpperCase());
        } else {
            // Handle the case where the full name doesn't follow the expected format
            lblLastName.setText("");
            lblFirstName.setText("");
        }

        lblEmployeeID.setText(employee.getStrEmployeeID());
        lblGender.setText(employee.getStrGender());
        lblBirthday.setText(employee.getStrBirthday());
        lblGender.setText(employee.getStrGender());
        lblActiveStatus.setText(employee.getStrActive());
        lblEmail.setText(employee.getStrEmail());
        lblAddress.setText(employee.getStrAddress());
        lblMobileNum.setText(employee.getStrMobileNum());

        Image profileImage = DBCommonMethods.getProfilePictureFromDB("employee", "profile_image", "employee_id", strEmployeeID);

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
            if(DBCommonMethods.storeProfilePictureInDB(selectedFile, "employee", "profile_image",
                    "employee_id", strEmployeeID)){
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
        }
    }

    private Stage getStage() {
        return (Stage) imgVwProfilePic.getScene().getWindow();
    }

    public void removeImageFromDB() {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to remove your current profile picture?",
                "Remove Profile Image",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (result == JOptionPane.YES_OPTION) {
            DBCommonMethods.removeProfileImage("employee", "profile_image", "employee_id", strEmployeeID);
            Image imgDefault = new Image("file:src/main/resources/images/blankPicture.png");
            btnRemoveImg.setVisible(false);
            imgVwProfilePic.setImage(imgDefault);
        }
    }

    public void onClickBtnChangePassword() {
        boolean boolPasswordsValid = c.validatePasswords(
                strEmployeeID,
                c.getPassword(txtCurrPassword, passCurrPassword),
                c.getPassword(txtNewPassword, passNewPassword),
                c.getPassword(txtConfirmNewPassword, passConfirmNewPassword),
                errLblCurrentPass, errLblNewPass, errLblConfirmPass
        );

        // check if valid, and ask if they wish to continue updating password (w/o errors)
        if(boolPasswordsValid
            && DBMethodsUserInfoAdmin.updateAdminPassword(c.getPassword(txtNewPassword, passNewPassword), strEmployeeID)){
            // clear if operation success
            txtCurrPassword.setText(""); passCurrPassword.setText("");
            txtNewPassword.setText(""); passNewPassword.setText("");
            txtConfirmNewPassword.setText(""); passConfirmNewPassword.setText("");
            WarningDialogUtils.showSuccessDialog();
        }
    }

    public void onClickLblShowCurrPass() {
        c.togglePasswordVisibility(lblShowCurrPass, txtCurrPassword, passCurrPassword);
    }

    public void onClickLblShowNewPass() {
        c.togglePasswordVisibility(lblShowNewPass, txtNewPassword, passNewPassword);
    }

    public void onClickLblShowConfirmNewPass() {
        c.togglePasswordVisibility(lblShowConfirmNewPass, txtConfirmNewPassword, passConfirmNewPassword);
    }


}
