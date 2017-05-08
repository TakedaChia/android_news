package com.example.video.android_news_adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Video on 2017/2/27.
 */

//ActivityLead 中需要的ViewPager的适配器
public class LeadImgAdapter extends PagerAdapter {
    private List<View> data;//要显示的View集合

    public LeadImgAdapter(List<View> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //每次要显示的View
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = data.get(position);
        container.addView(v);
        return v;
    }

    //之前显示过的View的消亡
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }
}
