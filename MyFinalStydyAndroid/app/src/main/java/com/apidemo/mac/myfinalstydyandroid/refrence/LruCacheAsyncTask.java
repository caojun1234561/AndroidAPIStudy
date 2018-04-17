package com.apidemo.mac.myfinalstydyandroid.refrence;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

/**
 * Created by cqj on 2018/2/2.
 */

public class LruCacheAsyncTask extends AsyncTask {
    Activity activity;

    LruCache lruCache = new LruCache(1);

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}
