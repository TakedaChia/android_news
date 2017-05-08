package com.example.video.android_news_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.video.android_news.R;
import com.example.video.android_news_ui.base.MyBaseAdapter;
import com.example.video.entity.News;

/**
 * Created by Video on 2017/3/2.
 */
public class NewsAdapter extends MyBaseAdapter<News> {

    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        View v = this.inflater.inflate(R.layout.item_list_news, null);
        ImageView iv_icon = (ImageView) v.findViewById(R.id.imageView1);
        TextView tv_title = (TextView) v.findViewById(R.id.textView1);
        TextView tv_text = (TextView) v.findViewById(R.id.textView2);
        TextView tv_time = (TextView) v.findViewById(R.id.textView3);

        News news = this.myList.get(position);
        tv_title.setText(news.getTitle());
        tv_text.setText(news.getSummary());
        tv_time.setText(news.getStamp());

        //图片加载

        return v;
    }
}
