package in.monitor.Util;

/**
 * Created by miteshgandhi on 2/25/18.
 */

public class DbConstants {

    public static final int DATABASE_VERSION=7;

    public static  final String DATABASE_NAME="SensorData.db";


    //Table name for  different Sensors

    public static final String TABLE_HEART_S="HeartBeatSensor_S";
    public static final String TABLE_HEART_M="HeartBeatSensor_M";
    public static final String TABLE_MOTIONSENSOR="MotionSensorData";


    //Column names for  HeartBeat Sensors

    public static final String COULMN_ID="ID";
    public static final String COULMN_SENSORDATA="SensorData";
    public static final String COLUMN_DATATIMESTAMP="TimeStamp";
    public static final String COLUMN_DATE="SensorDataDate";


    //Column names for  Motion Sensors


    public static final String Q_W="q_w";
    public static final String Q_X="q_x";
    public static final String Q_Y="q_y";
    public static final String Q_Z="q_z";

    public static final String EULER0_M_PI="euler0_M_PI";
    public static final String EULER1_M_PI="euler1_M_PI";
    public static final String EULER2_M_PI="euler2_M_PI";


    public static final String YPR0_M_PI="ypr0_M_PI";
    public static final String YPR1_M_PI="ypr1_M_PI";
    public static final String YPR2_M_PI="ypr2_M_PI";


    public static final String AA_X="aa_x";
    public static final String AA_Y="aa_y";
    public static final String AA_Z="aa_z";

    public static final String AAREAL_X="aaReal_x";
    public static final String AAREAL_Y="aaReal_y";
    public static final String AAREAL_Z="aaReal_z";


    public static final String AAWORLD_X="aaWorld_x";
    public static final String AAWORLD_Y="aaWorld_y";
    public static final String AAWORLD_Z="aaWorld_z";



























}
