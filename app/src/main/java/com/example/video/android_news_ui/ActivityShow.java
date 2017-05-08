package com.example.video.android_news_ui;

import android.app.ActionBar;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.video.android_news.R;
import com.example.video.android_news_ui.base.MyBaseActivity;
import com.example.video.db.NewsDBManager;
import com.example.video.entity.News;

/**
 * Created by Video on 2017/3/2.
 */

//新闻显示界面
public class ActivityShow extends MyBaseActivity {
    private WebView web_view;//网页显示工具
    private ProgressBar progressBar;//加载进度显示
    private News news;//传来的新闻对象

    private TextView tv_comment;//评论按钮
    private ImageView iv_back;//返回新闻主界面
    private ImageView iv_menu;//返回

    private PopupWindow popupWindow;//显示收藏按钮的小窗口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        web_view = (WebView) findViewById(R.id.webView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        news = (News) this.getIntent().getSerializableExtra("news");

        tv_comment = (TextView) findViewById(R.id.textView2);
        iv_back = (ImageView) findViewById(R.id.imageView_back);
        iv_menu = (ImageView) findViewById(R.id.imageView_menu);

        tv_comment.setOnClickListener(listener);
        iv_back.setOnClickListener(listener);
        iv_menu.setOnClickListener(listener);

        initPopup();//初始化popupwindow

        //将内容显示在webview 并关联进度条
        this.web_view.loadUrl(news.getLink());
        this.web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (progressBar.getProgress() >= 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    //界面事件监听
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView_back:
                    ActivityShow.this.finish();
                    break;
                case R.id.textView2:
                    //跳转到评论界面
                    break;
                case R.id.imageView_menu:
                    //显示收藏popupWindow
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(iv_menu, 0, 12);//在iv_menu下方弹出
                    }
                    break;
                case R.id.saveLocal:
                    //执行新闻收藏
                    NewsDBManager db = new NewsDBManager(ActivityShow.this);
                    if (db.saveLoveNews(news)) {
                        ActivityShow.this.showToast("收藏成功,在收藏界面查看");
                    } else {
                        ActivityShow.this.showToast("已经收藏了这条新闻");
                    }
                    break;
            }
        }
    };

    private void initPopup() {
        View v = this.getLayoutInflater().inflate(R.layout.item_pop_save, null);
        popupWindow = new PopupWindow(v, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        TextView tv_save = (TextView) v.findViewById(R.id.saveLocal);
        tv_save.setOnClickListener(listener);//点击收藏新闻
    }
}
