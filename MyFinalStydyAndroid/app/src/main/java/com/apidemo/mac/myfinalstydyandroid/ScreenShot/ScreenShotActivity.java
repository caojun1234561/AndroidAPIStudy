package com.apidemo.mac.myfinalstydyandroid.ScreenShot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apidemo.mac.myfinalstydyandroid.R;
import com.apidemo.mac.myfinalstydyandroid.Utils.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

/**
 * Created by cqj on 2018/2/26.
 */

public class ScreenShotActivity extends FragmentActivity {
    public static final int GET_URL = 3;
    ScreenshotObserverTest observer;
    ScreenShotManager screenShotManager;
    ImageView imageView1;
    ImageView imageView2;
    LinearLayout linearLayout;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        imageView2 = findViewById(R.id.imageView2);
        imageView1 = findViewById(R.id.imageView);
        linearLayout = findViewById(R.id.linearlayout);
        button = findViewById(R.id.button3);
//        observer = new ScreenshotObserverTest(new OnScreenshotTakenListener() {
//            @Override
//            public void onScreenshotTaken(Uri uri) {
//                Log.e("拿到了", "");
//            }
//        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapUtils.convertViewToBitmap(getWindow().getDecorView());
                if (bitmap != null)
                    imageView1.setImageBitmap(BitmapUtils.removeTopAndBottomBar(ScreenShotActivity.this, bitmap));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = Bitmap.createBitmap(linearLayout.getWidth(), linearLayout.getHeight(),
                        Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(bitmap);
                linearLayout.draw(canvas);
                imageView2.setVisibility(View.GONE);
                imageView1.setImageBitmap(bitmap);
            }
        });
        screenShotManager = new ScreenShotManager(this, new OnScreenshotTakenListener() {
            @Override
            public void onScreenshotTaken(String url) {
                Log.e("拿到了", url);
                Message message = new Message();
                message.obj = url;
                message.what = GET_URL;
                handler.sendMessage(message);
            }
        });

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap peng = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_1, options);
        peng.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        Log.i("png", baos.toByteArray().length + "");

        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        BitmapFactory.Options options1 = new BitmapFactory.Options();
        options1.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap peng1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_1, options1);
        peng1.compress(Bitmap.CompressFormat.JPEG, 80, baos1);
        Log.i("WEBP", baos1.toByteArray().length + "");
        imageView2.setImageBitmap(peng1);

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap peng2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_1, options2);
        peng2.compress(Bitmap.CompressFormat.JPEG, 80, baos2);
        Log.i("JPEG", baos2.toByteArray().length + "");

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_URL) {
                Toast.makeText(ScreenShotActivity.this, "what fuck", Toast.LENGTH_SHORT).show();
                int StatusBarHeight = getStatusBarHeight(ScreenShotActivity.this);
                Bitmap bitmap = BitmapFactory.decodeFile((String) msg.obj);
                if (bitmap == null) {
                    Log.e("没有找到图片", "");
                    return;
                }
                int height = bitmap.getHeight();
                int bottomHeight = getBottomStatusHeight(ScreenShotActivity.this);
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, StatusBarHeight, bitmap.getWidth(), bitmap.getHeight() - StatusBarHeight - bottomHeight, null, true);
                imageView1.setImageBitmap(newBitmap);
                // Bitmap bitmap = Uri.fromFile(new File((String) msg.obj));
                // imageView1.setImageURI();
                imageView2.setImageBitmap(BitmapUtils.quickResponse(100, "https://www.jianshu.com/p/9a1387840cd6", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
            }
        }
    };

    @Override
    public void finish() {
        super.finish();
        screenShotManager.onDestory();
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        screenShotManager.startWatching();
    }

    @Override
    protected void onPause() {
        super.onPause();
        screenShotManager.stopWatching();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
