package com.example.video.android_news_ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.video.android_news.R;
import com.example.video.android_news_adapter.NewsAdapter;
import com.example.video.android_news_adapter.NewsTypeAdapter;
import com.example.video.android_news_common.CommonUtil;
import com.example.video.android_news_common.SystemUtils;
import com.example.video.biz.NewsBiz;
import com.example.video.db.NewsDBManager;
import com.example.video.entity.News;
import com.example.video.entity.SubType;
import com.example.video.parse.ParseNews;
import com.example.video.views.hlistview.HorizontalListView;
import com.example.video.views.xlistview.XListView;

import java.util.List;

/**
 * Created by Video on 2017/2/27.
 */
public class FragmentMain extends Fragment {
    private XListView list_view_news;//新闻列表
    private HorizontalListView list_view_type;//类型列表
    private ActivityMain activity_main;//所在Activity
    private ImageView iv_moretype;//跳转到更多类型的fragment
    private NewsDBManager db_manager;//数据库操作工具
    private List<SubType> types;//新闻类型集合
    private NewsTypeAdapter typeAdapter;

    private int n_id;//新闻ID
    private int subid = 1;//当前要显示的的新闻类型的id
    private int load_mode;//加载模式(上一页/下一页)
    private NewsAdapter news_adapter;//新闻适配器
    private List<News> news_data;//新闻集合

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_newslist, null);

        //初始化
        this.list_view_news = (XListView) v.findViewById(R.id.listview);
        this.list_view_type = (HorizontalListView) v.findViewById(R.id.hl_type);
        this.iv_moretype = (ImageView) v.findViewById(R.id.iv_moretype);
        this.activity_main = (ActivityMain) this.getActivity();
        this.db_manager = new NewsDBManager(this.activity_main);
        this.typeAdapter = new NewsTypeAdapter(this.activity_main);
        this.list_view_type.setAdapter(typeAdapter);

        this.news_adapter = new NewsAdapter(this.activity_main);
        this.list_view_news.setAdapter(news_adapter);

        //事件
        this.list_view_news.setPullRefreshEnable(true);
        this.list_view_news.setPullLoadEnable(true);
        this.list_view_news.setXListViewListener(x_listener);//新闻列表滑动事件
        this.list_view_news.setOnItemClickListener(null);

        list_view_type.setOnItemClickListener(type_list_listener);//新闻类型的点击事件
        iv_moretype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将中间容器内容切换为FragmentType
                activity_main.showFragmentType();
            }
        });

        //新闻条目的点击事件
        this.list_view_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", news);
                activity_main.openActivity(ActivityShow.class, bundle);//跳转到新闻显示界面
            }
        });


        //加载所有新闻类型
        loadNewsType();
        //加载第一类型下所有的新闻
        loadNextNews(true);//第一次执行 就直接读第一页
        //显示数据加载的进度条对话框
        this.activity_main.showLoadingDialog(this.activity_main, "数据加载中……", false);
        return v;
    }

    //加载新的新闻
    private void loadNextNews(boolean isFirst) {
        //翻页逻辑
        this.n_id = 1;

        if (isFirst == false) {
            this.load_mode = NewsBiz.MODE_NEXT;
            if (news_adapter.getAdapterData().size() > 0) {
                n_id = news_adapter.getItem(0).getNid();
            }
        }
        load_mode = NewsBiz.MODE_NEXT;//加载下一页

        if (SystemUtils.getInstance(this.activity_main).isNetConn()) {
            NewsBiz.loadNews(this.activity_main, newlistener, new_e_listener, subid, n_id, load_mode);
        } else {
            //读取数据库
        }
    }

    //加载翻页，阅读过去的新闻
    private void loadPreNews() {
        load_mode = NewsBiz.MODE_PRE;
        if (news_adapter.getCount() - 2 <= 0) {//确保当前listview有显示数据，xlistview头尾有内容，各自需要占据一个listview的item
            return;
        }

        n_id = news_adapter.getItem(list_view_news.getLastVisiblePosition() - 2).getNid();
        if (SystemUtils.getInstance(this.activity_main).isNetConn()) {
            NewsBiz.loadNews(this.activity_main, newlistener, new_e_listener, subid, n_id, load_mode);
        } else {
            //读取数据库
        }
    }

    //新闻数据加载成功
    private Response.Listener<String> newlistener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            System.out.println("news_json" + response);
            news_data = ParseNews.parseNews(response);

            boolean isClear = load_mode == NewsBiz.MODE_NEXT ? true : false;//依据翻页模式来判断要不要清除集合中原有的数据
            news_adapter.appendData(news_data, isClear);
            news_adapter.update();
            activity_main.cancelDialog();
        }
    };
    private Response.ErrorListener new_e_listener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            activity_main.showToast("数据加载失败！");
            activity_main.cancelDialog();
        }
    };

    //界面事件处理

    //新闻列表上下滑动事件
    private XListView.IXListViewListener x_listener = new XListView.IXListViewListener() {
        @Override
        public void onRefresh() {//刷新数据
            //加载新的新闻数据
            loadNextNews(false);
            //listview刷新
            list_view_news.stopLoadMore();
            list_view_news.stopRefresh();
            list_view_news.setRefreshTime(CommonUtil.getSystime());
        }

        @Override
        public void onLoadMore() {//翻页
            //加载下一页数据
            loadPreNews();
            list_view_news.stopLoadMore();
            list_view_news.stopRefresh();

        }
    };

    //加载所有新闻类型
    private void loadNewsType() {
        //先判断数据库内有无数据
        if (db_manager.queryNewsType().size() == 0) {
            System.out.println("load type from http");
            if (SystemUtils.getInstance(this.activity_main).isNetConn()) {
                //网络访问 获取服务器中的新闻类型数据
                NewsBiz.loadNewsType(this.activity_main, type_listener, type_elistener);
            }

        } else {
            //访问数据库
            System.out.println("load type from db");
            types = db_manager.queryNewsType();
            typeAdapter.appendData(types, true);
            typeAdapter.update();
        }
    }

    //新闻类型信息网络访问的回调监听
    private Response.Listener<String> type_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            System.out.println("new type json:" + response);
            types = ParseNews.parseNewsType(response);
            //网络访问成功 则需要将数据填入数据库
            db_manager.saveNewsType(types);
            //将数据显示在listview
            typeAdapter.appendData(types, true);
            typeAdapter.update();
        }
    };

    private Response.ErrorListener type_elistener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            System.out.println("new type error:");
            volleyError.printStackTrace();
        }
    };

    //新闻类型列表选线点击事件
    private AdapterView.OnItemClickListener type_list_listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SubType type = (SubType) parent.getItemAtPosition(position);
            subid = type.getSubid();//获得类型ID

            //让被选中的元素 显示为红色字体
            typeAdapter.setSelectedIndex(position);
            typeAdapter.update();

            //重新加载新闻列表数据
            loadNextNews(true);

            //再次显示加在数据的进度条
            activity_main.showLoadingDialog(activity_main, "加载数据中……", false);
        }
    };


}
