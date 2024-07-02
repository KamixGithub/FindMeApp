package com.example.findme.control.UnitTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import com.example.findme.control.LocationController;
import com.example.findme.model.DeviceModel;
import com.example.findme.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LocationControllerUnitTest {

    @Mock
    MainActivity mockMainActivity;

    @Mock
    LocationManager mockLocationManager;

    @Mock
    DeviceModel mockDeviceModel;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock MainActivity behavior
        when(mockMainActivity.getSystemService(Context.LOCATION_SERVICE)).thenReturn(mockLocationManager);
        when(mockMainActivity.checkSelfPermission(anyString())).thenReturn(PackageManager.PERMISSION_GRANTED);
        when(mockMainActivity.getDeviceModel()).thenReturn(mockDeviceModel);
    }

    @Test
    public void testCalculateBearing() {
        LocationController locationController = new LocationController(mockMainActivity);
        double lat2 = 1.0;
        double lon2 = 1.0;

        // Mocking getLatitude() and getLongitude() in mockDeviceModel
        when(mockDeviceModel.getLatitude()).thenReturn(0.0);
        when(mockDeviceModel.getLongitude()).thenReturn(0.0);

        double bearing = locationController.calculateBearing(lat2, lon2);

        // Assert the calculated bearing is as expected
        assertEquals(45.0, bearing, 1.0); // Adjust delta as needed
    }

    private Location createMockLocation(double latitude, double longitude) {
        Location mockLocation = new Location(LocationManager.GPS_PROVIDER);
        mockLocation.setLatitude(latitude);
        mockLocation.setLongitude(longitude);
        return mockLocation;
    }
}
