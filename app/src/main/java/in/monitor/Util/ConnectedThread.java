package in.monitor.Util;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import in.monitor.Activity.MainActivity;

import static in.monitor.Activity.MainActivity.BUFF_SIZE;
import static in.monitor.Activity.MainActivity.MessageConstants.MESSAGE_READ;
import static in.monitor.Activity.MainActivity.MessageConstants.MESSAGE_TOAST;
import static in.monitor.Activity.MainActivity.MessageConstants.MESSAGE_WRITE;

/**
 * Created by Swapnil Bhoite
 */
public class ConnectedThread extends Thread {
    private final BluetoothSocket mSocket;
    private final InputStream mInputStream;
    private final OutputStream mOutputStream;
    private Handler mHandler;
    private byte[] mBuffer; // mmBuffer store for the stream

    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mSocket = socket;
        mHandler = handler;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        mBuffer = new byte[BUFF_SIZE];

        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Error occurred when creating output stream", e);
        }

        mInputStream = tmpIn;
        mOutputStream = tmpOut;
    }

    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
        while (!interrupted()) {
            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Message message = mHandler.obtainMessage(MESSAGE_READ, -1, -1, line);
                   // Log.d("Mitesh   ",message.toString());
                    message.sendToTarget();
                }
            } catch (IOException e) {
                Log.d(MainActivity.TAG, "Input stream was disconnected", e);
                break;
            }
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void write(byte[] bytes) {
        try {
            mOutputStream.write(bytes);
            Message writtenMsg = mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, mBuffer);
            writtenMsg.sendToTarget();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Error occurred when sending data", e);
            Message writeErrorMsg = mHandler.obtainMessage(MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast", "Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            mHandler.sendMessage(writeErrorMsg);
        }
    }

    public void cancel() {
        if (mSocket == null) {
            return;
        }
        try {
            mSocket.close();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Could not close the connect socket", e);
        }
    }
}
