package com.apidemo.mac.myfinalstydyandroid.Bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.apidemo.mac.myfinalstydyandroid.R;
import com.apidemo.mac.myfinalstydyandroid.Utils.BitmapUtils;
import com.apidemo.mac.myfinalstydyandroid.Utils.WindowUtil;

import java.lang.ref.SoftReference;
import java.util.Set;

/**
 * Created by cqj on 2018/3/8.
 */

public class BitmapTest1Fragment extends Fragment {

    private static final String Tag = "BitmapTest1Fragment";
    ImageView image0;
    BitmapFactory.Options options;
    SoftReference<Bitmap> cach;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Tag, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(Tag, "onCreateView");
        options = new BitmapFactory.Options();
        options.inMutable = true;
        View view = inflater.inflate(R.layout.fragment_bitmap_test1, container, false);
        image0 = view.findViewById(R.id.image4);
        view.findViewById(R.id.image0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BitmapUtils.loadImageScr(BitmapTest1Fragment.this.getActivity(),image0);
                loadImage(image0);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        WindowUtil.getScreenWith(getActivity());
//        image0.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                loadImage(image0);
//                image0.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });


    }


    /**
     * ALPHA_8：  每个像素占用1byte内存
     * ARGB_4444:每个像素占用2byte内存
     * ARGB_8888:每个像素占用4byte内存
     * RGB_565:     每个像素占用2byte内存
     * <p>
     * 内存占用大小= bitmap.getWidth() * bitmap.getHeight() * 4
     */
    private void loadImage(ImageView imageView) {
        Log.i(Tag, "imageViewWidth" + imageView.getWidth());
        Log.i(Tag, "imageViewHeight" + imageView.getHeight());
        Bitmap bitmap;
        if (cach != null && cach.get() != null) {
            bitmap = cach.get();
            options.inBitmap = bitmap;
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_4, options);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_6, options);
            cach = new SoftReference<Bitmap>(bitmap);
        }

        Log.i(Tag, "bitmapSize:" + bitmap.getByteCount() + "byte = " + bitmap.getByteCount() / 1024f / 1024f + "M");
        Log.i(Tag, "bitmapWith:" + bitmap.getWidth());
        Log.i(Tag, "bitmapheight:" + bitmap.getHeight());
        Log.i(Tag, "optionsWith：" + options.outWidth);
        Log.i(Tag, "optionsHeight：" + options.outHeight);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onDestroy() {
        if (cach != null && cach.get() != null) {
            cach.get().recycle();
            cach.clear();
        }
        super.onDestroy();
    }
}
