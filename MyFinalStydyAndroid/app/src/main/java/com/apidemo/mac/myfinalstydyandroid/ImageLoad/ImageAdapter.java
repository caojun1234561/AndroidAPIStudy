/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apidemo.mac.myfinalstydyandroid.ImageLoad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.apidemo.mac.myfinalstydyandroid.R;

public class ImageAdapter extends BaseAdapter {

    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    private String url = "https://www.562aa.com/htm/piclist1/";

    private static final String[] URLS = {
            "http://imgsrc.baidu.com/imgad/pic/item/bf096b63f6246b60553a62a0e1f81a4c510fa22a.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/8b82b9014a90f6037cb445933312b31bb151edda.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1419207701,4272270283&fm=214&gp=0.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/b21c8701a18b87d6232299000d0828381f30fd48.jpg",
            "http://pic1.win4000.com/wallpaper/8/58f48e128666f.jpg",
            "https://yazhouse8.com/index.php?page=40656",
            "http://imgsrc.baidu.com/image/c0%3Dpixel_huitu%2C0%2C0%2C294%2C40/sign=c9d9632d69d0f703f2bf9d9c61823451/eaf81a4c510fd9f994e060532e2dd42a2834a410.jpg"
    };

    private final ImageDownloader imageDownloader = new ImageDownloader();

    public int getCount() {
        return URLS.length;
    }

    public String getItem(int position) {
        return URLS[position];
    }

    public long getItemId(int position) {
        return URLS[position].hashCode();
    }

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }
        imageDownloader.download(URLS[position], (ImageView) (view.findViewById(R.id.haha)));

        return view;
    }

    public ImageDownloader getImageDownloader() {
        notifyDataSetChanged();
        return imageDownloader;
    }
}
