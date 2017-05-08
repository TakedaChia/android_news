package com.example.video.android_news_ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.video.android_news.R;
import com.example.video.android_news_adapter.LeadImgAdapter;
import com.example.video.android_news_ui.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Video on 2017/2/27.
 */
public class ActivityLead extends MyBaseActivity {
    private ViewPager viewPager;
    private ImageView[] points = new ImageView[4];
    private LeadImgAdapter adapter;
    private SharedPreferences sp;//偏好设置  实现仅首次进入时显示lead内容
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        sp = this.getSharedPreferences("my_news", Context.MODE_PRIVATE);
        isFirst = sp.getBoolean("isFirst", true);
        if (isFirst == false) {//直接跳转
            openActivity(ActivityLogo.class);
            myFinish();
            return;
        }

        initView();
        event();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //给viewpager添加4个ImageView 显示4张图片
        List<View> views = new ArrayList();
        views.add(this.getLayoutInflater().inflate(R.layout.lead_1, null));
        views.add(this.getLayoutInflater().inflate(R.layout.lead_2, null));
        views.add(this.getLayoutInflater().inflate(R.layout.lead_3, null));
        views.add(this.getLayoutInflater().inflate(R.layout.lead_4, null));
        adapter = new LeadImgAdapter(views);
        viewPager.setAdapter(adapter);

        points[0] = (ImageView) findViewById(R.id.iv_p1);
        points[1] = (ImageView) findViewById(R.id.iv_p2);
        points[2] = (ImageView) findViewById(R.id.iv_p3);
        points[3] = (ImageView) findViewById(R.id.iv_p4);
        this.setPoint(0);//默认索引是第一个
    }

    //设置对应索引的红点为不透明，其他变半透明
    private void setPoint(int index) {
        for (int i = 0; i < points.length; i++) {
            if (i == index) {
                points[i].setAlpha(255);//不透明
            } else {
                points[i].setAlpha(100);
            }
        }

    }

    private void event() {
        //ViewPager滚动事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //划到最后一页时实现跳转
                if (position == 3) {
                    openActivity(ActivityLogo.class);
                    myFinish();
                    sp.edit().putBoolean("isFirst", false).commit(); //进入一次之后把"isFirst"设置为false
                }
                setPoint(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
