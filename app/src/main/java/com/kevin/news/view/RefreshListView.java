package com.kevin.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.kevin.news.R;

/**
 * Created by Kevin on 2016/4/17.
 */
public class RefreshListView extends ListView {

    private View mHeaderView;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initHeaderView();

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    private void initHeaderView(){

        mHeaderView = View.inflate(getContext(),R.layout.refresh_header,null);
        this.addHeaderView(mHeaderView);

        mHeaderView.measure(0,0);
        int mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
        Log.i("news", "initHeaderView: 1111");

    }
}
