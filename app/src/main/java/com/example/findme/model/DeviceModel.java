package com.example.findme.model;

public class DeviceModel {

    private double latitude = 0.0;
    private double longitude = 0.0;

    private int myFriendCode = 0;

    private float[] orientation = new float[3];
    private float[] rMat = new float[9];

    private float mAzimuth = 0;

    public int getMyFriendCode() {
        return myFriendCode;
    }

    public void setMyFriendCode(int myFriendCode) {
        this.myFriendCode = myFriendCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getmAzimuth() {
        return mAzimuth;
    }

    public void setmAzimuth(float mAzimuth) {
        this.mAzimuth = mAzimuth;
    }

    public float[] getOrientation() {
        return orientation;
    }

    public float[] getrMat() {
        return rMat;
    }


}
