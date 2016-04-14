package com.kevin.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Kevin on 2016/4/14.
 */
public class HorizontalViewPager extends ViewPager {
    public HorizontalViewPager(Context context) {
        super(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发, 请求父控件及祖宗控件是否拦截事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentItem()!=0) {
            getParent().requestDisallowInterceptTouchEvent(true);//不拦截
        }else{
            getParent().requestDisallowInterceptTouchEvent(false);//拦截
        }
        return super.dispatchTouchEvent(ev);
    }
}
