package com.kevin.news.base;

import android.app.Activity;
import android.os.ParcelUuid;
import android.view.View;

/**
 * Created by Kevin on 2016/4/11.
 */
public abstract class BaseMenuDetailPager {

    public Activity myActivity;
    public View myRootView;

    public BaseMenuDetailPager(Activity activity) {

        myActivity = activity;
        myRootView = initViews();
    }

    public abstract View initViews();

    public void initData(){

    }

}
