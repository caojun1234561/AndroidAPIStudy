package com.apidemo.mac.myfinalstydyandroid.refrence;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by cqj on 2018/2/2.
 */

public class WeakRefrenceAsyncTask extends AsyncTask {
    WeakReference<Activity> softReference;

    public WeakRefrenceAsyncTask setSoftReference(Activity activity) {
        softReference = new WeakReference<>(activity);
        return this;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.e(WeakRefrenceAsyncTask.class.getSimpleName(), "activit isDestroyed : "+(softReference.get().isDestroyed()) + "");
        Log.e(WeakRefrenceAsyncTask.class.getSimpleName(), "activit isFinishing : "+(softReference.get().isFinishing()) + "");
    }
}
