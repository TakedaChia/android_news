package com.example.video.android_news_common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

/**
 * Created by Video on 2017/3/5.
 */
public class LoadImage {
    private Context context;

    public LoadImage(Context context) {
        this.context = context;
    }

    public void load_image(String url, ImageView iv) {
        if (url == null || url.length() == 0) {
            return;
        }

        Bitmap bitmap = null;
        //先在内存中搜索图片
        bitmap = this.getBitmapFromCache(url);
        if (bitmap != null) {
            System.out.println("内存中找到图片");
            iv.setImageBitmap(bitmap);
            return;
        }

        //内存中没有 则到应用根目录下 cache文件夹内搜索
        bitmap = this.getBitmapFromCacheDir(url);
        if(){

        }
    }
}
