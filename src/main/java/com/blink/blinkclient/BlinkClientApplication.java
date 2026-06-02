package com.blink.blinkclient;

import com.blink.blinkclient.ui.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;


public class BlinkClientApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView();
        loginView.show(primaryStage);
    }
}
