package com.apidemo.mac.myfinalstydyandroid.picasso;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.apidemo.mac.myfinalstydyandroid.R;
import com.apidemo.mac.myfinalstydyandroid.databinding.ActivityPicassoBinding;
import com.squareup.picasso.Picasso;

/**
 * Created by cqj on 2018/4/9.
 */
public class PicassoActivity extends Activity {
    private String path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523259364514&di=a93a3c0ba32073f8ea8694bd0446195b&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1309%2F05%2Fc3%2F25274577_1378321466195.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPicassoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_picasso);
        Picasso picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(true);
        picasso.load(path).resize(100, 100).placeholder(R.mipmap.ic_1).into(binding.iv);
    }


}
