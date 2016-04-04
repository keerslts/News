package com.kevin.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kevin.news.R;
import com.kevin.news.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/3.
 */
public class GuideActivity extends Activity {

    private final static int[] mImageIds = new int[]{
            R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private ViewPager viewPager;
    private ArrayList<ImageView> myImageViewList;
    private LinearLayout linearPointGroup;
    private View viewRedPoint;
    private int redPointWeith;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        linearPointGroup = (LinearLayout) findViewById(R.id.linear_point_group);
        viewRedPoint = (View) findViewById(R.id.view_red_point);
        btn = (Button) findViewById(R.id.btn_start);

        initViews();
        viewPager.setAdapter(new GuideAdapter());
        viewPager.addOnPageChangeListener(new GuideChangeListener());

    }

    private void initViews() {

        myImageViewList = new ArrayList<ImageView>();

        for (int i = 0; i < mImageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImageIds[i]);
            myImageViewList.add(image);
        }

        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    10, 10);
            if (i > 0) {
                params.leftMargin = 10;
            }

            point.setLayoutParams(params);
            linearPointGroup.addView(point);

        }

        /**
         * 计算两个圆点的距离，以便下面给红点移动计算
         */
        linearPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        linearPointGroup.getViewTreeObserver().
                                removeGlobalOnLayoutListener(this);
                        redPointWeith = linearPointGroup.getChildAt(1).getLeft()
                                - linearPointGroup.getChildAt(0).getLeft();
                        Log.i("keers", "距离 " + redPointWeith);
                    }
                });

    }

    public void startnow(View view) {
        PrefUtils.setBloolen(GuideActivity.this,
                "is_user_guide_showed", true);
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }


    class GuideAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(myImageViewList.get(position));
            return myImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class GuideChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            int len = (int) (positionOffset * redPointWeith) + position * redPointWeith;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                    viewRedPoint.getLayoutParams();
            params.leftMargin = len;
            viewRedPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {

            if (position == mImageIds.length - 1) {
                btn.setVisibility(View.VISIBLE);
            } else {
                btn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }
}
