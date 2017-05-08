package com.example.video.android_news_ui;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.video.android_news.R;
import com.example.video.android_news_ui.base.MyBaseActivity;

/**
 * Created by Video on 2017/2/27.
 */
public class ActivityLogo extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        ImageView logo = (ImageView) findViewById(R.id.iv_logo);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.logo);
        anim.setFillAfter(true);//最后保持在最后状态
        //设置动画事件
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //结束时跳转到main界面
                openActivity(ActivityMain.class);
                myFinish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logo.setAnimation(anim);
    }
}
