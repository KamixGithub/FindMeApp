package com.example.findme.control;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import com.example.findme.model.DeviceModel;
import com.example.findme.view.MainActivity;

public class LocationController implements LocationListener {
    private final DeviceModel deviceModel;
    private final MainActivity activity;

    private LocationManager locationManager;


    public LocationController(MainActivity activity) {
        this.deviceModel = new DeviceModel();
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if (activity.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && activity.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation!= null) {
                deviceModel.setLatitude(lastKnownLocation.getLatitude());
                deviceModel.setLongitude(lastKnownLocation.getLongitude());
                activity.compassView.updateCoordinatesDisplay(String.format("Latitude: %s° \nLongitude: %s°",deviceModel.getLatitude(),deviceModel.getLongitude()));
            }else{
                //TODO
            }
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        deviceModel.setLatitude(location.getLatitude());
        deviceModel.setLongitude(location.getLongitude());
        activity.compassView.updateCoordinatesDisplay(String.format("Latitude: %s° \nLongitude: %s°",deviceModel.getLatitude(),deviceModel.getLongitude()));
    }

    public double calculateBearing( double lat2, double lon2) {
        double phi1 = Math.toRadians(deviceModel.getLatitude());
        double phi2 = Math.toRadians(lat2);
        double deltaLambda = Math.toRadians(lon2 - deviceModel.getLongitude());

        double y = Math.sin(deltaLambda) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2) -
                Math.sin(phi1) * Math.cos(phi2) * Math.cos(deltaLambda);
        return Math.toDegrees(Math.atan2(y, x));
    }
}
