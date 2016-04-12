package com.kevin.news.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kevin.news.R;
import com.kevin.news.activity.MainActivity;

/**
 * Created by Kevin on 2016/4/8.
 */
public class BasePager {

    public View myRootView;
    public Activity mActivity;
    public TextView tvTitle;
    public FrameLayout flContent;
    public ImageButton btnMenu;


    public BasePager(Activity myActivity) {
        mActivity = myActivity;
        initViews();
    }


    public void initViews() {

        myRootView = View.inflate(mActivity, R.layout.base_paper, null);

        tvTitle = (TextView) myRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) myRootView.findViewById(R.id.fl_content);
        btnMenu = (ImageButton) myRootView.findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
    }

    private void toggleSlidingMenu() {
        MainActivity mainUI = (MainActivity)mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();
    }

    public void initData() {

    }

    /**
     * 设置侧边栏是否可滑动
     *
     * @param enable
     */
    public void setSlidingMenuEnable(boolean enable) {

        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();

        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}

