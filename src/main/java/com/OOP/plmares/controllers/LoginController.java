package com.OOP.plmares.controllers;

import com.OOP.plmares.controllers.utilities.CommonUtils;
import com.OOP.plmares.database.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.OOP.plmares.controllers.utilities.CommonUtils.fadeInAndMoveUpAndCenterStage;
import static com.OOP.plmares.controllers.utilities.WarningDialogUtils.showGenericErrorWarning;

public class LoginController {
    @FXML
    private Hyperlink hlinkCantSignIn;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtUsername, txtPassword;
    @FXML
    private PasswordField passPassword;
    @FXML
    private CheckBox chkShowPassword;
    @FXML
    private Label errLblLogin;

    private Stage stage;
    private Scene scene;
    private final CommonUtils c = new CommonUtils();

    public void handleContactUs() throws URISyntaxException, IOException { // open plm contacts website link
        String strURL = "https://www.plm.edu.ph/contact";
        Desktop.getDesktop().browse(new URI(strURL));
    }

    public void onClickBtnLogin(ActionEvent e) {
        String strUserID = txtUsername.getText();
        String strPassword = c.getPassword(txtPassword, passPassword);

        // Use the ConnectDB method to check login credentials
        String query = "SELECT type FROM login_credential WHERE user_id = ? AND BINARY password = ?";
        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, strUserID);
            preparedStatement.setString(2, strPassword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String userType = resultSet.getString("type");

                    FXMLLoader fxmlLoader;
                    Parent root;
                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

                    if ("S".equals(userType)) {
                        fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/student_system/StudentMainScreen.fxml"));
                        root = fxmlLoader.load();
                    } else if ("E".equals(userType)) {
                        fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/admin_system/AdminMainScreen.fxml"));
                        root = fxmlLoader.load();
                    } else {
                        System.out.println("Invalid user type.");
                        return;
                    }

                    // Access the controller associated with the loaded FXML
                    Object controller = fxmlLoader.getController();

                    // Pass the data to the controller
                    if (controller instanceof DataInitializable) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("userID", strUserID);
                        ((DataInitializable) controller).initializeData(data);
                    }

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    fadeInAndMoveUpAndCenterStage(stage, root);
                    stage.show();

                } else {
                    errLblLogin.setText("Invalid credentials. Review your inputs and try again.");
                }
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            showGenericErrorWarning();
        }
    }

    public void handleChkShowPassword() {
        Boolean boolIsChecked = chkShowPassword.isSelected();
        if (boolIsChecked) {
            // Show the password
            txtPassword.setText(passPassword.getText());
            txtPassword.setVisible(true);
            passPassword.setVisible(false);
        } else {
            // Hide the password
            passPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false);
            passPassword.setVisible(true);
        }
    }

}
