package com.gobeike.radioapp.config.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by xuff on 17-3-18.
 */

public class IntentUtils {

    public static Intent getExplicitIapIntent(Context mContext,String action) {
        PackageManager pm = mContext.getPackageManager();
        Intent implicitIntent = new Intent(action);
        List<ResolveInfo> resolveInfos = pm.queryIntentServices(implicitIntent, 0);

        // Is somebody else trying to intercept our IAP call?
        if (resolveInfos == null || resolveInfos.size() != 1) {
            return null;
        }

        ResolveInfo serviceInfo = resolveInfos.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        Intent iapIntent = new Intent();
        iapIntent.setComponent(component);
        return iapIntent;
    }
}
