package com.apidemo.mac.myfinalstydyandroid.ScreenShot;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

/**
 * Created by cqj on 2018/2/26.
 */

public class ScreenShotManager {
    private Activity activity;
    private ScreenShotObserver mInternalObserver;
    private ScreenShotObserver mExternalObserver;
    private HandlerThread mHandlerThread;
    private Handler mHandler;

    public ScreenShotManager(@NonNull Activity activity, @NonNull OnScreenshotTakenListener onScreenshotTakenListener) {
        this.activity = activity;
        mHandlerThread = new HandlerThread("Screenshot_Observer");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mInternalObserver = new ScreenShotObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, mHandler, activity, onScreenshotTakenListener);
        mExternalObserver = new ScreenShotObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mHandler, activity, onScreenshotTakenListener);
    }

    public void startWatching() {
        // 添加监听
        activity.getContentResolver().registerContentObserver(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                false,
                mInternalObserver
        );
        activity.getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                false,
                mExternalObserver
        );
    }

    public void stopWatching() {
        activity.getContentResolver().unregisterContentObserver(mInternalObserver);
        activity.getContentResolver().unregisterContentObserver(mExternalObserver);
    }

    public void onDestory() {
        mHandler.removeCallbacks(mHandlerThread);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mHandlerThread.quitSafely();
        } else {
            mHandlerThread.quit();
        }
    }

}
