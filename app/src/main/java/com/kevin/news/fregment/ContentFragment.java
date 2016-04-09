package com.kevin.news.fregment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.kevin.news.R;
import com.kevin.news.base.BasePager;
import com.kevin.news.base.impl.GovAffairsPager;
import com.kevin.news.base.impl.HomePager;
import com.kevin.news.base.impl.NewsCenterPager;
import com.kevin.news.base.impl.SettingPager;
import com.kevin.news.base.impl.SmartServicePager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/6.
 */
public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.rg_group)
    private RadioGroup radioGroup;
    @ViewInject(R.id.vp_content)
    private ViewPager myViewPager;

    private ArrayList<BasePager> myPagerList;


    @Override
    public View initViews() {
        View view = View.inflate(myActivity, R.layout.fragment_content, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        radioGroup.check(R.id.rb_home);
        myPagerList = new ArrayList<BasePager>();
//        for (int i = 0; i < 5; i++) {
//            BasePager pager = new BasePager(myActivity);
//            myPagerList.add(pager);
//        }

        myPagerList.add(new HomePager(myActivity));
        myPagerList.add(new NewsCenterPager(myActivity));
        myPagerList.add(new SmartServicePager(myActivity));
        myPagerList.add(new GovAffairsPager(myActivity));
        myPagerList.add(new SettingPager(myActivity));

        myViewPager.setAdapter(new ContentAdapter());
    }

    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return myPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = myPagerList.get(position);
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
