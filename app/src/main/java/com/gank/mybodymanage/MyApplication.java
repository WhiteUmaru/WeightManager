package com.gank.mybodymanage;

import android.app.Application;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author Ly
 * @date 2018/2/22
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        int SERVICE_ID = 155260;
        String entry = "Ly" + Build.MODEL;
        entry = entry.replaceAll(" ", "");
        Log.e("app", "onCreate: " + entry);
        Trace mTrace = new Trace(SERVICE_ID, entry, true);
        LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
        mTraceClient.startTrace(mTrace, null);
        mTraceClient.startGather(null);
        CrashReport.initCrashReport(getApplicationContext(), "b8f3ce2a2b", false);
    }
}
