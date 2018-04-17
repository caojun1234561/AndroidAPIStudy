package com.apidemo.mac.myfinalstydyandroid.refrence;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.apidemo.mac.myfinalstydyandroid.R;
import com.apidemo.mac.myfinalstydyandroid.Utils.BitmapUtils;
import com.apidemo.mac.myfinalstydyandroid.Utils.DisplayUtils;

import java.lang.ref.WeakReference;

/**
 * Created by cqj on 2018/2/2.
 */

public class RefrenceActivity extends Activity {
    LruCache<Integer, Bitmap> lruCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new StrongRefrenceAsyncTask().setActivity(this).execute();
        //new SoftRefrenceAsyncTask().setSoftReference(this).execute();
        //new WeakRefrenceAsyncTask().setSoftReference(this).execute();
        //WeakReference weakRefer = new WeakReference(new String("sss"));

        //Log.e(RefrenceActivity.class.getSimpleName(), "String is null : " + (weakRefer.get() == null) + "");
        //finish();
        lruCache = new LruCache<Integer, Bitmap>(10 * 1024 * 1024) {
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                //Log.e("Bitmap btye Count ：", "" + value.getByteCount());
                return value.getByteCount();
            }
        };
//        for (int i = 0; i < 3; i++) {
//            demo();
//        }
//        lruCache.get(4);
        zipBitmap();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void zipBitmap() {
        ((ImageView) findViewById(R.id.image)).setImageBitmap(BitmapUtils.zipBitmapByScale(getResources(), R.mipmap.bg_buy_card, DisplayUtils.dip2px(this, 270), DisplayUtils.dip2px(this, 270)));
        //((ImageView) findViewById(R.id.image)).setImageBitmap(BitmapUtils.zipBitmapByConfig(getResources(), R.mipmap.ic_2));

    }

    private void demo() {
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_1);
        lruCache.put(1, bitmap1);
        Log.e("lruCacheSize:", String.valueOf(lruCache.maxSize()) + ":when input:" + 1);
        lruCache.put(2, bitmap2);
        Log.e("lruCacheSize:", String.valueOf(lruCache.size()) + ":when input:" + 2);
        lruCache.put(3, bitmap3);
        Log.e("lruCacheSize:", String.valueOf(lruCache.size()) + ":when input:" + 3);
        lruCache.put(4, bitmap4);
        Log.e("lruCacheSize:", String.valueOf(lruCache.size()) + ":when input:" + 4);
    }

   /* *//**//*private class StrongRefrenceAsyncTask extends AsyncTask {
        Activity activity;

        public StrongRefrenceAsyncTask setActivity(Activity activity) {
            this.activity = activity;
            return StrongRefrenceAsyncTask.this;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.e(StrongRefrenceAsyncTask.class.getSimpleName(), (activity != null) + "");
            super.onPostExecute(o);
        }
    }*/
}
