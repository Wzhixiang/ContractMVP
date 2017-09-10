package com.wzx.contractmvp;

import android.app.Application;
import android.content.Context;

/**
 * 描述 TODO
 * Created by 王治湘 on 2017/9/10.
 * version 1.0
 */

public class MyApplication extends Application {
    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Context getContext() {
        return mApplication.getApplicationContext();
    }
}
