package com.example.video.volleyhttp;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.video.android_news.R;

/**
 * Created by Video on 2017/2/28.
 */
public class VolleyHttp {
    public static RequestQueue request_Queue;
    private Context context;

    public VolleyHttp(Context context) {
        if (request_Queue == null) {
            request_Queue = Volley.newRequestQueue(context);
        }
        this.context = context;
    }

    //http访问:获得json结果
    public void getJsonObject(String url, Response.Listener<String> listener, Response.ErrorListener elistener) {
        StringRequest request = new StringRequest(url, listener, elistener);
        request_Queue.add(request);
    }

    //图片加载
    public void loadImage(String url, ImageLoader.ImageCache cache, ImageView iv) {
        ImageLoader image_Loader = new ImageLoader(request_Queue, cache);
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.defaultpic, R.drawable.defaultpic);
        image_Loader.get(url, listener);

    }
}
