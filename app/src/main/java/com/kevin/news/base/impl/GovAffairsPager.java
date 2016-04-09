package com.kevin.news.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.kevin.news.base.BasePager;

/**
 * Created by Kevin on 2016/4/9.
 */
public class GovAffairsPager extends BasePager {

    public GovAffairsPager(Activity myActivity) {
        super(myActivity);
    }

    @Override
    public void initData() {
        tvTitle.setText("人口管理");
        setSlidingMenuEnable(true);

        
        TextView textView = new TextView(mActivity);
        textView.setText("政务");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        flContent.addView(textView);


    }
}
