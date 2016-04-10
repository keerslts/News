package com.kevin.news.fregment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kevin.news.R;
import com.kevin.news.bean.NewsData;
import com.kevin.news.bean.NewsData.NewsMenuData;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


/**
 * Created by Kevin on 2016/4/6.
 */
public class LeftMenuFragment extends BaseFragment {

    @ViewInject(R.id.lv_list)
    private ListView lvList;
    private ArrayList<NewsMenuData> myMenuList;
    private MenuAdapter menuAdapter;

    @Override
    public View initViews() {
        View view = View.inflate(myActivity, R.layout.fragment_left_menu, null);
        ViewUtils.inject(this, view);
        return view;
    }

    /**
     * 设置网络数据
     * @param newsData
     */
    public void setMenuData(NewsData newsData) {

        Log.i("kevin", "LeftMenuFragment:拿到Gson数据 " + newsData);
//        myMenuList = newsData.data;
//        menuAdapter = new MenuAdapter();
//        lvList.setAdapter(menuAdapter);

    }

    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myMenuList.size();
        }

        @Override
        public Object getItem(int position) {
            return myMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(myActivity, R.layout.list_menu_item, null);
            TextView tvTitle = (TextView)view.findViewById(R.id.tv_title);
            NewsMenuData newsMenuData = (NewsMenuData) getItem(position);
            tvTitle.setText(newsMenuData.title);

            return view;
        }
    }
}
