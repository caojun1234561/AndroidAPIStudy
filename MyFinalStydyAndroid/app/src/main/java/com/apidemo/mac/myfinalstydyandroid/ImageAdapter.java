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

package com.apidemo.mac.myfinalstydyandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    private static final String[] URLS = {
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2017/326/48/ODY4AL2TV0GF.jpg"
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
