package com.apidemo.mac.myfinalstydyandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by cqj on 2018/1/27.
 */

public class BitmapUtils {

    public static Bitmap zipBitmap(InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;//宽高都压缩为原来的二分之一, 此参数需要根据图片要展示的大小来确定
        options.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片格式
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

}
