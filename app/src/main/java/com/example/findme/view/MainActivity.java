package com.example.findme.view;


import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findme.control.LocationController;
import com.example.findme.R;
import com.example.findme.control.SensorController;
import com.example.findme.control.StorageController;
import com.example.findme.model.LocationModel;
import com.example.findme.model.UserModel;


public class MainActivity extends AppCompatActivity{


     private FriendlistView friendlistView;
    private CompassView compassView;

    private StorageController storageController;
    FragmentManager fragmentManager;

    LocationModel focusLocation;

    boolean compassviewActive = true;

    Button navButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compassView = new CompassView();
        friendlistView = new FriendlistView();
        storageController = new StorageController();

        friendlistView.setStorageController(storageController);

        navButton = findViewById(R.id.buttonFriend);

        navButton.setOnClickListener(v -> {
           if(compassviewActive) {
               replaceFragment(friendlistView);
               navButton.setText(getString(R.string.Compass));
               compassviewActive = false;
           }else{
            replaceFragment(compassView);
            navButton.setText(getString(R.string.Friend));
            compassviewActive = true;}
        });


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, compassView);
        fragmentTransaction.commit();

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }



    @Override
    protected void onResume() {
        super.onResume();
        compassView.sensorController.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compassView.sensorController.unregister();

    }

    public CompassView getCompassView() {
        return compassView;
    }

    public StorageController getStorageController (){
        return storageController;
    }
}

