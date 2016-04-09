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
public class HomePager extends BasePager {

    public HomePager(Activity myActivity) {
        super(myActivity);
    }

    @Override
    public void initData() {
        tvTitle.setText("智慧北京");
        btnMenu.setVisibility(View.GONE);
        setSlidingMenuEnable(false);

        TextView textView = new TextView(mActivity);
        textView.setText("首页");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        flContent.addView(textView);


    }
}
