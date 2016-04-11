package com.kevin.news.base.menudetail;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.kevin.news.base.BaseMenuDetailPager;

/**
 * Created by Kevin on 2016/4/11.
 */
public class InteractMenuDetailPager extends BaseMenuDetailPager {

    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(myActivity);
        text.setText("网络");
        text.setGravity(Gravity.CENTER);
        return text;
    }
}
