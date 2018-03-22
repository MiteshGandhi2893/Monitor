package in.monitor.Data;

import java.util.Date;

/**
 * Created by miteshgandhi on 2/4/18.
 */

public class HeartBeatSensor {

    public long sensorData;
    public long sensorDataTime;
    public String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;



    public HeartBeatSensor()
    {

    }
    public HeartBeatSensor(long sensorDataIn, long sensorDataTimeIn,String dateIn) {
        sensorData = sensorDataIn;
        sensorDataTime=sensorDataTimeIn;

        date=dateIn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public long getSensorData() {
        return sensorData;
    }

    public void setSensorData(long sensorData) {
        this.sensorData = sensorData;
    }

    public long getSensorDataTime() {
        return sensorDataTime;
    }

    public void setSensorDataTime(long sensorDataTime) {
        this.sensorDataTime = sensorDataTime;
    }
}
