package com.kevin.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Kevin on 2016/4/15.
 */
public class TopNewsViewPager extends ViewPager {

    int startX;
    int startY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                getParent().requestDisallowInterceptTouchEvent(true);

                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:

                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();

                if (Math.abs(endX - startX) > Math.abs(endY - startY)) {// 左右滑动
                    if (endX > startX) {// 右划
                        if (getCurrentItem() == 0) {// 第一个页面, 需要父控件拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }else {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    } else {// 左划
                        if (getCurrentItem() == getAdapter().getCount() - 1) {// 最后一个页面,
                            // 需要拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {// 上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            default:

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
