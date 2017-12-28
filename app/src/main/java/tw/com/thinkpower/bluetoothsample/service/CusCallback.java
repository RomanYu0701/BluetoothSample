package tw.com.thinkpower.bluetoothsample.service;

import android.bluetooth.le.ScanCallback;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by RomanYu on 2017/11/2.
 */

public class CusCallback extends ScanCallback implements ServiceConnection {


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
