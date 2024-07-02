package com.example.findme;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.findme.control.SensorController;
import com.example.findme.control.StorageController;
import com.example.findme.model.DeviceModel;

import org.junit.Rule;
import org.junit.Test;

public class IntegrationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testSensorControllerRegistration() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            SensorController sensorController = activity.getSensorController();

            activity.onResume();

            boolean isRegisteredAfterResume = sensorController.isRegistered();
            assertThat(isRegisteredAfterResume, is(true));

            activity.onPause();

            boolean isRegisteredAfterPause = sensorController.isRegistered();
            assertThat(isRegisteredAfterPause, is(false));
        });
    }

    @Test
    public void testUpdateDevice() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            StorageController storageController = activity.getStorageController();
            DeviceModel deviceModel = activity.getDeviceModel();

            int expectedFriendCode = 12345;
            deviceModel.setMyFriendCode(expectedFriendCode);
            activity.updateDeviceFriendCode();
            deviceModel.setMyFriendCode(0);
            storageController.updatFromStorage();
            assertEquals(expectedFriendCode,deviceModel.getMyFriendCode());
        });
    }



}
