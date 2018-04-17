package com.apidemo.mac.myfinalstydyandroid.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.apidemo.mac.myfinalstydyandroid.R;
import com.apidemo.mac.myfinalstydyandroid.ScreenShot.ScreenShotActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * ARGB 是在RGB色彩模式上附加了透明度通道
 * RGBA是代表Red（红色） Green（绿色） Blue（蓝色）和 Alpha的色彩空间，也就是透明度/不透明度。
 * 虽然它有的时候被描述为一个颜色空间，但是它其实仅仅是RGB模型的附加了额外的信息。
 * <p>
 * 图片长度 x 图片宽度 x 一个像素点占用的字节数
 * 压缩原理：降低分辨率，然后降低每个分辨率的占用内存
 * <p>
 * <p>
 * <p>
 * Bitmap 代表了最大的堆内存区块的连续使用，所以大量位图的使用会导致OOM，同时也会导致更多的GC时间消耗
 * 加载大量位图的GC会大量消耗性能，我们可以使用位图对象池来解决
 * Object Pool是高抖动数据的类型的使用技巧。
 * 当我们处理完某个对象，我们把对象加入到Object Pool 中而不是释放它下次需要同类型对象时可以将池内现有对象重新目的化
 * Android BitMap API 会加载你的图像数据并且会在堆上创建新的位图来存储这些数据。
 * 我们可以指示解码器使用已存内存片来加载位图而不是创建一个新的BitMapOptions 中的 inBitmap可以很好的解决。
 * 对于接下来的像素数据，你的任何解码和加载指令都会重复使用这个位图。
 * 对于列表显示图片页面，我们只需要创建当前屏幕可以显示的下的位图个数的对象，而不是为每个图片创建一个对象。
 * 当滑动屏幕时，复用这些已经创建好的位图对象
 * <p>
 * 位图复用注意：
 * 1.在API19以下，只能复用大小一样的位图，而在19及以上接下来的位图可以比当前位图大
 * 2.虽然可以跨像素格式重复使用，但是建议为每一个像素格式使用单独的位图例如，如果使用565 或 444 来减少内存开销，那就不应该保存到8888的BitMap中
 * <p>
 * 创建一个应用期间可以重复使用的，带有常见位图大小及格式的对象池，对我们帮助很大，可以进一步减少内存抖动和存储残片
 * Created by cqj on 2018/1/27.
 */

public class BitmapUtils {
    private static int IMAGE_HALFWIDTH = 50;//宽度值，影响中间图片大小
    private static final String Tag = BitmapUtils.class.getSimpleName();


    static boolean canUseForInBitmap(
            Bitmap candidate, BitmapFactory.Options targetOptions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // From Android 4.4 (KitKat) onward we can re-use if the byte size of
            // the new bitmap is smaller than the reusable bitmap candidate
            // allocation byte count.
            int width = targetOptions.outWidth / targetOptions.inSampleSize;
            int height = targetOptions.outHeight / targetOptions.inSampleSize;
            int byteCount = width * height * getBytesPerPixel(candidate.getConfig());
            return byteCount <= candidate.getAllocationByteCount();
        }

        // On earlier versions, the dimensions must match exactly and the inSampleSize must be 1
        return candidate.getWidth() == targetOptions.outWidth
                && candidate.getHeight() == targetOptions.outHeight
                && targetOptions.inSampleSize == 1;
    }

    /**
     * A helper function to return the byte usage per pixel of a bitmap based on its configuration.
     */
    static int getBytesPerPixel(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        } else if (config == Bitmap.Config.RGB_565) {
            return 2;
        } else if (config == Bitmap.Config.ARGB_4444) {
            return 2;
        } else if (config == Bitmap.Config.ALPHA_8) {
            return 1;
        }
        return 1;
    }

    /**
     * 加载图片 缩放大小
     *
     * @param imageView
     */
    public static void loadImageScr(final Context context, final ImageView imageView) {
        imageView.post(new Runnable() {
            @Override
            public void run() {
                Log.i(Tag, "imageViewWidth" + imageView.getWidth());
                Log.i(Tag, "imageViewHeight" + imageView.getHeight());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_6, options);
                int inSampleSize = 1;
                int width = options.outWidth;
                int height = options.outHeight;
                final int heightRatio = Math.round((float) height / (float) imageView.getHeight());
                //宽度比例值
                final int widthRatio = Math.round((float) width / (float) imageView.getWidth());
                inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
                options.inSampleSize = inSampleSize;
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_6, options);
                Log.i(Tag, "bitmapSize:" + bitmap.getByteCount() + "byte = " + bitmap.getByteCount() / 1024f / 1024f + "M");
                Log.i(Tag, "bitmapWith:" + bitmap.getWidth());
                Log.i(Tag, "bitmapheight:" + bitmap.getHeight());
                Log.i(Tag, "optionsWith：" + options.outWidth);
                Log.i(Tag, "optionsHeight：" + options.outHeight);
                imageView.setImageBitmap(bitmap);
            }
        });

    }

    public static BitmapFactory.Options getBitmapInfo(Context context, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), id, options);
        return options;
    }


    public static Bitmap zipBitmap(InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;//宽高都压缩为原来的二分之一, 此参数需要根据图片要展示的大小来确定
        options.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片格式
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public static Bitmap zipBitmapByConfig(Resources resources, int id) {
        Bitmap bitmap1 = BitmapFactory.decodeResource(resources, id);
        Log.e("zipBitmapByConfig 压缩前:", String.valueOf(bitmap1.getByteCount()));
        Log.e("Bimap 压缩前Config:", bitmap1.getConfig().name());
        Log.e("Bimap 压缩前宽高:", "宽:" + String.valueOf(bitmap1.getWidth()) + ",高:" + String.valueOf(bitmap1.getHeight()));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, id, options);
        Log.e("zipBitmapByConfig 压缩后:", String.valueOf(bitmap.getByteCount()));
        Log.e("Bimap 压缩后Config:", bitmap.getConfig().name());
        Log.e("Bimap 压缩后宽高:", "宽:" + String.valueOf(bitmap.getWidth()) + ",高:" + String.valueOf(bitmap.getHeight()));

        return bitmap;
    }

    /**
     * @param with
     * @param height
     * @return
     */
    public static Bitmap zipBitmapByScale(Resources resources, int id, int with, int height) {
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        //options2.inJustDecodeBounds = true;
        Bitmap bitmap1 = BitmapFactory.decodeResource(resources, id, options2);
        //Bitmap bitmap1 = BitmapFactory.decodeResource(resources, id, options2);
        Log.e("zipBitmapByConfig 压缩前:", String.valueOf(bitmap1.getAllocationByteCount()));
        Log.e("Bimap 压缩前Config:", bitmap1.getConfig().name());
        Log.e("Bimap 压缩前宽高:", "宽:" + String.valueOf(bitmap1.getWidth()) + ",高:" + String.valueOf(bitmap1.getHeight()));
        Log.e("Bimap 压缩前宽高:", "宽:" + String.valueOf(options2.outWidth) + ",高:" + String.valueOf(options2.outHeight));
        float originalWith = bitmap1.getWidth();
        float originalHeight = bitmap1.getHeight();
        bitmap1.recycle();
        Log.e("zipBitmapByConfig 压缩前:", String.valueOf(bitmap1.getAllocationByteCount()));
        float originalScale = originalWith / originalHeight;
        float newScale = with / height;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, id, options);
        Log.e("zipBitmapByConfig 压缩后:", String.valueOf(bitmap.getAllocationByteCount()));
        Log.e("Bimap 压缩后Config:", bitmap.getConfig().name());
        Log.e("Bimap 压缩后宽高:", "宽:" + String.valueOf(bitmap.getWidth()) + ",高:" + String.valueOf(bitmap.getHeight()));

        return bitmap;
    }

    /**
     * View 转换为BitMap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    /**
     * 去掉statusBar和Bottombar高度的图片
     *
     * @param context
     * @param bitmap
     * @return
     */
    public static Bitmap removeTopAndBottomBar(Context context, Bitmap bitmap) {
        int statusBarHeight = WindowUtil.getAppBarHeight(context);
        int bottomBarHeight = WindowUtil.getBottomStatusHeight(context);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, statusBarHeight, bitmap.getWidth(), bitmap.getHeight() - statusBarHeight - bottomBarHeight, null, true);
        return newBitmap;
    }

    /**
     * @param url
     * @return
     */
    public static Bitmap quickResponse(String url) {
        return quickResponse(IMAGE_HALFWIDTH, url, null);
    }

    /**
     * 生成二维码，指定大小
     *
     * @param size
     * @param url
     * @return
     */
    public static Bitmap quickResponse(int size, String url) {
        return quickResponse(size, url, null);
    }

    /**
     * 生成二维码，指定大小
     *
     * @param url
     * @return
     */
    public static Bitmap quickResponse(String url, Bitmap logoBitmap) {
        return quickResponse(IMAGE_HALFWIDTH, url, logoBitmap);
    }

    /**
     * 生成二维码带logo
     *
     * @param size
     * @param url
     * @param logoBitmap
     * @return
     */
    public static Bitmap quickResponse(int size, String url, Bitmap logoBitmap) {
        Bitmap bitmap = null;
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, size, size, hints);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * size + x] = 0xff000000;
                    } else {
                        pixels[y * size + x] = 0xffffffff;
                    }
                }
            }
            bitmap = Bitmap.createBitmap(size, size,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            if (logoBitmap != null)
                bitmap = addLogo(bitmap, logoBitmap);
            Log.i("", "生成二维码成功");
            return bitmap;
        } catch (Exception e) {
            Log.e("QRCodeUtilsError", "生成二维码失败");
        }
        return bitmap;
    }


    /**
     * 在二维码中间添加Logo图案
     * 大小是5分之一
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }
}
