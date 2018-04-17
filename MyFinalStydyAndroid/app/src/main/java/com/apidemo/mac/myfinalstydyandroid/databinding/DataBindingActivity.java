package com.apidemo.mac.myfinalstydyandroid.databinding;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.apidemo.mac.myfinalstydyandroid.R;

/**
 * Created by cqj on 2018/3/27.
 */

public class DataBindingActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_data_binding_test);
    }
}
