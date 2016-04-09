package com.kevin.news.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kevin.news.R;

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

        myRootView = View.inflate(mActivity, R.layout.base_paper,null);

        tvTitle = (TextView) myRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) myRootView.findViewById(R.id.fl_content);
        btnMenu = (ImageButton) myRootView.findViewById(R.id.btn_menu);




    }

    public void initData() {

    }
}

