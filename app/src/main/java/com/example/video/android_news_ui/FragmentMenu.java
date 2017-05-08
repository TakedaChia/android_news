package com.example.video.android_news_ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.video.android_news.R;

/**
 * Created by Video on 2017/2/27.
 */
public class FragmentMenu extends Fragment {
    private ActivityMain activity_main;
    private RelativeLayout rls[] = new RelativeLayout[5];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_left, null);
        activity_main = (ActivityMain) this.getActivity();

        rls[0] = (RelativeLayout) v.findViewById(R.id.rl_news);
        rls[1] = (RelativeLayout) v.findViewById(R.id.rl_reading);
        rls[2] = (RelativeLayout) v.findViewById(R.id.rl_local);
        rls[3] = (RelativeLayout) v.findViewById(R.id.rl_commnet);
        rls[4] = (RelativeLayout) v.findViewById(R.id.rl_photo);

        for (RelativeLayout r : rls) {//每个条目添加点击事件
            r.setOnClickListener(listener);
        }

        return v;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (RelativeLayout r : rls) {
                r.setBackgroundColor(0);
            }
            switch (v.getId()) {
                case R.id.rl_news:
                    rls[0].setBackgroundColor(0x33c85555);
                    activity_main.showFragmentMain();
                    break;
                case R.id.rl_reading:
                    rls[1].setBackgroundColor(0x33c85555);
                    activity_main.showFragmentFavorite();
                    break;
            }
        }
    };
}
