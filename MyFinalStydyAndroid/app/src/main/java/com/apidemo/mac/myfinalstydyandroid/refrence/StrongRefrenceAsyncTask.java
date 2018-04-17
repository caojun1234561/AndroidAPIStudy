package com.apidemo.mac.myfinalstydyandroid.refrence;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by cqj on 2018/2/2.
 */

public class StrongRefrenceAsyncTask extends AsyncTask {
    Activity activity;

    public StrongRefrenceAsyncTask setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.e(StrongRefrenceAsyncTask.class.getSimpleName(), "activit is null : " + (activity == null) + "");

    }
}
