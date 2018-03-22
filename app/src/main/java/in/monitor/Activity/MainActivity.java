package in.monitor.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import in.monitor.Data.HeartBeatSensor;
import in.monitor.Data.MotionSensor;
import in.monitor.Data.MyDbHandler;
import in.monitor.Util.BluetoothConnectionHandler;
import in.monitor.Util.ConnectThread;
import in.monitor.Util.ConnectedThread;
import in.monitor.R;
//import in.monitor.R;


public class MainActivity extends AppCompatActivity implements BluetoothConnectionHandler {

    public static final String TAG = "BLUETOOTH_TEST";
    public static final String SPP_UUID = "00001101-0000-1000-8000-00805f9b34fb";
    public static final int MAX_Y = 1024;
    public static final int BUFF_SIZE = MAX_Y;
    private static final int REQUEST_ENABLE_BT = 13423;
    private static final int MAX_X = 800;
    private View mLayoutMain;
    private View mLayoutProgress;
    private TextView mTextViewProgress;
    private BluetoothAdapter mBluetoothAdapter;
    private ListView mListViewDevices;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private TextView mTextViewTitle;
    private TextView mTextViewSubtitle;
    private ArrayAdapter<String> mAdapter;
    private GraphView mChartSData;
    private GraphView mChartMData;
    private GraphView mChartBData;
    private GraphView mChartQData;
    private long mSTime;
    private long mMTime;
    private long mBTime;
    private Button showData;
    private long mQTime;
    private LineGraphSeries<DataPoint> mSDataSeries;
    private LineGraphSeries<DataPoint> mMDataSeries;
    private LineGraphSeries<DataPoint> mBDataSeries;
    private LineGraphSeries<DataPoint> mQDataSeries;
    private TextView mTextViewGyroData;
    private DatabaseReference heartBeat_S_Reference;
    private DatabaseReference heartBeat_M_Reference;
    private MyDbHandler myDbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_main);

        initViews();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();



        // Check if device supports bluetooth
        showProgress("Checking if bluetooth is present...");
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            hideProgress();
            safelyExit(R.string.error, R.string.bluetooth_not_supported, R.string.ok);
        }

        // Check if bluetooth is enabled
        showProgress("Checking if bluetooth is enabled...");
        if (!mBluetoothAdapter.isEnabled()) {
            hideProgress();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            hideProgress();
            showConnectedDevices();
            myDbHandler=new MyDbHandler(getApplicationContext(),"HeartBeatSensor_S",null,1);


           /* heartBeat_S_Reference= FirebaseDatabase.getInstance().getReference("HeartBeatSensor_S/HeartBeatSensorSData_"+formatedDate);
            heartBeat_M_Reference= FirebaseDatabase.getInstance().getReference("HeartBeatSensor_M/HeartBeatSensorMData_"+formatedDate);*/
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mConnectThread != null) {
            mConnectThread.interrupt();
            mConnectThread.cancel();
        }
       if (mConnectedThread != null) {
            mConnectedThread.interrupt();
            mConnectedThread.cancel();
        }
    }

    private void initViews() {
        mLayoutMain = findViewById(R.id.layoutMain);
        mLayoutProgress = findViewById(R.id.layoutProgress);
        mTextViewProgress = findViewById(R.id.textViewProgress);
        mListViewDevices = findViewById(R.id.listViewDevices);
        mTextViewTitle = findViewById(R.id.textViewTitle);
        mTextViewSubtitle = findViewById(R.id.textViewSubtitle);
        mChartSData = findViewById(R.id.chart1);
        mChartMData = findViewById(R.id.chart2);
        mChartBData = findViewById(R.id.chart3);
        mChartQData = findViewById(R.id.chart4);
        showData=findViewById(R.id.showData);

        mChartSData.getViewport().setXAxisBoundsManual(true);
        mChartSData.getViewport().setMaxX(MAX_X);
        mChartSData.getViewport().setScalable(true);
        mChartSData.getViewport().setYAxisBoundsManual(true);
        mChartSData.getViewport().setMinY(0);
        mChartSData.getViewport().setMaxY(MAX_Y);
        mSDataSeries = new LineGraphSeries<>();
        mChartSData.addSeries(mSDataSeries);

        mChartMData.getViewport().setXAxisBoundsManual(true);
        mChartMData.getViewport().setMaxX(MAX_X);
        mChartMData.getViewport().setScalable(true);
        mChartMData.getViewport().setYAxisBoundsManual(true);
        mChartMData.getViewport().setMinY(0);
        mChartMData.getViewport().setMaxY(MAX_Y);
        mMDataSeries = new LineGraphSeries<>();
        mChartMData.addSeries(mMDataSeries);

        mChartBData.getViewport().setXAxisBoundsManual(true);
        mChartBData.getViewport().setMaxX(MAX_X);
        mChartBData.getViewport().setScalable(true);
        mChartBData.getViewport().setYAxisBoundsManual(true);
        mChartBData.getViewport().setMinY(0);
        mChartBData.getViewport().setMaxY(MAX_Y);
        mBDataSeries = new LineGraphSeries<>();
        mChartBData.addSeries(mBDataSeries);

        mChartQData.getViewport().setXAxisBoundsManual(true);
        mChartQData.getViewport().setMaxX(MAX_X);
        mChartQData.getViewport().setScalable(true);
        mChartQData.getViewport().setYAxisBoundsManual(true);
        mChartQData.getViewport().setMinY(0);
        mChartQData.getViewport().setMaxY(MAX_Y);
        mQDataSeries = new LineGraphSeries<>();
        mChartQData.addSeries(mQDataSeries);

        mTextViewGyroData = findViewById(R.id.textViewGyroData);

    }

    private void showProgress(String message) {
        mLayoutMain.setVisibility(View.GONE);
        mLayoutProgress.setVisibility(View.VISIBLE);
        mTextViewProgress.setText(message);
    }

    private void hideProgress() {
        mLayoutMain.setVisibility(View.VISIBLE);
        mLayoutProgress.setVisibility(View.GONE);
        mTextViewProgress.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                showConnectedDevices();
            } else {
                safelyExit(R.string.error, R.string.bluetooth_permission_denied, R.string.ok);
            }
        }
    }

    private void showConnectedDevices() {
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        List<String> deviceNames = new ArrayList<>();
        final List<BluetoothDevice> bluetoothDevices = new ArrayList<>();
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (Arrays.asList(bluetoothDevice.getUuids()).contains(ParcelUuid.fromString(SPP_UUID))) {
                deviceNames.add(bluetoothDevice.getName());
                bluetoothDevices.add(bluetoothDevice);
            }
        }
        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                deviceNames);
        mListViewDevices.setAdapter(mAdapter);
        mListViewDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deviceSelected(bluetoothDevices.get(position));

/*                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("ddMMMyyyy");
                String formatedDate=dateFormat.format(c.getTime());
*/


            }
        });
    }

    private void deviceSelected(BluetoothDevice bluetoothDevice) {
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
        mTextViewTitle.setText(bluetoothDevice.getName());
        mTextViewSubtitle.setText(bluetoothDevice.getAddress());
        showProgress("Connecting...");
        mConnectThread = new ConnectThread(bluetoothDevice, this);
        mConnectThread.start();
    }

    private void safelyExit(int titleResId, int messageResId, int buttonResId) {
        new AlertDialog.Builder(this)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setCancelable(false)
                .setPositiveButton(buttonResId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void manageMyConnectedSocket(final BluetoothSocket bluetoothSocket) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                mListViewDevices.setVisibility(View.GONE);
                mChartSData.setVisibility(View.VISIBLE);
                mChartMData.setVisibility(View.VISIBLE);
                mChartBData.setVisibility(View.VISIBLE);
                mChartQData.setVisibility(View.VISIBLE);
            }
        });
        mSTime = 0;
        mMTime = 0;
        mBTime = 0;
        mQTime = 0;
        mConnectedThread = new ConnectedThread(bluetoothSocket, new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(final Message msg) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("ddMMMyyyy");
                final String formatedDate=dateFormat.format(c.getTime());
                final long milliseconds=new Date().getTime();



                switch (msg.what) {
                    case MessageConstants.MESSAGE_READ:
                        final String data = (String) msg.obj;
                        if (data.startsWith("S")) {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    long data_S=Long.parseLong(data.substring(1));



                                    DataPoint dataPoint = new DataPoint(mSTime, Integer.parseInt(data.substring(1)));
                                    mSDataSeries.appendData(dataPoint, true, MAX_X);
                                    mSTime++;


                                    HeartBeatSensor heartBeatSensor_S=new HeartBeatSensor(data_S,milliseconds,formatedDate);
                                    myDbHandler.addHeartSensorData(heartBeatSensor_S,"S");




                                }
                            });



                        } else if (data.startsWith("M")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    long data_M=Long.parseLong(data.substring(1));

                                    DataPoint dataPoint = new DataPoint(mMTime, Integer.parseInt(data.substring(1)));
                                    mMDataSeries.appendData(dataPoint, true, MAX_X);
                                    mMTime++;

                                    HeartBeatSensor heartBeatSensor_M=new HeartBeatSensor(data_M,milliseconds,formatedDate);
                                    myDbHandler.addHeartSensorData(heartBeatSensor_M,"M");

                                }
                            });




                        } else if (data.startsWith("B")) {
                            DataPoint dataPoint = new DataPoint(mBTime, Integer.parseInt(data.substring(1)));
                            mBDataSeries.appendData(dataPoint, true, MAX_X);
                            mBTime++;
                        } else if (data.startsWith("Q")) {
                            DataPoint dataPoint = new DataPoint(mQTime, Integer.parseInt(data.substring(1)));
                            mQDataSeries.appendData(dataPoint, true, MAX_X);
                            mQTime++;
                        } else if (data.startsWith("aaAll")) {
                            mTextViewGyroData.setText(data);
                            final String[] motiondata=data.split("\t");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    MotionSensor motionSensor=new MotionSensor();

                                    for(int i=1;i<motiondata.length;i++)
                                    {
                                        if(motiondata[i].equals("nan")||motiondata[i]==null)
                                        {
                                            motiondata[i]="0";
                                        }

                                    }

                                    motionSensor.setQ_w(Double.parseDouble(motiondata[1]));
                                    motionSensor.setQ_x(Double.parseDouble(motiondata[2]));
                                    motionSensor.setQ_y(Double.parseDouble(motiondata[3]));
                                    motionSensor.setQ_z(Double.parseDouble(motiondata[4]));

                                    motionSensor.setEuler0_M_PI(Double.parseDouble(motiondata[4]));
                                    motionSensor.setEuler1_M_PI(Double.parseDouble(motiondata[5]));
                                    motionSensor.setEuler2_M_PI(Double.parseDouble(motiondata[6]));

                                    motionSensor.setYpr0_M_PI(Double.parseDouble(motiondata[7]));
                                    motionSensor.setYpr1_M_PI(Double.parseDouble(motiondata[8]));
                                    motionSensor.setYpr2_M_PI(Double.parseDouble(motiondata[9]));

                                    motionSensor.setAa_x(Double.parseDouble(motiondata[10]));
                                    motionSensor.setAa_y(Double.parseDouble(motiondata[11]));
                                    motionSensor.setAa_z(Double.parseDouble(motiondata[12]));


                                    motionSensor.setAaReal_x(Double.parseDouble(motiondata[13]));
                                    motionSensor.setAaReal_y(Double.parseDouble(motiondata[14]));
                                    motionSensor.setAaReal_z(Double.parseDouble(motiondata[15]));


                                    motionSensor.setAaWorld_x(Double.parseDouble(motiondata[16]));
                                    motionSensor.setAaWorld_y(Double.parseDouble(motiondata[17]));
                                    motionSensor.setAaWorld_z(Double.parseDouble(motiondata[18]));

                                    motionSensor.setDate(formatedDate);
                                    motionSensor.setSensorDataTime(milliseconds);


                                    myDbHandler.addMotionSensorData(motionSensor);







                                    showData.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            try {
                                                String showdata = myDbHandler.showMessage(myDbHandler.getAllHeartbeatData("S"));
                                                showdata="HeartBeatSensor S:\n"+showdata+"\nHeartBEatData  M:\n"+myDbHandler.showMessage(myDbHandler.getAllHeartbeatData("M"))+"\nMotionSensor:"+myDbHandler.showMotionSensorMessage(myDbHandler.getAllMotionSensorData());
                                                //mTextViewGyroData.setText(showdata);
                                                // Toast.makeText(getApplicationContext(),showdata,Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MainActivity.this, showData.class);
                                                intent.putExtra("Data", showdata);
                                                startActivity(intent);
                                            }
                                            catch(Exception ex)
                                            {
                                                ex.printStackTrace();
                                            }
                                        }
                                    });



                                }
                            });

                        }
                        else {
                            Log.d(TAG, "Other Data: " + msg.obj);
                        }
                        //mAdapter.add((String) msg.obj);
                        //mAdapter.notifyDataSetChanged();
                        break;
                }



            }
        });
        mConnectedThread.start();
    }

    // Defines several constants used when transmitting messages between the service and the UI.
    public interface MessageConstants {
        int MESSAGE_READ = 0;
        int MESSAGE_WRITE = 1;
        int MESSAGE_TOAST = 2;
    }
}
