package in.monitor.Util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import in.monitor.Activity.MainActivity;

/**
 * Created by Swapnil Bhoite
 */
public class ConnectThread extends Thread {
    private final BluetoothSocket mBluetoothSocket;
    private final BluetoothDevice mBluetoothDevice;
    private BluetoothConnectionHandler mBluetoothConnectionHandler;

    public ConnectThread(BluetoothDevice device, BluetoothConnectionHandler bluetoothConnectionHandler) {
        mBluetoothConnectionHandler = bluetoothConnectionHandler;
        BluetoothSocket tmp = null;
        mBluetoothDevice = device;

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(MainActivity.SPP_UUID));
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Socket's create() method failed", e);
        }
        mBluetoothSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            Log.d(MainActivity.TAG, "Connecting to " + mBluetoothDevice.getName() + " " + mBluetoothDevice.getAddress());
            mBluetoothSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            Log.e(MainActivity.TAG, "Unable to connect", connectException);
            try {
                mBluetoothSocket.close();
            } catch (IOException closeException) {
                Log.e(MainActivity.TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        Log.d(MainActivity.TAG, "Connected to " + mBluetoothDevice.getName() + " " + mBluetoothDevice.getAddress());
        mBluetoothConnectionHandler.manageMyConnectedSocket(mBluetoothSocket);
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        if (mBluetoothSocket == null) {
            Log.d(MainActivity.TAG, "Socket was not connected");
            return;
        }
        try {
            mBluetoothSocket.close();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Could not close the client socket", e);
        }
    }
}
