package com.apidemo.mac.myfinalstydyandroid.ScreenShot;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by cqj on 2018/2/26.
 * 媒体内容观察者(观察媒体数据库的改变)
 */

public class ScreenShotObserver extends ContentObserver {
    private Activity activity;
    private OnScreenshotTakenListener onScreenshotTakenListener;
    private static final String[] KEYWORDS = {
            "screenshot", "screen_shot", "screen-shot", "screen shot",
            "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap"
    };

    /**
     * 读取媒体数据库时需要读取的列
     */
    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
    };

    private static final String TAG = "ScreenShotObserver";
    private Uri mContentUri;


    public ScreenShotObserver(Uri contentUri, Handler handler, Activity activity, OnScreenshotTakenListener onScreenshotTakenListener) {
        super(handler);
        this.activity = activity;
        mContentUri = contentUri;
        this.onScreenshotTakenListener = onScreenshotTakenListener;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d(TAG, mContentUri.toString());
        handleMediaContentChange(mContentUri);
    }

    private void handleMediaContentChange(Uri contentUri) {
        Cursor cursor = null;
        try {
            // 数据改变时查询数据库中最后加入的一条数据
            cursor = activity.getContentResolver().query(
                    contentUri,
                    MEDIA_PROJECTIONS,
                    null,
                    null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1"
            );

            if (cursor == null) {
                return;
            }
            if (!cursor.moveToFirst()) {
                return;
            }

            // 获取各列的索引
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int dateTakenIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);

            // 获取行数据
            String data = cursor.getString(dataIndex);
            long dateTaken = cursor.getLong(dateTakenIndex);
            // 处理获取到的第一行数据
            if (checkScreenShot(data)) {
                if (onScreenshotTakenListener != null) {
                    Log.e(TAG, data + " " + dateTaken);
                    onScreenshotTakenListener.onScreenshotTaken(data);
                }
            }

        } catch (Exception e) {
            Log.e("error", e.toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    /**
     * 判断是否是截屏
     */
    private boolean checkScreenShot(String data) {
        data = data.toLowerCase();
        // 判断图片路径是否含有指定的关键字之一, 如果有, 则认为当前截屏了
        for (String keyWork : KEYWORDS) {
            if (data.contains(keyWork)) {
                return true;
            }
        }
        return false;
    }
}
