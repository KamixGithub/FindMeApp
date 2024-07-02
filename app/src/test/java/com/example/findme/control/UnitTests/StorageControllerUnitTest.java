package com.example.findme.control.UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.findme.control.StorageController;
import com.example.findme.model.DeviceModel;
import com.example.findme.model.UserModel;
import com.example.findme.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Objects;

public class StorageControllerUnitTest {

    @Mock
    MainActivity mockMainActivity;

    @Mock
    SharedPreferences mockSharedPreferences;

    StorageController storageController;


    @Before
    public void setUp() {

        mockSharedPreferences = mock(SharedPreferences.class);
        SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        mockMainActivity = mock(MainActivity.class);

        when(mockMainActivity.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);

        DeviceModel mockDeviceModel = mock(DeviceModel.class);
        when(mockDeviceModel.getMyFriendCode()).thenReturn(0);
        doNothing().when(mockDeviceModel).setMyFriendCode(anyInt());

        when(mockMainActivity.getDeviceModel()).thenReturn(mockDeviceModel);

        storageController = new StorageController(mockMainActivity);
        storageController.clearSave();
    }


    @Test
    public void testAddFriend_SuccessfullyAddsUser() {

        UserModel userToAdd = new UserModel("Steffan",123);

        boolean result = storageController.addFriend(userToAdd);

        assertTrue(result);
        HashMap<Integer, UserModel> userList = storageController.getUserList();
        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertTrue(userList.containsKey(123));
        assertEquals("Steffan", Objects.requireNonNull(userList.get(123)).getName());


        verify(mockSharedPreferences, times(2)).edit();
    }

    @Test
    public void testDeleteFriend_SuccessfullyDeletesUser() {

        UserModel userToAdd = new UserModel("Steffan", 123);
        storageController.addFriend(userToAdd);

        boolean result = storageController.deleteFriend(123);

        assertTrue(result);
        HashMap<Integer, UserModel> userList = storageController.getUserList();
        assertNotNull(userList);
        assertEquals(0, userList.size());

        verify(mockSharedPreferences, times(3)).edit();
    }

}
