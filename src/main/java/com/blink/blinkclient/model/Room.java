package com.blink.blinkclient.model;

import java.time.LocalDateTime;
import java.util.List;

public class Room {

    private String id;
    private String ownerId;
    private String name;
    private String desc;
    private LocalDateTime createdAt;

    private List<Membership> members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Membership> getMembers() {
        return members;
    }

    public void setMembers(List<Membership> members) {
        this.members = members;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
