package com.gank.mybodymanage;

import android.app.Application;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;

/**
 * @author Ly
 * @date 2018/2/22
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        int SERVICE_ID = 155260;
        String entry = "Ly";
        Trace mTrace = new Trace(SERVICE_ID, entry, true);
        LBSTraceClient mTraceClient = new LBSTraceClient(getApplicationContext());
        mTraceClient.startTrace(mTrace, null);
        mTraceClient.startGather(null);
    }

}
