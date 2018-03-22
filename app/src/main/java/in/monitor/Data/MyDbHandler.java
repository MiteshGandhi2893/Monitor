package in.monitor.Data;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.monitor.Util.DbConstants;

/**
 * Created by miteshgandhi on 2/4/18.
 */






public class MyDbHandler extends SQLiteOpenHelper {


    Context context;

    public MyDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, DbConstants.DATABASE_VERSION);
        this.context=context;
     //   this.DATABASE_NAME=name;

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try
        {
        String query="CREATE TABLE "+DbConstants.TABLE_HEART_S+
                " ("+DbConstants.COULMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +DbConstants.COULMN_SENSORDATA+" INTEGER,"+DbConstants.COLUMN_DATATIMESTAMP+" INTEGER,"+DbConstants.COLUMN_DATE+" TEXT)";
            sqLiteDatabase.execSQL(query);



            query="CREATE TABLE "+DbConstants.TABLE_HEART_M +
                    " ("+DbConstants.COULMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +DbConstants.COULMN_SENSORDATA+" INTEGER,"+DbConstants.COLUMN_DATATIMESTAMP+" INTEGER,"+DbConstants.COLUMN_DATE+" TEXT)";
            sqLiteDatabase.execSQL(query);


            query= "CREATE TABLE " + DbConstants.TABLE_MOTIONSENSOR +
                    " (" + DbConstants.COULMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DbConstants.Q_W + " FLOAT,"
                    + DbConstants.Q_X + " FLOAT,"
                    + DbConstants.Q_Y + " FLOAT,"
                    + DbConstants.Q_Z + " FLOAT,"
                    + DbConstants.EULER0_M_PI + " FLOAT,"
                    + DbConstants.EULER1_M_PI + " FLOAT,"
                    + DbConstants.EULER2_M_PI + " FLOAT,"
                    + DbConstants.YPR0_M_PI + " FLOAT,"
                    + DbConstants.YPR1_M_PI + " FLOAT,"
                    + DbConstants.YPR2_M_PI + " FLOAT,"
                    + DbConstants.AA_X + " FLOAT,"
                    + DbConstants.AA_Y + " FLOAT,"
                    + DbConstants.AA_Z + " FLOAT,"
                    + DbConstants.AAREAL_X + " FLOAT,"
                    + DbConstants.AAREAL_Y + " FLOAT,"
                    + DbConstants.AAREAL_Z + " FLOAT,"
                    + DbConstants.AAWORLD_X + " FLOAT,"
                    + DbConstants.AAWORLD_Y + " FLOAT,"
                    + DbConstants.AAWORLD_Z+" FLOAT,"
                    + DbConstants.COLUMN_DATATIMESTAMP + " INTEGER," + DbConstants.COLUMN_DATE + " TEXT)";
            sqLiteDatabase.execSQL(query);






        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DbConstants.TABLE_HEART_S);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DbConstants.TABLE_HEART_M);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DbConstants.TABLE_MOTIONSENSOR);
        onCreate(sqLiteDatabase);

    }

public void addHeartSensorData(HeartBeatSensor sensor,String type)
{
    try {

        ContentValues values = new ContentValues();
        values.put(DbConstants.COULMN_SENSORDATA, sensor.getSensorData());
        values.put(DbConstants.COLUMN_DATATIMESTAMP, sensor.getSensorDataTime());
        values.put(DbConstants.COLUMN_DATE, sensor.getDate());

        SQLiteDatabase db = getWritableDatabase();


        if (type.equalsIgnoreCase("S"))
        {

            db.insert(DbConstants.TABLE_HEART_S, null, values);

        }
        else if (type.equalsIgnoreCase("M"))
        {
            db.insert(DbConstants.TABLE_HEART_M, null, values);


        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }

}
    public void addMotionSensorData(MotionSensor sensor)
    {
        try {

            ContentValues values = new ContentValues();
           values.put(DbConstants.Q_W,sensor.getQ_w());
            values.put(DbConstants.Q_X,sensor.getQ_x());
            values.put(DbConstants.Q_Y,sensor.getQ_y());
            values.put(DbConstants.Q_Z,sensor.getQ_z());
            values.put(DbConstants.EULER0_M_PI,sensor.getEuler0_M_PI());
            values.put(DbConstants.EULER1_M_PI,sensor.getEuler1_M_PI());
            values.put(DbConstants.EULER2_M_PI,sensor.getEuler2_M_PI());
            values.put(DbConstants.YPR0_M_PI,sensor.getYpr0_M_PI());
            values.put(DbConstants.YPR1_M_PI,sensor.getYpr1_M_PI());
            values.put(DbConstants.YPR2_M_PI,sensor.getYpr2_M_PI());
            values.put(DbConstants.AA_X,sensor.getAa_x());
            values.put(DbConstants.AA_Y,sensor.getAa_y());
            values.put(DbConstants.AA_Z,sensor.getAa_z());
            values.put(DbConstants.AAREAL_X,sensor.getAaReal_x());
            values.put(DbConstants.AAREAL_Y,sensor.getAaReal_y());
            values.put(DbConstants.AAREAL_Z,sensor.getAaReal_z());
            values.put(DbConstants.AAWORLD_X,sensor.getAaWorld_x());
            values.put(DbConstants.AAWORLD_Y,sensor.getAaWorld_y());
            values.put(DbConstants.AAWORLD_Z,sensor.getAaWorld_z());
            values.put(DbConstants.COLUMN_DATATIMESTAMP,sensor.getSensorDataTime());
            values.put(DbConstants.COLUMN_DATE,sensor.getDate());

            SQLiteDatabase db = getWritableDatabase();

            db.insert(DbConstants.TABLE_MOTIONSENSOR, null, values);


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public int getHeartSensorDataCount(String type)
    {
        String table_name="";

        if(type.equals("S"))
        {
            table_name=DbConstants.TABLE_HEART_S;
        }
        else if(type.equals("M"))
        {
            table_name=DbConstants.TABLE_HEART_M;
        }
        String query="Select * from "+table_name;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(query,null);
        return cursor.getCount();

    }


    public int getMotionSensorDataCount(String type)
    {

        String query="Select * from "+DbConstants.TABLE_MOTIONSENSOR;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(query,null);
        return cursor.getCount();

    }



    public List<HeartBeatSensor> getAllHeartbeatData(String type)
    {

        List<HeartBeatSensor>heartBeatSensors=new ArrayList<HeartBeatSensor>();
        SQLiteDatabase db=this.getReadableDatabase();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("ddMMMyyyy");
        String formatedDate=dateFormat.format(c.getTime());
        String tablename="";

        if(type.equals("S"))
        {
            tablename=DbConstants.TABLE_HEART_S;
        }
        else if(type.equals("M"))
        {
            tablename=DbConstants.TABLE_HEART_M;
        }




        Cursor cursor=db.query(tablename,new String[]{DbConstants.COULMN_ID,DbConstants.COULMN_SENSORDATA,DbConstants.COLUMN_DATATIMESTAMP,DbConstants.COLUMN_DATE},null,null,null,null,DbConstants.COULMN_ID+" DESC","1");
        if(cursor.moveToFirst())
        {
            do {

                HeartBeatSensor heartBeatSensor=new HeartBeatSensor();
                heartBeatSensor.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbConstants.COULMN_ID))));
                heartBeatSensor.setSensorData(Long.parseLong(cursor.getString(cursor.getColumnIndex(DbConstants.COULMN_SENSORDATA))));
                heartBeatSensor.setSensorDataTime(Long.parseLong(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_DATATIMESTAMP))));
                heartBeatSensor.setDate((cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_DATE))));

               /* java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                String formatedDate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                grocery.setGrocery_Date(formatedDate);*/

                heartBeatSensors.add(heartBeatSensor);


            }while(cursor.moveToNext());
        }


        return heartBeatSensors;


    }
    public List<MotionSensor> getAllMotionSensorData()
    {

        List<MotionSensor>motionSensors=new ArrayList<MotionSensor>();
        SQLiteDatabase db=this.getReadableDatabase();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("ddMMMyyyy");
        String formatedDate=dateFormat.format(c.getTime());


        Cursor cursor=db.query(DbConstants.TABLE_MOTIONSENSOR,new String[]{DbConstants.COULMN_ID,DbConstants.Q_W,DbConstants.Q_X,DbConstants.Q_Y,
                DbConstants.Q_Z,DbConstants.EULER0_M_PI,DbConstants.EULER1_M_PI,DbConstants.EULER2_M_PI,DbConstants.YPR0_M_PI,DbConstants.YPR1_M_PI,
                DbConstants.YPR2_M_PI,DbConstants.AA_X,DbConstants.AA_Y,DbConstants.AA_Z,DbConstants.AAREAL_X,DbConstants.AAREAL_Y,DbConstants.AAREAL_Z,
                DbConstants.AAWORLD_X,DbConstants.AAWORLD_Y,DbConstants.AAWORLD_Z, DbConstants.COLUMN_DATATIMESTAMP,DbConstants.COLUMN_DATE},
                null,null,null,null,DbConstants.COULMN_ID+" DESC","3");

        if(cursor.moveToFirst())
        {
            do {

                MotionSensor motionSensor=new MotionSensor();
                motionSensor.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbConstants.COULMN_ID))));
                motionSensor.setQ_w((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.Q_W)))));
                motionSensor.setQ_x((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.Q_X)))));
                motionSensor.setQ_z((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.Q_Z)))));
                motionSensor.setQ_y((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.Q_Y)))));

                motionSensor.setEuler0_M_PI((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.EULER0_M_PI)))));
                motionSensor.setEuler1_M_PI((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.EULER1_M_PI)))));
                motionSensor.setEuler2_M_PI((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.EULER2_M_PI)))));

                motionSensor.setYpr0_M_PI((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.YPR0_M_PI)))));
                motionSensor.setYpr1_M_PI((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.YPR1_M_PI)))));
                motionSensor.setYpr2_M_PI((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.YPR2_M_PI)))));

                motionSensor.setAa_x((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AA_X)))));
                motionSensor.setAa_y((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AA_Y)))));
                motionSensor.setAa_z((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AA_Z)))));

                motionSensor.setAaReal_x((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AAREAL_X)))));
                motionSensor.setAaReal_y((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AAREAL_Y)))));
                motionSensor.setAaReal_z((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AAREAL_Z)))));

                motionSensor.setAaWorld_x((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AAWORLD_X)))));
                motionSensor.setAaWorld_y((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AAWORLD_Y)))));
                motionSensor.setAaWorld_z((Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbConstants.AAWORLD_Z)))));


                motionSensor.setSensorDataTime(Long.parseLong(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_DATATIMESTAMP))));
                motionSensor.setDate((cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_DATE))));
               /* java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                String formatedDate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                grocery.setGrocery_Date(formatedDate);*/

                motionSensors.add(motionSensor);

            }while(cursor.moveToNext());
        }

        return motionSensors;

    }
    public String showMessage(List<HeartBeatSensor>heartBeatSensors)
    {
        StringBuilder sb=new StringBuilder();

        for(HeartBeatSensor heartBeatSensor : heartBeatSensors)
        {
             sb.append("\n HeartBeat Data : "+heartBeatSensor.getSensorData()+"\n TimeStamp : "+heartBeatSensor.getSensorDataTime()+"\n Date :"+heartBeatSensor.getDate());

        }
           return sb.toString();

    }
    public String showMotionSensorMessage(List<MotionSensor>motionSensors)
    {
        StringBuilder sb=new StringBuilder();

        for(MotionSensor motionSensor : motionSensors)
        {
            sb.append("\n MotionSensor Data : "+
                    motionSensor.getQ_w()
                    + "," +motionSensor.getQ_x()
                    + "," +motionSensor.getQ_y()
                    + "," +motionSensor.getQ_z()
                    + "," +motionSensor.getEuler0_M_PI()
                    + "," +motionSensor.getEuler1_M_PI()
                    + "," +motionSensor.getEuler2_M_PI()
                    + "," +motionSensor.getYpr0_M_PI()
                    + "," +motionSensor.getYpr1_M_PI()
                    + "," +motionSensor.getYpr2_M_PI()
                    + "," +motionSensor.getAa_x()
                    + "," +motionSensor.getAa_y()
                    + "," +motionSensor.getAa_z()
                    + "," +motionSensor.getAaReal_x()
                    + "," +motionSensor.getAaReal_y()
                    + "," +motionSensor.getAaReal_z()
                    + "," +motionSensor.getAaWorld_x()
                    + "," +motionSensor.getAaWorld_y()
                    + "," +motionSensor.getAaWorld_z()

                    +"\n TimeStamp : "+motionSensor.getSensorDataTime()+"\n Date :"+motionSensor.getDate());

        }
        return sb.toString();

    }



}
