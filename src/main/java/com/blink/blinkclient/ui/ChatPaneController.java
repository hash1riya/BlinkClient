package com.blink.blinkclient.ui;

import com.blink.blinkclient.core.SessionManager;
import com.blink.blinkclient.model.Message;
import com.blink.blinkclient.network.BlinkApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChatPaneController {

    @FXML private Label chatTitleLabel;
    @FXML private ListView<Message> messageListView;
    @FXML private TextField messageInputField;

    private final BlinkApiService apiService = new BlinkApiService();
    private final ObservableList<Message> messageHistory = FXCollections.observableArrayList();
    private String activeTargetId;
    private boolean isRoomChat;

    @FXML
    public void initialize() {
        messageListView.setItems(messageHistory);
    }

    public void setupChatTarget(String id, String displayName, boolean isRoomChat) {
        this.activeTargetId = id;
        this.isRoomChat = isRoomChat;
        this.chatTitleLabel.setText(isRoomChat ? " # " + displayName : " @ " + displayName);

        loadMessageHistory();
    }

    private void loadMessageHistory() {
        messageHistory.clear();
        try {
            String rawJson = this.apiService.getRoomHistory(SessionManager.getUserId());

            List<Message> messages = this.apiService.objectMapper.readValue(
                    rawJson,
                    new TypeReference<>() {
                    }
            );

            this.messageHistory.setAll(messages);
            this.messageListView.setItems(this.messageHistory);
        } catch (Exception e) {
            System.err.println("Could not load message history: " + e.getMessage());
        }
    }

    @FXML
    public void handleSendMessage() {
        String text = messageInputField.getText().trim();
        if (text.isEmpty()) return;

        Message msg = new Message();
        msg.setUsername(SessionManager.getCurrUsername());
        msg.setContent(text);

        messageHistory.add(msg);
        messageInputField.clear();

        // Auto-scroll to bottom
        messageListView.scrollTo(messageHistory.size() - 1);
    }
}
