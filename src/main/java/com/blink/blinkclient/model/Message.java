package com.blink.blinkclient.model;

import java.time.LocalDateTime;

public class Message {

    public enum MessageStatus {
        SENT,
        PENDING,
        FAILED
    }

    private String id;
    private String username;
    private String content;
    private MessageStatus status = MessageStatus.PENDING;
    private LocalDateTime timeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        String statusIndicator =    status == MessageStatus.PENDING ? "[...]" :
                                    status == MessageStatus.FAILED ? "[!]" : "";
        return String.format("%s [%s]: %s", statusIndicator, username, content);
    }
}