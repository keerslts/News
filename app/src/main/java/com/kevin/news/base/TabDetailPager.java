package com.kevin.news.base;

import android.app.Activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove;
import com.kevin.news.R;
import com.kevin.news.bean.NewsData.NewsTabData;
import com.kevin.news.bean.TabData;
import com.kevin.news.global.GlobalContents;
import com.kevin.news.view.RefreshListView;
import com.kevin.news.view.TopNewsViewPager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/12.
 */
public class TabDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {

    NewsTabData mTabData;
    private TextView tvText;
    private String myUrl;
    private TabData mTabDetailData;
    private ArrayList<TabData.TopNewsData> mTopNewsList;
    private NewsAdapter mNewsAdapter;

    @ViewInject(R.id.lv_news_list)
    private RefreshListView listView;
    @ViewInject(R.id.vp_news)
    private TopNewsViewPager mViewPager;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.indicator)
    private CirclePageIndicator mIndicator;

    private ArrayList<TabData.TabNewsData> mNewsList; // 新闻数据集合


    public TabDetailPager(Activity activity, NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;
        myUrl = GlobalContents.SERVER_URL + mTabData.url;
    }

    @Override
    public View initViews() {
        View view = View.inflate(myActivity, R.layout.tab_detail_pager, null);
        View headerView = View.inflate(myActivity, R.layout.list_header_topnews,
        null);
        //mViewPager = (TopNewsViewPager) view.findViewById(R.id.vp_news);
        ViewUtils.inject(this, view);
        ViewUtils.inject(this, headerView);
        listView.addHeaderView(headerView);
        return view;

    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, myUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("news", "onSuccess的页签详情页返回结果:" + result);
                Log.i("news", myUrl);
                parseData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(myActivity, msg, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

    }

    protected void parseData(String result) {

        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, TabData.class);

        Log.i("news", "页签详情解析:" + mTabDetailData);

        mTopNewsList = mTabDetailData.data.topnews;

        mNewsList = mTabDetailData.data.news;


        if (mTopNewsList != null) {
            mViewPager.setAdapter(new TopNewsAdapter());
            //mViewPager.addOnPageChangeListener(this);
            mIndicator.setViewPager(mViewPager);
            //mIndicator.setSnap(true);  快照
            mIndicator.setOnPageChangeListener(this);
            mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点
            tvTitle.setText(mTopNewsList.get(0).title);
        }

        if (mNewsList != null) {
            mNewsAdapter = new NewsAdapter();
            listView.setAdapter(mNewsAdapter);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabData.TopNewsData topNewsData = mTopNewsList.get(position);

        tvTitle.setText(topNewsData.title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class NewsAdapter extends BaseAdapter {

        private BitmapUtils utils;

        public NewsAdapter() {
            utils = new BitmapUtils(myActivity);
            utils.configDefaultLoadingImage(R.mipmap.pic_item_list_default);

        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public TabData.TabNewsData getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            Log.i("news", "mNewslist " + mNewsList.size());

            if (convertView == null) {
                convertView = View.inflate(myActivity, R.layout.news_item_list,
                        null);
                holder = new ViewHolder();
                holder.ivPic = (ImageView) convertView
                        .findViewById(R.id.iv_pic);
                holder.tvTitle = (TextView) convertView
                        .findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView
                        .findViewById(R.id.tv_date);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            TabData.TabNewsData item = getItem(position);

            holder.tvTitle.setText(item.title);
            holder.tvDate.setText(item.pubdate);

            utils.display(holder.ivPic, item.listimage);

            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivPic;
    }

    class TopNewsAdapter extends PagerAdapter {
        private BitmapUtils utils;

        public TopNewsAdapter() {
            utils = new BitmapUtils(myActivity);
            utils.configDefaultLoadingImage(R.mipmap.topnews_item_default);// 设置默认图片
        }

        @Override
        public int getCount() {
            return mTabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView image = new ImageView(myActivity);
            //  image.setImageResource(R.mipmap.topnews_item_default);// 设置默认图片

            image.setScaleType(ImageView.ScaleType.FIT_XY);
            TabData.TopNewsData topNewsData = mTopNewsList.get(position);
            utils.display(image, topNewsData.topimage);// 传递imagView对象和图片地址

            container.addView(image);

            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
