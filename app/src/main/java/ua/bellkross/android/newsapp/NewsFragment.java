package ua.bellkross.android.newsapp;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

import ua.bellkross.android.newsapp.data.News;

public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks{

    public static final String LOG_TAG = NewsFragment.class.getSimpleName();
    public static String URL = "http://content.guardianapis.com/search?q=debates&" +
            "show-tags=contributor&show-fields=thumbnail&" +
            "api-key=57a35d7d-c2ae-4751-b3b3-3ba09c32f21d";
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyView;

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


        return v;
    }

    public static Fragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getActivity(), mProgressBar);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mAdapter.getNews().clear();

    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.getNews().clear();
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {

        private ArrayList<News> mNews;

        public RecyclerAdapter(ArrayList<News> news) {
            mNews = news;
        }

        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return null;
        }

        @Override
        public void onBindViewHolder(NewsViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mNews.size();
        }

        public ArrayList<News> getNews() {
            return mNews;
        }

        public void setNews(ArrayList<News> news) {
            mNews = news;
        }
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder{

        private News mNews;

        private ImageView mImageViewThumbnail;
        private TextView mTextViewTitle;
        private TextView mTextViewTime;
        private TextView mTextViewName;
        private TextView mTextViewTag;

        public NewsViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));

            mImageViewThumbnail = itemView.findViewById(R.id.ivThumbnail);
            mTextViewTitle = itemView.findViewById(R.id.tvTitle);
            mTextViewTime = itemView.findViewById(R.id.tvTime);
            mTextViewName = itemView.findViewById(R.id.tvName);
            mTextViewTag = itemView.findViewById(R.id.tvTag);
        }

    }

}