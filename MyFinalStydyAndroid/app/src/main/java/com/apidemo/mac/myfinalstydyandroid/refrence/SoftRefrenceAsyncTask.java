package com.apidemo.mac.myfinalstydyandroid.refrence;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.SoftReference;

/**
 * Created by cqj on 2018/2/2.
 */

public class SoftRefrenceAsyncTask extends AsyncTask {
    SoftReference<Activity> softReference;

    public SoftRefrenceAsyncTask setSoftReference(Activity activity) {
        softReference = new SoftReference<Activity>(activity);
        return this;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Thread.sleep(8000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.e(SoftRefrenceAsyncTask.class.getSimpleName(), "activit is null : " + (softReference.get() == null) + "");

    }
}
