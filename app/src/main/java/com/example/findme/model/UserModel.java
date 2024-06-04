package com.example.findme.model;

public class UserModel {
    private String name;
    private int friendcode;

    public UserModel(String name, int friendcode) {
        this.name = name;
        this.friendcode = friendcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFriendcode() {
        return friendcode;
    }

    public void setFriendcode(int friendcode) {
        this.friendcode = friendcode;
    }
}
