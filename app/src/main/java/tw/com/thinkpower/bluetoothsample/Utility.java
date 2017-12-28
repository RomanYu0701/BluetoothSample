package tw.com.thinkpower.bluetoothsample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by RomanYu on 2017/11/2.
 */

public class Utility {

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentServices(implicitIntent, 0);
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        ResolveInfo serviceInfo = (ResolveInfo) resolveInfo.get(0);
        ComponentName component = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

}
