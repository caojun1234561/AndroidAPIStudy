package com.apidemo.mac.myfinalstydyandroid.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by cqj on 2018/3/9.
 */

public class SystemUtils {
    static final String TAG = SystemUtils.class.getSimpleName();

    public static int getSystemMemorySize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memorySize = activityManager.getMemoryClass();
        Log.i("TAG", "memorySize:" + memorySize + "M");
        return memorySize * 1024 * 1024;
    }

}
