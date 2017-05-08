package com.example.video.biz;

import android.content.Context;

import com.android.volley.Response;
import com.example.video.android_news_common.CommonUtil;
import com.example.video.android_news_common.SystemUtils;
import com.example.video.volleyhttp.VolleyHttp;

/**
 * Created by Video on 2017/2/28.
 */
public class NewsBiz {
    public static final int MODE_NEXT = 1;
    public static final int MODE_PRE = 2;

    //加载新闻类型的业务方法
    public static void loadNewsType(Context context, Response.Listener<String> listener, Response.ErrorListener elistener) {
        VolleyHttp volley = new VolleyHttp(context);
        //网络路径
        String url = CommonUtil.APPURL + "/news_sort?ver=" + CommonUtil.VERSION_CODE + "&imei=" + SystemUtils.getIMEI(context);
        System.out.println("news type url:" + url);
        volley.getJsonObject(url, listener, elistener);
    }

    public static void loadNews(Context context, Response.Listener<String> listener
            , Response.ErrorListener elistener, int subid, int n_start, int mode) {
        VolleyHttp volleyHttp = new VolleyHttp(context);
        String url = CommonUtil.APPURL + "/news_list?ver=" + CommonUtil.VERSION_CODE + "&subid="
                + subid + "&nid=" + n_start + "&dir=" + mode
                + "&stamp=" + CommonUtil.getDate() + "&cnt=" + 20;
        volleyHttp.getJsonObject(url, listener, elistener);

    }
}
