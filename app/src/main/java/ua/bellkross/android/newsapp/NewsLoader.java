package ua.bellkross.android.newsapp;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import static ua.bellkross.android.newsapp.NewsFragment.LOG_TAG;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>>{

    private String mUrl;
    private ProgressBar mProgressBar;

    public NewsLoader(Context context, ProgressBar progressBar) {
        super(context);
        mProgressBar = progressBar;
    }

    @Override
    public ArrayList<News> loadInBackground() {
        mProgressBar.setVisibility(View.VISIBLE);

        ArrayList<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }
}