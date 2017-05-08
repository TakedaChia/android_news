package com.example.video.android_news_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.video.android_news.R;
import com.example.video.android_news_ui.base.MyBaseActivity;
import com.example.video.views.slidingmenu.SlidingMenu;

/**
 * Created by Video on 2017/2/27.
 */
public class ActivityMain extends MyBaseActivity {

    private TextView tv_title;
    private ImageView iv_set;
    private ImageView iv_user;

    private FragmentMain fg_main;
    private FragmentFavorite fg_favorite;
    private FragmentMenu fg_menu;
    private FragmentMenuRight fg_menu_right;
    private FragmentType fg_type;

    public static SlidingMenu slidingMenu;// 侧滑菜单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.initView();
        this.initSlidingMenu();
        this.showFragmentMain();//开始默认显示fg_main

        this.event();//顶部左右两个图片的点击事件
    }

    private void event() {
        this.iv_set.setOnClickListener(listener);
        this.iv_user.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView_set:
                    if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                        slidingMenu.showContent();//回到显示主内容
                    } else {
                        slidingMenu.showMenu();
                    }
                    break;
                case R.id.imageView_user:
                    if (slidingMenu != null && slidingMenu.isSecondaryMenuShowing()) {
                        slidingMenu.showContent();//回到显示主内容
                    } else {
                        slidingMenu.showSecondaryMenu();
                        break;
                    }
            }
        }
    };

    private void initView() {
        this.tv_title = (TextView) this.findViewById(R.id.textView1);
        this.iv_set = (ImageView) this.findViewById(R.id.imageView_set);
        this.iv_user = (ImageView) this.findViewById(R.id.imageView_user);

        this.fg_main = new FragmentMain();
        this.fg_menu = new FragmentMenu();
        this.fg_menu_right = new FragmentMenuRight();
        this.fg_type = new FragmentType();
        this.fg_favorite = new FragmentFavorite();
        this.slidingMenu = new SlidingMenu(this);
    }

    //初始化fragment菜单，
    private void initSlidingMenu() {


        // 设置slingmenu
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        // 设置slidingmeun的内容
        slidingMenu.setMenu(R.layout.layout_menu);// 设置空容器
        slidingMenu.setSecondaryMenu(R.layout.layout_menu_right);

        //在空容器内设置fragment内容
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_menu, fg_menu).commit();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_menu_right, fg_menu_right).commit();

    }

    //显示fg_main
    public void showFragmentMain() {
        this.tv_title.setText("资讯");
        slidingMenu.showContent();//让slidingmenu隐藏所有菜单 而显示中间的主要内容
        this.getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fg_main).commit();
    }

    //显示fg_type
    public void showFragmentType() {
        this.tv_title.setText("新闻分类");
        slidingMenu.showContent();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fg_type).commit();
    }

    //显示收藏Fragment
    public void showFragmentFavorite() {
        tv_title.setText("收藏");
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fg_favorite).commit();
    }
}
