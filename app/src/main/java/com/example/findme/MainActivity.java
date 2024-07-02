package com.example.findme;


import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.findme.control.LocationController;
import com.example.findme.control.NetworkController;
import com.example.findme.control.SensorController;
import com.example.findme.control.StorageController;
import com.example.findme.model.DeviceModel;
import com.example.findme.model.UserModel;
import com.example.findme.view.CompassView;
import com.example.findme.view.FriendlistView;


public class MainActivity extends AppCompatActivity{

    private  LocationController locationController;
    private  SensorController sensorController;
    private  StorageController storageController;

    private NetworkController networkController;
    private DeviceModel deviceModel;
    private UserModel focus;

    private FriendlistView friendlistView;
    private CompassView compassView;
    private boolean compassviewActive = true;

    Button navButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        deviceModel = new DeviceModel();
        focus = new UserModel();


        sensorController = new SensorController(this);
        locationController = new LocationController(this);
        storageController = new StorageController(this);
        networkController = new NetworkController(this);

        compassView = new CompassView(sensorController,locationController);
        friendlistView = new FriendlistView(storageController, focus);


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


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, compassView,"FRAGMENT_COMPASS");
        fragmentTransaction.commit();

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment,"FRAGMENT_FRIENDLIST");
        fragmentTransaction.commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        sensorController.register();
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorController.unregister();

    }

    public CompassView getCompassView() {
        return compassView;
    }

    public UserModel getFocus() {
        return friendlistView.getFocus();
    }

    public DeviceModel getDeviceModel(){
        return deviceModel;
    }

    public void updateDeviceFriendCode() {
        storageController.writeToStorage();
    }

    public void createToast(String text){
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public SensorController getSensorController() {
        return sensorController;
    }

    public StorageController getStorageController() {
        return storageController;
    }

    public NetworkController getNetworkController(){return networkController;}
    public FriendlistView getFriendlistView(){
        return friendlistView;
    }
}

