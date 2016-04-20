package com.kevin.news.base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kevin.news.activity.MainActivity;
import com.kevin.news.base.BaseMenuDetailPager;
import com.kevin.news.base.BasePager;
import com.kevin.news.base.menudetail.InteractMenuDetailPager;
import com.kevin.news.base.menudetail.NewsMenuDetailPager;
import com.kevin.news.base.menudetail.PhotoMenuDetailPager;
import com.kevin.news.base.menudetail.TopicMenuDetailPager;
import com.kevin.news.bean.NewsData;
import com.kevin.news.fregment.LeftMenuFragment;
import com.kevin.news.global.GlobalContents;
import com.kevin.news.utils.CacheUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.util.ArrayList;


/**
 * Created by Kevin on 2016/4/9.
 */
public class NewsCenterPager extends BasePager {

    private ArrayList<BaseMenuDetailPager> detailPagers;
    private NewsData myNewsData;
    public NewsCenterPager(Activity myActivity) {
        super(myActivity);
    }

    @Override
    public void initData() {
        tvTitle.setText("新闻");
        setSlidingMenuEnable(true);


        String cache = CacheUtils.getCache(GlobalContents.CATEGORIES_URL,
                mActivity);

        if (!TextUtils.isEmpty(cache)) {// 如果缓存存在,直接解析数据, 无需访问网路
            parseData(cache);
        }

//        TextView textView = new TextView(mActivity);
//        textView.setText("首页");
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(25);
//        textView.setGravity(Gravity.CENTER);
//
//        flContent.addView(textView);

        getDataFromServer();
    }

    private void getDataFromServer() {

        HttpUtils utils = new HttpUtils();

        utils.send(HttpMethod.GET, GlobalContents.CATEGORIES_URL,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Log.i("kevin", "返回结果: " + result);

                        parseData(result);

                        // 设置缓存
                        CacheUtils.setCache(GlobalContents.CATEGORIES_URL,
                                result, mActivity);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        Toast.makeText(mActivity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
    }
    private void parseData(String result) {

        Gson gson = new Gson();
        myNewsData = gson.fromJson(result, NewsData.class);
        Log.i("kevin", "解析结果: " + myNewsData);

        //刷新侧边栏的数据
        MainActivity mainUi = (MainActivity)mActivity;
        LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
        leftMenuFragment.setMenuData(myNewsData);

        detailPagers = new ArrayList<BaseMenuDetailPager>();
        detailPagers.add(new NewsMenuDetailPager(mActivity,
                myNewsData.data.get(0).children));
        detailPagers.add(new TopicMenuDetailPager(mActivity));
        detailPagers.add(new PhotoMenuDetailPager(mActivity, btnPhoto));
        detailPagers.add(new InteractMenuDetailPager(mActivity));
        setCurrentMenuDetailPager(0);
    }

    public void setCurrentMenuDetailPager(int position){

        BaseMenuDetailPager pager = detailPagers.get(position);
        flContent.removeAllViews();
        flContent.addView(pager.myRootView);

        // 设置当前页的标题
        NewsData.NewsMenuData menuData = myNewsData.data.get(position);
        tvTitle.setText(menuData.title);

        pager.initData();// 初始化当前页面的数据


        if (pager instanceof PhotoMenuDetailPager) {
            btnPhoto.setVisibility(View.VISIBLE);
        } else {
            btnPhoto.setVisibility(View.GONE);
        }
    }

}
