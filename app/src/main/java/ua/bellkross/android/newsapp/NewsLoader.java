package ua.bellkross.android.newsapp;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String mUrl;
    private ProgressBar mProgressBar;

    public NewsLoader(Context context, ProgressBar progressBar) {
        super(context);
        mProgressBar = progressBar;
    }

    @Override
    public ArrayList<News> loadInBackground() {
        ArrayList<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }

    @Override
    protected void onForceLoad() {
        mProgressBar.setVisibility(View.VISIBLE);
        super.onForceLoad();
    }

    @Override
    protected boolean onCancelLoad() {
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