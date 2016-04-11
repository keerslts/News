package com.kevin.news.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.kevin.news.R;
import com.kevin.news.fregment.ContentFragment;
import com.kevin.news.fregment.LeftMenuFragment;

/**
 * Created by Kevin on 2016/4/4.
 */
public class MainActivity extends SlidingFragmentActivity {

    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.left_menu);// 设置侧边栏
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(300);

        initFragment();

    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.activity_main,
                new ContentFragment(), FRAGMENT_CONTENT);
        fragmentTransaction.replace(R.id.left_menu,
                new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
        fragmentTransaction.commit();

    }

    public LeftMenuFragment getLeftMenuFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        LeftMenuFragment leftMenuFragment = (LeftMenuFragment)
                fragmentManager.findFragmentByTag(FRAGMENT_LEFT_MENU);
        return leftMenuFragment;
    }

    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ContentFragment fragment = (ContentFragment) fm.
                findFragmentByTag(FRAGMENT_CONTENT);
        return fragment;
    }
}
