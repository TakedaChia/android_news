package com.example.video.parse;

import com.example.video.entity.BaseEntity;
import com.example.video.entity.News;
import com.example.video.entity.NewsType;
import com.example.video.entity.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Video on 2017/2/28.
 */

//新闻类数据的json解析工具
public class ParseNews {
    public static List<SubType> parseNewsType(String json) {
        Gson gson = new Gson();
        BaseEntity<List<NewsType>> entity = gson.fromJson(json, new TypeToken<BaseEntity<List<NewsType>>>() {
        }.getType());
        //需要Gson解析的类中 如果带有泛型 必须使用 TypeToken  Type 的模式设置类型

        List<NewsType> news_types = entity.getData();//获得所有的大类型
//      List<SubType> sub_types = news_types.get(0).getSubgrp();//在大类型集合中取出第一个元素，再取出其中所有的小类型

        List<SubType> sub_types = new ArrayList();
        for (NewsType n : news_types) {
            for (SubType s : n.getSubgrp()) {
                sub_types.add(s);
            }
        }
        return sub_types;
    }

    public static List<News> parseNews(String json) {
        Gson gson = new Gson();
        BaseEntity<List<News>> entity = gson.fromJson(json, new TypeToken<BaseEntity<List<News>>>() {
        }.getType());
        return entity.getData();
    }
}
