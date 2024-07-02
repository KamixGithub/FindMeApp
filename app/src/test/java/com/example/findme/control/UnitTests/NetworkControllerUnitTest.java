package com.example.findme.control.UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.example.findme.control.NetworkController;
import com.example.findme.model.DeviceModel;
import com.example.findme.model.UserModel;
import com.example.findme.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@RunWith(PowerMockRunner.class) // Use PowerMockRunner
@PrepareForTest({NetworkController.class, Socket.class}) // Prepare classes for mocking
public class NetworkControllerUnitTest {

    @Test
    public void testSendData_success() throws Exception {
        // Mocks
        MainActivity mockActivity = mock(MainActivity.class);
        DeviceModel mockDevice = mock(DeviceModel.class);
        UserModel mockUser = mock(UserModel.class);
        when(mockActivity.getDeviceModel()).thenReturn(mockDevice);
        when(mockActivity.getFocus()).thenReturn(mockUser);
        when(mockDevice.getMyFriendCode()).thenReturn(123);
        when(mockDevice.getLatitude()).thenReturn(45.678);
        when(mockDevice.getLongitude()).thenReturn(-78.912);
        when(mockUser.getFriendcode()).thenReturn(456);

        // Mock Socket and Streams using PowerMockito
        Socket mockSocket = mock(Socket.class);
        whenNew(Socket.class).withNoArguments().thenReturn(mockSocket);
        OutputStream mockOut = mock(OutputStream.class);
        InputStream mockIn = mock(InputStream.class);
        when(mockSocket.getOutputStream()).thenReturn(mockOut);
        when(mockSocket.getInputStream()).thenReturn(mockIn);

        // Setup output stream to capture written data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        doAnswer(invocation -> {
            byte[] data = invocation.getArgument(0);
            outputStream.write(data);
            return data.length;
        }).when(mockOut).write(any(byte[].class));

        // Execute sendData method
        NetworkController networkController = new NetworkController(mockActivity);
        boolean result = networkController.sendData();

        // Verify behavior
        assertTrue(result);
        // Verify that correct data was sent
        byte[] sentData = outputStream.toByteArray();
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(sentData));
        assertEquals(123, in.readInt());
        assertEquals(45.678, in.readDouble(), 0.001); // Adjust delta as needed
        assertEquals(-78.912, in.readDouble(), 0.001); // Adjust delta as needed
        assertEquals(456, in.readInt());
    }
}
