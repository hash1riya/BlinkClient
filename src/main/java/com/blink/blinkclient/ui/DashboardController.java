package com.blink.blinkclient.ui;

import com.blink.blinkclient.core.SessionManager;
import com.blink.blinkclient.model.Friendship;
import com.blink.blinkclient.model.Room;
import com.blink.blinkclient.model.User;
import com.blink.blinkclient.network.BlinkApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private ListView<User> friendsListView;
    @FXML private ListView<Room> roomsListView;

    private final BlinkApiService apiService = new BlinkApiService();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @FXML
    public void initialize() {
        // Set context profile state details
        this.welcomeLabel.setText("Active Session: " + SessionManager.getCurrUsername());

        // Load protected data records
        loadFriendsData();
        loadRoomsData();
    }

    private void loadFriendsData() {
        try {
            String rawJson = this.apiService.getFriendsList(SessionManager.getUserId());

            List<Friendship> friendships = this.objectMapper.readValue(
                    rawJson,
                    new TypeReference<>() {
                    }
            );

            ObservableList<User> friends = FXCollections.observableArrayList();
            for (Friendship f : friendships) {
                friends.add(f.getFriend());
            }

            this.friendsListView.setItems(friends);

        } catch (Exception e) {
            System.err.println("Error parsing friends: " + e.getMessage());
        }
    }

    private void loadRoomsData() {
        try {
            String rawJson = this.apiService.getRoomsList(SessionManager.getUserId());

            if (rawJson == null || rawJson.isBlank()) {
                System.out.println("Rooms payload was empty");
                return;
            }

            List<Room> rooms = this.objectMapper.readValue(
                    rawJson,
                    new TypeReference<>() {
                    }
            );

            ObservableList<Room> observableRooms = FXCollections.observableArrayList();
            observableRooms.addAll(rooms);

            this.roomsListView.setItems(observableRooms);

        } catch (Exception e) {
            System.err.println("Error parsing rooms: " + e.getMessage());
        }
    }

    @FXML
    public void handleLogout() throws IOException {
        // Drop runtime session context values
        SessionManager.clearSession();

        // Roll stage view backwards onto login page window layout
        Stage stage = (Stage) this.welcomeLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/blink/blinkclient/ui/login/login.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root, 1620, 720));
        stage.setTitle("Blink - Login");
        stage.centerOnScreen();
    }
}
