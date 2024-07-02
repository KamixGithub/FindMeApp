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
    public UserModel(){
        this.name = "No User Selected";
        this.friendcode = -1;
        this.location = new double[]{90,0};
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

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }
}
