package com.example.findme.control;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.findme.model.DeviceModel;
import com.example.findme.model.UserModel;
import com.example.findme.MainActivity;
import com.example.findme.view.CompassView;

public class SensorController implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private MainActivity activity;
    private boolean isSensorRegistered;

    public SensorController(MainActivity activity) {
        this.activity = activity;
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        this.isSensorRegistered = false;
    }

    public void register() {
        if (rotationSensor != null && !isSensorRegistered) {
            sensorManager.registerListener(
                    this,
                    rotationSensor,
                    SensorManager.SENSOR_DELAY_UI);
            isSensorRegistered = true;
        }
    }

    public void unregister() {
        if (isSensorRegistered) {
            sensorManager.unregisterListener(this);
            isSensorRegistered = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            DeviceModel deviceModel = activity.getDeviceModel();
            SensorManager.getRotationMatrixFromVector(deviceModel.getrMat(), event.values);

            deviceModel.setmAzimuth((float) Math.toDegrees(
                    SensorManager.getOrientation(deviceModel.getrMat(), deviceModel.getOrientation())[0]));
        }

        UserModel focus = activity.getFocus();
        if (focus != null) {
            CompassView compassView = activity.getCompassView();
            compassView.updateArrowRotation(activity.getDeviceModel().getmAzimuth(), focus);
            compassView.setFocusText(focus.getName());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public boolean isRegistered() {
        return isSensorRegistered;
    }
}
