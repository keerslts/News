package com.kevin.news.fregment;

import android.view.View;

import com.kevin.news.R;

/**
 * Created by Kevin on 2016/4/6.
 */
public class LeftMenuFragment extends BaseFragment {

    @Override
    public View initViews() {
        View view = View.inflate(myActivity, R.layout.fragment_left_menu,null);
        return view;
    }
}