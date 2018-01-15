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
        Log.d(LOG_TAG, "NewsLoader");
        mProgressBar = progressBar;
    }

    @Override
    public ArrayList<News> loadInBackground() {
        Log.d(LOG_TAG, "loadInBackground");

        ArrayList<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }

    @Override
    protected void onForceLoad() {
        Log.d(LOG_TAG, "onForceLoad");

        mProgressBar.setVisibility(View.VISIBLE);
        super.onForceLoad();
    }

    @Override
    protected boolean onCancelLoad() {
        Log.d(LOG_TAG, "onCancelLoad");

        mProgressBar.setVisibility(View.GONE);
        return super.onCancelLoad();
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