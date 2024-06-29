package com.example.findme.model;

public class UserModel {
    private String name;
    private int friendcode;

    double[] location;

    public UserModel(String name, int friendcode) {
        this.name = name;
        this.friendcode = friendcode;
        this.location = new double[]{0.0, 0.0};
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

    public void setLocation(double[] location) {
        this.location = location;
    }
}
