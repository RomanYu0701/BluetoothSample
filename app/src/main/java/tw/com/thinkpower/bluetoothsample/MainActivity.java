package tw.com.thinkpower.bluetoothsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import tw.com.thinkpower.bluetoothsample.service.BleService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main-BLE";

    public void fireButton(View v){
        Log.e(TAG,"fireButton invoked!!");
        int id = v.getId();
        boolean isServiceRunning = false;
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), BleService.class);
        intent = Utility.createExplicitFromImplicitIntent(getBaseContext(), intent);
        if (intent != null) {
            Log.e(TAG, "isServiceRunning ? " + isServiceRunning);
            switch (id) {
                case R.id.start_service /*2131296360*/:
                    Log.e(TAG, "////-->start_service(1)");
                    if (!isServiceRunning) {
                        Log.e(TAG, "////-->start_service(2)");
                        startService(intent);
                        finish();
                        return;
                    }
                    return;
                case R.id.stop_service /*2131296361*/:
                    Log.e(TAG, "////-->stop service(1)");
                    if (isServiceRunning) {
                        Log.e(TAG, "////-->stop service(2)");
                        stopService(intent);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
        Toast.makeText(getBaseContext(), "Intent is null, probably is service not yet been registered..", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getBaseContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]
                                {Manifest.permission.ACCESS_COARSE_LOCATION
                                        ,Manifest.permission.ACCESS_FINE_LOCATION
                                },
                        100);
            }
        }

    }
}
