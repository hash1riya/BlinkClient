package com.blink.blinkclient.ui;

import com.blink.blinkclient.core.SessionManager;
import com.blink.blinkclient.network.BlinkApiService;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {
    private final BlinkApiService apiService = new BlinkApiService();

    public void show(Stage stage) {
        VBox layout = new VBox(15);

        Label welcomeLabel = new Label("Welcome back, " + SessionManager.getCurrentUsername() + "!");
        TextArea rawDataView = new TextArea();
        rawDataView.setEditable(false);

        try {
            // Fetching from your secure user relationship endpoints
            String jsonResponse = apiService.getFriendsList(SessionManager.getCurrentUsername());
            rawDataView.setText(jsonResponse);
        } catch (Exception e) {
            rawDataView.setText("Error loading secure relationship metrics: " + e.getMessage());
        }

        layout.getChildren().addAll(welcomeLabel, rawDataView);
        stage.setScene(new Scene(layout, 600, 400));
        stage.setTitle("Blink - Dashboard");
    }
}
