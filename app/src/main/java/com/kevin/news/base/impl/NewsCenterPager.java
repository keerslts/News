package com.kevin.news.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kevin.news.activity.MainActivity;
import com.kevin.news.base.BasePager;
import com.kevin.news.bean.NewsData;
import com.kevin.news.fregment.LeftMenuFragment;
import com.kevin.news.global.GlobalContents;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;


/**
 * Created by Kevin on 2016/4/9.
 */
public class NewsCenterPager extends BasePager {

    //private ArrayList<>
    private NewsData myNewsData;
    public NewsCenterPager(Activity myActivity) {
        super(myActivity);
    }

    @Override
    public void initData() {
        tvTitle.setText("新闻");
        setSlidingMenuEnable(true);


        TextView textView = new TextView(mActivity);
        textView.setText("首页");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        flContent.addView(textView);

        getDataFromServer();
    }

    private void getDataFromServer() {

        HttpUtils utils = new HttpUtils();

        utils.send(HttpMethod.GET, GlobalContents.CATEGORIES_URL,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = (String) responseInfo.result;
                        Log.i("kevin", "返回结果: " + result);

                        parseData(result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        Toast.makeText(mActivity,msg,Toast.LENGTH_SHORT).show();
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
    }

}
