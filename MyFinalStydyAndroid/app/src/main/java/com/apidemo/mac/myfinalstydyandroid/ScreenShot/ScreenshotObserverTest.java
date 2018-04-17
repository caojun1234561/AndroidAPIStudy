package com.apidemo.mac.myfinalstydyandroid.ScreenShot;

import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

/**
 * Created by cqj on 2018/2/26.
 */

public class ScreenshotObserverTest extends FileObserver {
    private static final String TAG = "ScreenshotObserverTest";
    //不同手机的文件目录不一样 所以不适用于所有情况
    private static final String PATH = Environment.getExternalStorageDirectory() + File.separator
            + Environment.DIRECTORY_DCIM + File.separator + "Screenshots" + File.separator;
    private OnScreenshotTakenListener mListener;
    private String mLastTakenPath;

    public ScreenshotObserverTest(OnScreenshotTakenListener listener) {
        super(PATH);
        Log.e("path:", PATH);
        mListener = listener;
    }


    @Override
    public void onEvent(int event, @Nullable String path) {
        Log.i(TAG, "Event:" + event + "\t" + path);
        if (path == null || event != FileObserver.CLOSE_WRITE)
            Log.i(TAG, "Don't care.");
        else if (mLastTakenPath != null && path.equalsIgnoreCase(mLastTakenPath))
            Log.i(TAG, "This event has been observed before.");
        else {
            mLastTakenPath = path;
            File file = new File(PATH + path);
           // mListener.onScreenshotTaken(Uri.fromFile(file));
            Log.i(TAG, "Send event to listener.");
        }
    }

    public void start() {
        super.startWatching();
    }

    public void stop() {
        super.stopWatching();
    }

}
