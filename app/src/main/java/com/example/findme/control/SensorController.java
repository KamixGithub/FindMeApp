package com.example.findme.control;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.fragment.app.Fragment;

import com.example.findme.model.DeviceModel;
import com.example.findme.view.MainActivity;

public class SensorController implements SensorEventListener {
    DeviceModel deviceModel;
    private SensorManager sensorManager;
    private Sensor rotationSensor;
     private MainActivity activity;

    public SensorController (MainActivity activity){
        this.deviceModel = new DeviceModel();
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        this.activity = activity;
    }

    public void register() {
        if (rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(deviceModel.getrMat(), event.values);
            deviceModel.setmAzimuth((float) Math.toDegrees(SensorManager.getOrientation(deviceModel.getrMat(), deviceModel.getOrientation())[0]));
        }
        activity.compassView.updateArrowRotation(deviceModel.getmAzimuth());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
