package com.example.video.android_news_ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.video.android_news.R;
import com.example.video.android_news_adapter.NewsAdapter;
import com.example.video.db.NewsDBManager;
import com.example.video.entity.News;

import java.util.ArrayList;

/**
 * Created by Video on 2017/2/27.
 */
public class FragmentFavorite extends Fragment {
    private ActivityMain activity_main;
    private ListView lv_news;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, null);
        this.activity_main = (ActivityMain) this.getActivity();
        this.lv_news = (ListView) v.findViewById(R.id.listview);
        this.adapter = new NewsAdapter(this.activity_main);
        this.lv_news.setAdapter(adapter);

        //数据加载
        NewsDBManager db = new NewsDBManager(this.activity_main);
        ArrayList<News> news = db.queryLoveNews();
        adapter.appendData(news, true);
        adapter.update();

        //收藏的新闻列表点击事件
        this.lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", news);
                activity_main.openActivity(ActivityShow.class, bundle);//跳转到新闻显示界面
            }
        });

        return v;
    }
}
