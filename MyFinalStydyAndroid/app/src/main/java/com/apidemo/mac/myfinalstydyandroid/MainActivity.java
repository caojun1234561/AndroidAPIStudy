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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apidemo.mac.myfinalstydyandroid.CheckBox.CheckBoxActivity;
import com.apidemo.mac.myfinalstydyandroid.ImageLoad.ImageDownloader;
import com.apidemo.mac.myfinalstydyandroid.ImageLoad.ImageListActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cqj on 2018/1/26.
 */

public class MainActivity extends FragmentActivity {
    //ImageView imageView;
    RecyclerView recyclerView;
    String imageUrl = "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg";
    private static final String[] URLS = {
    };
    String[] images = new String[]{imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl, imageUrl};
    int i;
    private Map<Integer, Bitmap> bitmapMap = new HashMap<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        //imageView = findViewById(R.id.image);
        myAdapter = new MyAdapter();
        myAdapter.setUrlList(Arrays.asList(URLS));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new MyDialogFragment().show(getSupportFragmentManager(), "myDialog");
                //startActivity(new Intent(MainActivity.this, CheckBoxActivity.class));
                startActivity(new Intent(MainActivity.this, ImageListActivity.class));
            }
        });
    }

    public void loadImage(final int position, ImageView imageView, final String url) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
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
                    bitmapMap.put(msg.arg1, bmp);
                    myAdapter.notifyItemChanged(msg.arg1);
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
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
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
