package com.apidemo.mac.myfinalstydyandroid.Bitmap;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.apidemo.mac.myfinalstydyandroid.R;

/**
 * Created by cqj on 2018/3/8.
 */

public class BitmapActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, new BitmapTest1Fragment(), "bitmaptest1");
        transaction.commit();
    }

}
