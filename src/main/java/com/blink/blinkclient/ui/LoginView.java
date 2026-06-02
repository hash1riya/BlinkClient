package com.blink.blinkclient.ui;

import com.blink.blinkclient.core.SessionManager;
import com.blink.blinkclient.network.BlinkApiService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private final BlinkApiService apiService = new BlinkApiService();

    public void show(Stage stage) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Log In");
        Label errorLabel = new Label();

        loginButton.setOnAction(e -> {
            try {
                String token = apiService.authenticate(usernameField.getText(), passwordField.getText());
                SessionManager.startSession(token, usernameField.getText());

                new DashboardView().show(stage);
            } catch (Exception ex) {
                errorLabel.setText("Invalid credentials or server offline");
            }
        });

        layout.getChildren().addAll(usernameField, passwordField, loginButton, errorLabel);
        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Blink - Login");
        stage.show();
    }
}