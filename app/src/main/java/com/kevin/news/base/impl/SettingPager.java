package com.kevin.news.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.kevin.news.base.BasePager;

/**
 * Created by Kevin on 2016/4/9.
 */
public class SettingPager extends BasePager {

    public SettingPager(Activity myActivity) {
        super(myActivity);
    }

    @Override
    public void initData() {
        tvTitle.setText("设置");
        btnMenu.setVisibility(View.GONE);
        setSlidingMenuEnable(false);

        TextView textView = new TextView(mActivity);
        textView.setText("设置");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        flContent.addView(textView);


    }
}
