package com.OOP.plmares.controllers.student_system;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsUserInfoAdmin;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.controllers.utilities.MailSenderUtils;
import com.OOP.plmares.controllers.utilities.WarningDialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.Map;

import static com.OOP.plmares.controllers.tableUtils.DBCommonMethods.fetchNameData;

public class ChangePasswordSuggestInfoController implements DataInitializable {

    @FXML
    private Label
            errLblCurrentPass, errLblNewPass, errLblConfirmPass,
            lblShowCurrPass, lblShowNewPass, lblShowConfirmNewPass,
            errLblSuggestInfo;

    @FXML
    private TextField txtCurrPassword, txtNewPassword, txtConfirmNewPassword;
    @FXML
    private PasswordField passCurrPassword, passNewPassword, passConfirmNewPassword;
    @FXML
    private TextArea txtAreaSuggest;


    private CommonUtils c = new CommonUtils();
    private String strStudentNo = "";

    @Override
    public void initializeData(Map<String, Object> data) {
        strStudentNo = (String) data.get("studentNo");
    }
    public void onClickBtnChangePassword() {
        boolean boolPasswordsValid = c.validatePasswords(
                strStudentNo,
                c.getPassword(txtCurrPassword, passCurrPassword),
                c.getPassword(txtNewPassword, passNewPassword),
                c.getPassword(txtConfirmNewPassword, passConfirmNewPassword),
                errLblCurrentPass, errLblNewPass, errLblConfirmPass
        );
        System.out.println("STUDENT NUMBER: " + strStudentNo);
        // check if valid, and ask if they wish to continue updating password (w/o errors)
        if(boolPasswordsValid
                && DBMethodsUserInfoAdmin.updateAdminPassword(c.getPassword(txtNewPassword, passNewPassword), strStudentNo)){
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


    // ------ methods for SuggestInfo
    public void onClickSubmitRequest() {
        String strTextAreaContent = txtAreaSuggest.getText();
        if(!strTextAreaContent.isEmpty() && strTextAreaContent.length() < 1000){
            String strToEmail = "plmictodemo@gmail.com"; // replace with the recipient's email
            String strSubject = "INFORMATION SUGGESTION CHANGE FOR [" + strStudentNo + "]";
            String strStdName = fetchNameData("vwStudentMasterlist", "student_no", "full_name", strStudentNo);
            String strEmailContent = "FROM: " + strStdName.toUpperCase() + " [" + strStudentNo + "]\n\nCONCERN:\n\n\" " + txtAreaSuggest.getText() + " \"";

            if(MailSenderUtils.sendMail(strToEmail, strSubject, strEmailContent)){
                txtAreaSuggest.setText("");
                errLblSuggestInfo.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Please recheck your inputs.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
            errLblSuggestInfo.setText("Should be non-empty, max 1000 characters *");
        }
    }
}

