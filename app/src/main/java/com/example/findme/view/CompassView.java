package com.example.findme.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.findme.R;
import com.example.findme.control.LocationController;
import com.example.findme.control.SensorController;
import com.example.findme.model.UserModel;

public class CompassView extends Fragment {
    private ImageView arrowIMG;

    private TextView focusText;

    private SensorController sensorController;
    private LocationController locationController;


    public CompassView (SensorController sensorController, LocationController locationController){
    this.sensorController = sensorController;
    this.locationController = locationController;
    }

    public CompassView (){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compass_view, container, false);
        arrowIMG = view.findViewById(R.id.arrowIMG);
        focusText = view.findViewById(R.id.focus);
        return view;
    }

    public void updateArrowRotation(double rotation, UserModel focus){
       if(arrowIMG != null && focus != null ) {
           setArrowIMGRotation((float) (locationController.calculateBearing(focus.getLocation()[0],focus.getLocation()[1]) - rotation));
       }
    }


    public void setFocusText(String text) {
        if(text != null) focusText.setText(text);
    }



    public void setArrowIMGRotation(float rotation) {
        arrowIMG.setRotation(rotation);
    }

    public float getArrowIMGRotation() {
        return arrowIMG.getRotation();
    }
}