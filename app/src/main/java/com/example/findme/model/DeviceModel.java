package com.example.findme.model;

public class DeviceModel {

    private double latitude;
    private double longitude;
    private float[] orientation = new float[3];
    private float[] rMat = new float[9];

    private float mAzimuth = 0;


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
