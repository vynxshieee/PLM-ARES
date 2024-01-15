package com.OOP.plmares.views;

import com.OOP.plmares.database.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

import static com.OOP.plmares.controllers.utilities.CommonUtils.generateRandomPassword;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // set login page as landing scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        Parent rtLoginRoot = fxmlLoader.load();

        Scene scene = new Scene(rtLoginRoot);

        // set app icon and title
        Image imgIcon = new Image("file:src/main/resources/images/plm_logo.png");
        stage.getIcons().add(imgIcon);
        stage.setTitle("PLM ARES login");
        stage.setResizable(false);


        stage.setScene(scene);
        stage.show();
        System.out.println(generateRandomPassword());
    }
    public static void main(String[] args) {
        ConnectDB db = new ConnectDB();
        Connection connection = db.Connect();
        launch();
    }
}
