package tw.com.thinkpower.bluetoothsample.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanFilter.Builder;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.neovisionaries.bluetooth.ble.advertising.ADPayloadParser;
import com.neovisionaries.bluetooth.ble.advertising.ADStructure;
import com.neovisionaries.bluetooth.ble.advertising.IBeacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by RomanYu on 2017/11/2.
 */

public class BleService extends Service {

    private static final String TAG = "BleService";

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand invoked!!");
        startScan(null, null);
        return super.onStartCommand(intent, flags, startId);
    }

    public void startScan(String name, String address) {
        Log.d(TAG, "startScan invoked!!");
        Toast.makeText(getBaseContext(), "Start Scan...", Toast.LENGTH_SHORT).show();
        Builder filterBuilder = new Builder();
        if (name != null) {
            filterBuilder.setDeviceName(name);
        }
        if (address != null) {
            filterBuilder.setDeviceAddress(address);
        }
        ScanSettings.Builder settingBuilder = new ScanSettings.Builder();
        settingBuilder.setScanMode(2);
        List<ScanFilter> list = new ArrayList<>();
        list.add(filterBuilder.build());
        this.callback = new InnerCallback(getBaseContext());
        this.scanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();
        this.scanner.startScan(list, settingBuilder.build(), this.callback);
    }

    public BleService() {
        this.scanner = null;
        this.callback = null;
        this.myBinder = new LocalBinder();
    }

    private class InnerCallback extends ScanCallback {
        private final String TAG;
        private Context context;
        private boolean isActivityShown;

        public InnerCallback(Context context) {
            this.isActivityShown = false;
            this.TAG = InnerCallback.class.getSimpleName();
            this.context = context;
            this.isActivityShown = false;
        }

        public void onScanResult(int callbackType, ScanResult result) {
            Log.e(this.TAG, "onScanResult invoked!!");
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            String address = device.getAddress();
            Log.e(TAG,"device name : "+device.getName());
            Log.e(TAG,"device addr : "+device.getAddress());
            for (ADStructure structure : ADPayloadParser.getInstance().parse(result.getScanRecord().getBytes())) {
                if (structure instanceof IBeacon) {
                    IBeacon iBeacon = (IBeacon) structure;
                    UUID uuid = iBeacon.getUUID();
                    Log.e(this.TAG, "UUID(nv-bluetooth) : " + uuid.toString());
                    int major = iBeacon.getMajor();
                    Log.e(this.TAG, "major : " + major);
                    int minor = iBeacon.getMinor();
                    Log.e(this.TAG, "minor : " + minor);
                    int power = iBeacon.getPower();
                    Log.e(this.TAG, "power : " + power);
                    BleBean bean = new BleBean();
                    bean.setMacAddress(address);
                    bean.setName(device.getName());
                    bean.setRssi(result.getRssi());
                    bean.setType(device.getType());
                    bean.setUuid(uuid.toString().toUpperCase(Locale.TAIWAN));
                    bean.setMajor(major);
                    bean.setMinor(minor);
                    bean.setTxPower(power);
                    if (!this.isActivityShown) {
                        this.isActivityShown = true;
                        BleService.this.showMessageActivity();
                    }
                }
            }
        }

        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.d(this.TAG, "onBatchScanResults invoked!!");
        }

        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d(this.TAG, "onScanFailed(" + errorCode + ") invoked!!");
        }
    }

    private LocalBinder myBinder = null;
    private InnerCallback callback = null;
    private BluetoothLeScanner scanner = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.myBinder;
    }

    public void stopScan() {
        boolean z = true;
        Log.d(TAG, "stopScan invoked!!");
        Toast.makeText(getBaseContext(), "Stop Scan...", Toast.LENGTH_SHORT).show();
        if (this.callback == null || this.scanner == null) {
            boolean z2;
            StringBuilder stringBuilder = new StringBuilder("callback is null ? ");
            z2 = this.callback == null;
            Log.e(TAG, stringBuilder.append(z2).toString());
            StringBuilder stringBuilder2 = new StringBuilder("scanner is null ? ");
            if (this.scanner != null) {
                z = false;
            }
            Log.e(TAG, stringBuilder2.append(z).toString());
            return;
        }
        this.scanner.stopScan(this.callback);
    }

    public void showMessageActivity() {
        Log.e(TAG, "showMessageActivity invoked!!");
        stopScan();
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setClass(getBaseContext(), MessageActivity.class);
//        getApplicationContext().startActivity(intent);
    }

    public class LocalBinder extends Binder {
        public BleService getService() {
            return BleService.this;
        }
    }
}
