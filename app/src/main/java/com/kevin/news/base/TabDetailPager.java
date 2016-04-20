package com.kevin.news.base;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove;
import com.kevin.news.R;
import com.kevin.news.activity.NewsDetailActivity;
import com.kevin.news.bean.NewsData.NewsTabData;
import com.kevin.news.bean.TabData;
import com.kevin.news.global.GlobalContents;
import com.kevin.news.utils.CacheUtils;
import com.kevin.news.utils.PrefUtils;
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
    private TopNewsAdapter topNewsAdapter;

    @ViewInject(R.id.lv_news_list)
    private RefreshListView listView;
    @ViewInject(R.id.vp_news)
    private TopNewsViewPager mViewPager;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.indicator)
    private CirclePageIndicator mIndicator;

    private ArrayList<TabData.TabNewsData> mNewsList; // 新闻数据集合
    private String mMoreUrl;// 更多页面的地址

    private Handler mHandler;


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

        // 设置下拉刷新监听
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                if (mMoreUrl != null) {
                    getMoreDataFromServer();
                } else {
                    Toast.makeText(myActivity, "最后一页了", Toast.LENGTH_SHORT)
                            .show();
                    listView.onRefreshComplete(false);// 收起加载更多的布局
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("被点击:" + position);
                // 35311,34221,34234,34342
                // 在本地记录已读状态

                String ids = PrefUtils.getString(myActivity, "read_ids", "");
                String readId = mNewsList.get(position).id;
                if (!ids.contains(readId)) {
                    ids = ids + readId + ",";
                    PrefUtils.setString(myActivity, "read_ids", ids);
                }

                // mNewsAdapter.notifyDataSetChanged();
                changeReadState(view);// 实现局部界面刷新, 这个view就是被点击的item布局对象

                // 跳转新闻详情页
                Intent intent = new Intent();
                intent.setClass(myActivity, NewsDetailActivity.class);
                intent.putExtra("url", mNewsList.get(position).url);
                myActivity.startActivity(intent);
            }
        });

        return view;

    }

    private void changeReadState(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(myUrl, myActivity);

        if (!TextUtils.isEmpty(cache)) {
            parseData(cache, false);
        }

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
                parseData(result, false);
                listView.onRefreshComplete(true);

                // 设置缓存
                CacheUtils.setCache(myUrl, result, myActivity);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(myActivity, msg, Toast.LENGTH_SHORT).show();
                error.printStackTrace();

                listView.onRefreshComplete(false);
            }
        });

    }

    private void getMoreDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                parseData(result, true);

                listView.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(myActivity, msg, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                listView.onRefreshComplete(false);
            }
        });
    }

    protected void parseData(String result, boolean isMore) {

        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, TabData.class);

        Log.i("news", "页签详情解析:" + mTabDetailData);

        String more = mTabDetailData.data.more;
        if (!TextUtils.isEmpty(more)) {
            mMoreUrl = GlobalContents.SERVER_URL + more;
        } else {
            mMoreUrl = null;
        }

        if (!isMore) {
            mTopNewsList = mTabDetailData.data.topnews;

            mNewsList = mTabDetailData.data.news;


            if (mTopNewsList != null) {
                //mViewPager.setAdapter(new TopNewsAdapter());
                topNewsAdapter = new TopNewsAdapter();
                mViewPager.setAdapter(topNewsAdapter);

                //mViewPager.addOnPageChangeListener(this);
                mIndicator.setViewPager(mViewPager);
                mIndicator.setSnap(true);  //快照
                mIndicator.setOnPageChangeListener(this);
                mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点
                Log.i("news", "0 " + mTopNewsList.size());
                tvTitle.setText(mTopNewsList.get(0).title);
            }

            if (mNewsList != null) {
                mNewsAdapter = new NewsAdapter();
                listView.setAdapter(mNewsAdapter);
            }

            // 自动轮播条显示
            if (mHandler == null) {
                mHandler = new Handler() {
                    public void handleMessage(android.os.Message msg) {
                        int currentItem = mViewPager.getCurrentItem();

                        if (currentItem < mTopNewsList.size() - 1) {
                            currentItem++;
                        } else {
                            currentItem = 0;
                        }

                        mViewPager.setCurrentItem(currentItem);// 切换到下一个页面
                        Log.i("news", "1 " + mTopNewsList.size()+" "+currentItem);
                        mHandler.sendEmptyMessageDelayed(0, 3000);// 继续延时3秒发消息,
                        // 形成循环
                    }


                };

                mHandler.sendEmptyMessageDelayed(0, 3000);// 延时3秒后发消息
            }

        } else {// 如果是加载下一页,需要将数据追加给原来的集合
            ArrayList<TabData.TabNewsData> news = mTabDetailData.data.news;
            mNewsList.addAll(news);
            topNewsAdapter.notifyDataSetChanged();
            mNewsAdapter.notifyDataSetChanged();

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

            String ids = PrefUtils.getString(myActivity, "read_ids", "");
            if (ids.contains(getItem(position).id)) {
                holder.tvTitle.setTextColor(Color.GRAY);
            } else {
                holder.tvTitle.setTextColor(Color.BLACK);
            }

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

           // mViewPager.setOffscreenPageLimit(1);
            ImageView image = new ImageView(myActivity);
            //image.setImageResource(R.mipmap.topnews_item_default);// 设置默认图片

            image.setScaleType(ImageView.ScaleType.FIT_XY);
            TabData.TopNewsData topNewsData = mTopNewsList.get(position);
            utils.display(image, topNewsData.topimage);// 传递imagView对象和图片地址

            container.addView(image);
            image.setOnTouchListener(new TopNewsTouchListener());
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    /**
     * 新闻列表的适配器
     *
     * @author Kevin
     *
     */
    class TopNewsTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("按下");
                    mHandler.removeCallbacksAndMessages(null);// 删除Handler中的所有消息
                    // mHandler.postDelayed(new Runnable() {
                    //
                    // @Override
                    // public void run() {
                    //
                    // }
                    // }, 3000);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    System.out.println("事件取消");
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("抬起");
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                    break;

                default:
                    break;
            }

            return true;
        }

    }


}
