package com.apidemo.mac.myfinalstydyandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apidemo.mac.myfinalstydyandroid.MyView.ScrollViewActvity;
import com.apidemo.mac.myfinalstydyandroid.RxJava.RxJavaActivity;
import com.apidemo.mac.myfinalstydyandroid.ScreenShot.ScreenShotActivity;
import com.apidemo.mac.myfinalstydyandroid.Utils.WindowUtil;
import com.apidemo.mac.myfinalstydyandroid.picasso.PicassoActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cqj on 2018/1/26.
 */

public class MainActivity extends FragmentActivity {
    ImageView imageView;
    RecyclerView recyclerView;
    String imageUrl = "http://img0.imgtn.bdimg.com/it/u=3461919426,2481953320&fm=27&gp=0.jpg";
    private static final String[] URLS = {
    };
    String[] images = new String[]{imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl};
    int i;
    Bitmap bitmap1;
    private Map<Integer, Bitmap> bitmapMap = new HashMap<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        imageView = findViewById(R.id.image);
        myAdapter = new MyAdapter();
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScreenShotActivity.class));
            }
        });
        // myAdapter.setUrlList(Arrays.asList(images));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_1);
        if (bitmap != null) {
            Log.e("Bimap 压缩前宽高:", "宽:" + String.valueOf(bitmap.getWidth()) + ",高:" + String.valueOf(bitmap.getHeight()));
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(1, imageView, imageUrl);
                // Bitmap bitmap = getImageView(imageUrl);
//                if (bitmap1==null){
//                    bitmap1=BitmapFactory.decodeResource(getResources(), R.mipmap.ic_2);
//                }else {
//                    bitmap1.recycle();
//                }

                //new MyDialogFragment().show(getSupportFragmentManager(), "myDialog");
                //startActivity(new Intent(MainActivity.this, CheckBoxActivity.class));

                //startActivity(new Intent(MainActivity.this, RefrenceActivity.class));
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScrollViewActvity.class));
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("showTag", "" + WindowUtil.checkDeviceHasNavigationBar(MainActivity.this));
                //startActivity(new Intent(MainActivity.this, BitmapActivity.class));

            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PicassoActivity.class));
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void loadImage(final int position, ImageView imageView, final String url) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Bitmap bmp = getImageView(url);
                Message msg = new Message();
                msg.what = 0;
                msg.arg1 = position;
                msg.obj = bmp;
                System.out.println("000");
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    Bitmap bmp = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bmp);
//                    bitmapMap.put(msg.arg1, bmp);
//                    myAdapter.notifyItemChanged(msg.arg1);
                    break;
                }
            }
        }
    };

    private Bitmap getImageView(String iamgeURL) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(iamgeURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);//设置超时
            connection.setDoInput(true);
            connection.setUseCaches(false);//不设置缓存
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            Log.e("Bimap 压缩前宽高:", "宽:" + String.valueOf(bitmap.getWidth()) + ",高:" + String.valueOf(bitmap.getHeight()));
            Log.e("Bimap 压缩前宽高:", "宽:" + String.valueOf(options.outWidth) + ",高:" + String.valueOf(options.outHeight));
            inputStream.close();

        } catch (Exception e) {
            Log.e("a", "b", e);
        }
        return bitmap;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<String> urlList = new ArrayList<>();

        public void setUrlList(List<String> urlList) {
            this.urlList = urlList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            if (bitmapMap.containsKey(position) && bitmapMap.get(position) != null) {
                ImageView view = (ImageView) holder.view.findViewById(R.id.haha);
                view.setImageBitmap(bitmapMap.get(position));
            } else {
                loadImage(position, holder.imageView, urlList.get(position));
            }

        }

        @Override
        public int getItemCount() {
            return urlList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = findViewById(R.id.haha);
        }
    }

}
