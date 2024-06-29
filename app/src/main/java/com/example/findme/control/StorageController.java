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

        addFriend(new UserModel("Test1",1));
        addFriend(new UserModel("Test2",2));
    }

    private void updatFromStorage() {
    }
    private void writetoStorage(){
    }

    public boolean addFriend(UserModel user){
        return userModelList.putIfAbsent(user.getFriendcode(), user) != null;
    }

    public boolean deleteFriend(int friendCode){
        return userModelList.remove(friendCode) != null;
    }

    public boolean addLocation(LocationModel location){
        return locationList.putIfAbsent(location.getName(),location) != null;
    }

    public boolean deleteLocation(String name){
        return locationList.remove(name) != null;
    }

    public UserModel getUser(int friendCode){
       return userModelList.get(friendCode);
    }

    public HashMap<Integer,UserModel> getUserList(){
        return userModelList;
    }

    public HashMap<String, LocationModel> getLocationList() {
        return locationList;
    }
}
