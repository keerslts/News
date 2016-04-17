package com.kevin.news.bean;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/15.
 */
public class TabData {

    public int retcode;
    public TabDetail data;

    public class TabDetail {

        public String Title;
        public String more;
        public ArrayList<TabNewsData> news;
        public ArrayList<TopNewsData> topnews;

        @Override
        public String toString() {
            return "TabDetail{" + "Title='" + Title + '\'' +
                    ", news=" + news + ", topNews=" + topnews + '}';
        }
    }


    public class TabNewsData{
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TabNewsData{" +
                    "id='" + id + '\'' +
                    ", listimage='" + listimage + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
    public class TopNewsData{
        public String id;
        public String topimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TopNewsData{" + "title='" + title + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "TabData{" + "data=" + data + '}';
    }
}
