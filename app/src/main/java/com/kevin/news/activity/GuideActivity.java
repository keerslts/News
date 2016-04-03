package com.kevin.news.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.kevin.news.R;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/3.
 */
public class GuideActivity extends Activity {

    private final static int[] mImageIds = new int[]{
            R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private ViewPager viewPager;
    private ArrayList<ImageView> myImageViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.vp_guide);

        initViews();
        viewPager.setAdapter(new GuideAdapter());

    }

    private void initViews() {

        myImageViewList = new ArrayList<ImageView>();

        for (int i = 0; i < mImageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImageIds[i]);
            myImageViewList.add(image);
        }
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
}
