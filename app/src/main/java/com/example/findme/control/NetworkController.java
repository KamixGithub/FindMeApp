package com.example.findme.control;

import com.example.findme.model.DeviceModel;
import com.example.findme.model.UserModel;
import com.example.findme.MainActivity;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class NetworkController {

    private MainActivity mainActivity;
    private static final String HOST = "192.168.178.25";
    private static final int PORT = 9999;
    private static final int UPDATE_INTERVAL = 5;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public NetworkController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        scheduler.scheduleAtFixedRate(this::sendData, 5, UPDATE_INTERVAL, TimeUnit.SECONDS);
    }

    public boolean sendData() {
        DeviceModel myDevice = mainActivity.getDeviceModel();
        UserModel focus = mainActivity.getFocus();

        if (myDevice == null) return false;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(HOST, PORT), 10000);

            try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {

                // Send Data
                out.writeInt(myDevice.getMyFriendCode());
                out.writeDouble(myDevice.getLatitude());
                out.writeDouble(myDevice.getLongitude());
                out.writeInt(focus.getFriendcode());
                out.flush();


                // Read response
                try {
                    int myfriendcode = in.readInt();
                    double findLocationLatitude = in.readDouble();
                    double findLocationLongitude = in.readDouble();

                    if (myfriendcode != myDevice.getMyFriendCode()) {
                        myDevice.setMyFriendCode(myfriendcode);
                        mainActivity.updateDeviceFriendCode();
                        System.out.println("Updated my friend code");
                    }

                    if (findLocationLatitude != 90.0) {
                        focus.setLocation(new double[]{findLocationLatitude, findLocationLongitude});
                        System.out.println("Updated location");
                    } else {
                       // mainActivity.createToast("User not found");
                        System.out.println("User with this friend code doesn't exist");
                    }



                } catch (SocketTimeoutException e) {
                    System.err.println("Response timed out after 3 seconds");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
