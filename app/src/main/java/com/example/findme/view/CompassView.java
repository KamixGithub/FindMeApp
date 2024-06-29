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

import java.util.Objects;

public class CompassView extends Fragment {
    private ImageView arrowIMG;
    private TextView coordsText;

    SensorController sensorController;
    LocationController locationController;

    private double[] findMe_cords;

    public CompassView (){
        findMe_cords = new double[]{0.0, 0.0};
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

        locationController = new LocationController((MainActivity) requireActivity());
        sensorController = new SensorController((MainActivity) requireActivity());

        arrowIMG = view.findViewById(R.id.arrowIMG);
        coordsText = view.findViewById(R.id.coordsText);
        return view;
    }

    public void updateArrowRotation(double rotation){
       if(arrowIMG != null) arrowIMG.setRotation((float) (locationController.calculateBearing(findMe_cords[0],findMe_cords[1]) - rotation));
    }

    public  void updateCoordinatesDisplay(String coords){
        if(coordsText != null) coordsText.setText(coords);
    }

    public void setFindMe_cords(double[] findMe_cords) {
        this.findMe_cords = findMe_cords;
    }
}