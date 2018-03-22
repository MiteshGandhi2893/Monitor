package in.monitor.Data;

/**
 * Created by miteshgandhi on 2/25/18.
 */

public class MotionSensor {

    private int id;
    private long sensorDataTime;
    private String date;


    private double q_w;
    private double q_x;
    private double q_y;
    private double q_z;

    private double euler0_M_PI;
    private double euler1_M_PI;
    private double euler2_M_PI;


    private double ypr0_M_PI;
    private double ypr1_M_PI;
    private double ypr2_M_PI;


    private double aa_x;
    private double aa_y;
    private double aa_z;

    private double aaReal_x;
    private double aaReal_y;
    private double aaReal_z;


    private double aaWorld_x;
    private double aaWorld_y;
    private double aaWorld_z;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSensorDataTime() {
        return sensorDataTime;
    }

    public void setSensorDataTime(long sensorDataTime) {
        this.sensorDataTime = sensorDataTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getQ_w() {
        return q_w;
    }

    public void setQ_w(double q_w) {
        this.q_w = q_w;
    }

    public double getQ_x() {
        return q_x;
    }

    public void setQ_x(double q_x) {
        this.q_x = q_x;
    }

    public double getQ_y() {
        return q_y;
    }

    public void setQ_y(double q_y) {
        this.q_y = q_y;
    }

    public double getQ_z() {
        return q_z;
    }

    public void setQ_z(double q_z) {
        this.q_z = q_z;
    }

    public double getEuler0_M_PI() {
        return euler0_M_PI;
    }

    public void setEuler0_M_PI(double euler0_M_PI) {
        this.euler0_M_PI = euler0_M_PI;
    }

    public double getEuler1_M_PI() {
        return euler1_M_PI;
    }

    public void setEuler1_M_PI(double euler1_M_PI) {
        this.euler1_M_PI = euler1_M_PI;
    }

    public double getEuler2_M_PI() {
        return euler2_M_PI;
    }

    public void setEuler2_M_PI(double euler2_M_PI) {
        this.euler2_M_PI = euler2_M_PI;
    }

    public double getYpr0_M_PI() {
        return ypr0_M_PI;
    }

    public void setYpr0_M_PI(double ypr0_M_PI) {
        this.ypr0_M_PI = ypr0_M_PI;
    }

    public double getYpr1_M_PI() {
        return ypr1_M_PI;
    }

    public void setYpr1_M_PI(double ypr1_M_PI) {
        this.ypr1_M_PI = ypr1_M_PI;
    }

    public double getYpr2_M_PI() {
        return ypr2_M_PI;
    }

    public void setYpr2_M_PI(double ypr2_M_PI) {
        this.ypr2_M_PI = ypr2_M_PI;
    }

    public double getAa_x() {
        return aa_x;
    }

    public void setAa_x(double aa_x) {
        this.aa_x = aa_x;
    }

    public double getAa_y() {
        return aa_y;
    }

    public void setAa_y(double aa_y) {
        this.aa_y = aa_y;
    }

    public double getAa_z() {
        return aa_z;
    }

    public void setAa_z(double aa_z) {
        this.aa_z = aa_z;
    }

    public double getAaReal_x() {
        return aaReal_x;
    }

    public void setAaReal_x(double aaReal_x) {
        this.aaReal_x = aaReal_x;
    }

    public double getAaReal_y() {
        return aaReal_y;
    }

    public void setAaReal_y(double aaReal_y) {
        this.aaReal_y = aaReal_y;
    }

    public double getAaReal_z() {
        return aaReal_z;
    }

    public void setAaReal_z(double aaReal_z) {
        this.aaReal_z = aaReal_z;
    }

    public double getAaWorld_x() {
        return aaWorld_x;
    }

    public void setAaWorld_x(double aaWorld_x) {
        this.aaWorld_x = aaWorld_x;
    }

    public double getAaWorld_y() {
        return aaWorld_y;
    }

    public void setAaWorld_y(double aaWorld_y) {
        this.aaWorld_y = aaWorld_y;
    }

    public double getAaWorld_z() {
        return aaWorld_z;
    }

    public void setAaWorld_z(double aaWorld_z) {
        this.aaWorld_z = aaWorld_z;
    }
}
