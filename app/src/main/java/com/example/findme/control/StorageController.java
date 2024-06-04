package com.example.findme.control;

import com.example.findme.model.LocationModel;
import com.example.findme.model.UserModel;

import java.util.HashMap;

public class StorageController {

    HashMap<String, LocationModel> locationList;
    HashMap<Integer, UserModel> userModelList;

    public StorageController() {
        this.locationList = new HashMap<>();
        this.userModelList = new HashMap<>();
        updatFromStorage();
    }

    private void updatFromStorage() {
    }
    private void writetoStorage(){
    }

    public boolean addFriend(UserModel user){
    }

    public boolean deleteFriend(int friendCode){
    }

    public boolean addLocation(LocationModel location){
    }

    public boolean deleteLocation(String name){
    }

    public void updateFriendListView(){
    }

    public void updateLocationListView(){
    }


}
