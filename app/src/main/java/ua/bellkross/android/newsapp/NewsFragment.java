package ua.bellkross.android.newsapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ua.bellkross.android.newsapp.data.News;

public class NewsFragment extends Fragment {

    public static final String LOG_TAG = NewsFragment.class.getSimpleName();
    public static String URL = "http://content.guardianapis.com/" +
            "search?q=debate&tag=politics/politics&from-date=2018-01-01&" +
            "show-tags=contributor&api-key=57a35d7d-c2ae-4751-b3b3-3ba09c32f21d&" +
            "show-fields=thumbnail";
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        mRecyclerView = v.findViewById(R.id.news_recycler_view);

        TestAsyncTask tat = new TestAsyncTask();
        URL = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2018-01-01&show-fields=thumbnail&api-key=57a35d7d-c2ae-4751-b3b3-3ba09c32f21d";
        URL = "http://content.guardianapis.com/search?q=debates&show-tags=contributor&show-fields=thumbnail&api-key=57a35d7d-c2ae-4751-b3b3-3ba09c32f21d";
        tat.execute(URL);
        return v;
    }

    public static Fragment newInstance() {
        return new NewsFragment();
    }

    private class TestAsyncTask extends AsyncTask <String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... uris) {
            ArrayList<News> news = QueryUtils.fetchNewsData(uris[0]);
            return news;
        }

        @Override
        protected void onPostExecute(ArrayList<News> newsArrayList) {
            for (News news : newsArrayList) {
                Log.d(LOG_TAG, news.toString());
            }
        }
    }
}
