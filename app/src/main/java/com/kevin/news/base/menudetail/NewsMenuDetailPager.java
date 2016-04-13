package com.kevin.news.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


import com.kevin.news.R;
import com.kevin.news.base.BaseMenuDetailPager;
import com.kevin.news.base.TabDetailPager;
import com.kevin.news.bean.NewsData;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/11.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ArrayList<NewsData.NewsTabData> mNewsTabData;
    private ViewPager mViewPager;
    private ArrayList<TabDetailPager> mPagerList;
    private TabPageIndicator myIndicator;

    public NewsMenuDetailPager(Activity activity,
                               ArrayList<NewsData.NewsTabData> children) {
        super(activity);
        mNewsTabData = children;
    }

    @Override
    public View initViews() {

        View view = View.inflate(myActivity,
                R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

        myIndicator = (TabPageIndicator)view.findViewById(R.id.indicator);

        return view;
    }

    @Override
    public void initData() {
        mPagerList = new ArrayList<TabDetailPager>();

        for (int i = 0; i < mNewsTabData.size(); i++) {
            TabDetailPager pager = new TabDetailPager(myActivity, mNewsTabData.get(i));
            mPagerList.add(pager);
        }
        mViewPager.setAdapter(new MenuDetailAdapter());
        myIndicator.setViewPager(mViewPager);
    }

        private class MenuDetailAdapter extends PagerAdapter {

            @Override
            public CharSequence getPageTitle(int position) {
                return mNewsTabData.get(position).title;
            }

            @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.myRootView);
            pager.initData();
            return pager.myRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
