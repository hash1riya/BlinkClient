package com.blink.blinkclient.ui;

import com.blink.blinkclient.core.AuthResponse;
import com.blink.blinkclient.core.SessionManager;
import com.blink.blinkclient.network.BlinkApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {



    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;

    private final BlinkApiService apiService = new BlinkApiService();

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password cannot be empty.");
            return;
        }

        // Disable button while authenticating to prevent double submissions
        loginButton.setDisable(true);
        errorLabel.setText("");

        try {
            AuthResponse authResponse = apiService.authenticate(username, password);
            SessionManager.startSession(
                    authResponse.token(),
                    authResponse.id(),
                    authResponse.username(),
                    authResponse.email()
            );

        } catch (Exception e) {
            errorLabel.setText("Authentication failed: Invalid credentials.");
            loginButton.setDisable(false);
            return;
        }

        try {
            navigateToDashboard();
        } catch (Exception e) {
            System.err.println("CRASH DURING NAVIGATION:");
            e.printStackTrace();
            errorLabel.setText("System error: Could not load dashboard.");
            loginButton.setDisable(false);
        }
    }

    private void navigateToDashboard() throws IOException {

        Stage stage = (Stage) loginButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/blink/blinkclient/ui/dashboard/dashboard.fxml"));
        Parent root = loader.load();

        Scene dashboardScene = new Scene(root, 1620, 720);
        stage.setScene(dashboardScene);
        stage.setTitle("Blink - Dashboard");
        stage.centerOnScreen();
    }
}
