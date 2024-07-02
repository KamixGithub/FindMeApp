package com.example.findme.control;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.findme.model.UserModel;
import com.example.findme.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;


public class StorageController {

    private static final String PREFERENCES_FILE = "com.example.findme.PREFERENCES";
    private static final String USERS_KEY = "users";
    private HashMap<Integer, UserModel> userModelList;
    private SharedPreferences sharedPreferences;

    MainActivity mainActivity;

    private Gson gson;


    public StorageController(MainActivity mainActivity) {
        this.sharedPreferences = mainActivity.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        this.mainActivity = mainActivity;
        this.gson = new Gson();
        this.userModelList = new HashMap<>();

        updatFromStorage();
    }

    public void updatFromStorage() {
        String userJson = sharedPreferences.getString(USERS_KEY, null);

        if (userJson != null) {
            Type userType = new TypeToken<HashMap<Integer, UserModel>>() {}.getType();
            userModelList = gson.fromJson(userJson, userType);
        }
        mainActivity.getDeviceModel().setMyFriendCode(sharedPreferences.getInt("myFriendCode", 0));
    }
    public void writeToStorage() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userJson = gson.toJson(userModelList);
        editor.putString(USERS_KEY, userJson);
        editor.putInt("myFriendCode", mainActivity.getDeviceModel().getMyFriendCode());
        editor.apply();
    }

    public boolean addFriend(UserModel user) {
        boolean results = false;
        if (user != null) {
            results = userModelList.putIfAbsent(user.getFriendcode(), user) == null;
            writeToStorage();
        }
        return results;
    }

    public boolean deleteFriend(int friendCode) {
        boolean result = userModelList.remove(friendCode) != null;
        writeToStorage();
        return result;
    }


    public UserModel getUser(int friendCode) {
        return userModelList.get(friendCode);
    }

    public HashMap<Integer, UserModel> getUserList() {
        return userModelList;
    }

    public void clearSave() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        userModelList.clear();
    }


}
