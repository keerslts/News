package com.kevin.news.base.menudetail;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.kevin.news.base.BaseMenuDetailPager;
import com.kevin.news.bean.NewsData;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/11.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    public NewsMenuDetailPager(Activity activity,
                               ArrayList<NewsData.NewsTabData> children) {
        super(activity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(myActivity);
        text.setText("新闻");
        text.setGravity(Gravity.CENTER);
        return text;
    }
}
