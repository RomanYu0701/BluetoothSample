package tw.com.thinkpower.bluetoothsample.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by RomanYu on 2017/11/2.
 */

public class BleBean {
    private String deviceType;
    private String macAddress;
    private int major;
    private int minor;
    private String name;
    private int rssi;
    private int txPower;
    private int type;
    private String uuid;

    public BleBean() {
        this.name = null;
        this.uuid = null;
        this.major = 0;
        this.minor = 0;
        this.macAddress = null;
        this.rssi = 0;
        this.type = 0;
        this.deviceType = "UNKNOWN";
        this.txPower = 0;
    }

    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }

    public int getTxPower() {
        return this.txPower;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMajor() {
        return this.major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public double calDistance(double txPower) {
        if (this.rssi == 0) {
            return -1.0d;
        }
        double ratio = ((double) this.rssi) / txPower;
        if (ratio < 1.0d) {
            return Math.pow(ratio, 10.0d);
        }
        return (0.89976d * Math.pow(ratio, 7.7095d)) + 0.111d;
    }

    public double calDistance() {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(df.format(calDistance(-59.0d)));
    }
}
