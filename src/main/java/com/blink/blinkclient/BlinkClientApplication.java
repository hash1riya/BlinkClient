package com.blink.blinkclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class BlinkClientApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/blink/blinkclient/ui/login/login.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Blink - Login");
        primaryStage.setScene(new Scene(root, 1620, 720));
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
