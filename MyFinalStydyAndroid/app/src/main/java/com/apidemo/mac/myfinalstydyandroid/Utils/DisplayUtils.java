package com.apidemo.mac.myfinalstydyandroid.Utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by cqj on 2018/1/29.
 */

public class DisplayUtils {
    private static long availableMem = 0L;
    private static long totalMem = 0L;

    public DisplayUtils() {
    }

    public static float dip2pxWithFloatRtn(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5F;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static int getDisplayHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDisplayWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float getDensityDpi(Context context) {
        return (float)context.getResources().getDisplayMetrics().densityDpi;
    }

    public static boolean isHighDensity(Context context) {
        return getDensityDpi(context) >= 240.0F;
    }

    public static long getAvailableInternalMemorySize() {
        if(availableMem == 0L) {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = (long)stat.getBlockSize();
            long availableBlocks = (long)stat.getAvailableBlocks();
            availableMem = availableBlocks * blockSize;
        }

        return availableMem;
    }

    public static long getTotalInternalMemorySize() {
        if(totalMem == 0L) {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = (long)stat.getBlockSize();
            long totalBlocks = (long)stat.getBlockCount();
            totalMem = totalBlocks * blockSize;
        }

        return totalMem;
    }

    public static int getStatusBarHeight(Context context) {
        return (int)(25.0F * context.getResources().getDisplayMetrics().density);
    }

    public static int getRealStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int sbar = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return sbar;
    }

    public static int getContentHeight(Context context) {
        return getDisplayHeightPixels(context) - getRealStatusBarHeight(context);
    }

    public static int getContentHeight(Context context, boolean isFillScreen) {
        return isFillScreen?getDisplayHeightPixels(context):getContentHeight(context);
    }

    public static int getSmartBarHeight(Context context) {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("mz_action_button_min_height");
            int height = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(height);
        } catch (Exception var5) {
            var5.printStackTrace();
            return 0;
        }
    }
}

