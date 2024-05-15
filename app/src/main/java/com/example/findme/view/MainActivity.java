package com.example.findme.view;


import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;


import android.widget.ImageView;
import android.widget.TextView;

import com.example.findme.control.LocationController;
import com.example.findme.R;
import com.example.findme.control.SensorController;


public class MainActivity extends AppCompatActivity{

    private ImageView arrowIMG;
    private TextView coordsText;


    LocationController locationController;
    SensorController sensorController;

    private final double[] FINDEME_COORDS = {52.417662, 13.413297};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrowIMG = findViewById(R.id.arrowIMG);
        coordsText = findViewById(R.id.coordsText);

        locationController = new LocationController(this);
        sensorController = new SensorController(this);

    }

    public void updateArrowRotation(double rotation){
        arrowIMG.setRotation((float) (locationController.calculateBearing(FINDEME_COORDS[0],FINDEME_COORDS[1]) - rotation));
    }
    public  void updateCoordinatesDisplay(String coords){
        coordsText.setText(coords);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorController.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorController.unregister();

    }


}

