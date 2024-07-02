package com.example.findme.control;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import com.example.findme.MainActivity;

public class LocationController implements LocationListener {

    private final MainActivity mainActivity;


    public LocationController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        LocationManager locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);

        if (mainActivity.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && mainActivity.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mainActivity.requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (mainActivity.getDeviceModel() != null) {
            mainActivity.getDeviceModel().setLatitude(location.getLatitude());
            mainActivity.getDeviceModel().setLongitude(location.getLongitude());
        }
    }

    public double calculateBearing(double lat2, double lon2) {

        double lat1 = mainActivity.getDeviceModel().getLatitude();
        double lon1 = mainActivity.getDeviceModel().getLongitude();

        // Umwandlung von Grad in Bogenmaß
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double lambda1 = Math.toRadians(lon1);
        double lambda2 = Math.toRadians(lon2);

        // Berechnung von y und x
        double deltaLambda = lambda2 - lambda1;
        double y = Math.sin(deltaLambda) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2) -
                Math.sin(phi1) * Math.cos(phi2) * Math.cos(deltaLambda);

        // Berechnung des Winkels (Bearing) in Bogenmaß
        double theta = Math.atan2(y, x);

        // Umwandlung des Winkels in Grad und Anpassung auf den Bereich 0-360 Grad
        double bearing = (Math.toDegrees(theta) + 360) % 360;

        return bearing;
    }

}
