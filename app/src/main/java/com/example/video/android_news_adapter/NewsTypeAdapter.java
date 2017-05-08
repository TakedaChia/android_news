package com.example.video.android_news_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.video.android_news.R;
import com.example.video.android_news_ui.base.MyBaseAdapter;
import com.example.video.entity.SubType;

/**
 * Created by Video on 2017/2/28.
 */
public class NewsTypeAdapter extends MyBaseAdapter<SubType> {

    public NewsTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        View v = this.inflater.inflate(R.layout.item_list_newstype, null);
        SubType t = this.myList.get(position);

        TextView tv_type = (TextView) v.findViewById(R.id.tv_newstype_type);
        tv_type.setText(t.getSubgroup());

        if (position == selectedIndex) {
            tv_type.setTextColor(Color.RED);
        } else {
            tv_type.setTextColor(Color.BLACK);
        }
        return v;
    }

    //当点击某个条目时，这个条目中内容文本颜色显示为红色
    private int selectedIndex = 0;//被选中的条目的索引

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
    }
}