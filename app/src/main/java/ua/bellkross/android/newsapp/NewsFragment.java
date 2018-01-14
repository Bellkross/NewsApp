package ua.bellkross.android.newsapp;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    public static final String LOG_TAG = NewsFragment.class.getSimpleName();
    private static final int NEWS_LOADER_ID = 0;
    public static String URL = "http://content.guardianapis.com/search?q=debates&" +
            "show-tags=contributor&show-fields=thumbnail&" +
            "api-key=57a35d7d-c2ae-4751-b3b3-3ba09c32f21d";
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyView;
    private NewsLoader mLoader;

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
        mProgressBar = v.findViewById(R.id.progress_bar);

        mLoader = (NewsLoader) getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        mLoader.setUrl(URL);
        mLoader.onForceLoad();

        mAdapter = new RecyclerAdapter(new ArrayList<News>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);


        return v;
    }

    public static Fragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getActivity(), mProgressBar);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        mAdapter.getNews().clear();

        if (data != null && !data.isEmpty()) {
            mAdapter.getNews().addAll(data);
        }
        mAdapter.notifyDataSetChanged();

        Log.d(LOG_TAG, "ITEM COUNT = " + String.valueOf(mAdapter.getItemCount()));

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        mAdapter.getNews().clear();
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {

        private ArrayList<News> mNews;

        public RecyclerAdapter(ArrayList<News> news) {
            mNews = news;
        }

        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item, parent, false);

            return new NewsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsViewHolder holder, int position) {
            News news = mNews.get(position);
            holder.mImageViewThumbnail.setImageDrawable(news.getThumbnail());
            holder.mTextViewTitle.setText(news.getTitle());
            holder.mTextViewTag.setText(news.getSectionName());
            holder.mTextViewTime.setText(news.getDate());
            holder.mTextViewName.setText(news.getAuthor());
        }

        @Override
        public int getItemCount() {
            return mNews.size();
        }

        public ArrayList<News> getNews() {
            return mNews;
        }

    }

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewThumbnail;
        private TextView mTextViewTitle;
        private TextView mTextViewTime;
        private TextView mTextViewName;
        private TextView mTextViewTag;

        public NewsViewHolder(View view) {
            super(view);
            mImageViewThumbnail = itemView.findViewById(R.id.ivThumbnail);
            mTextViewTitle = itemView.findViewById(R.id.tvTitle);
            mTextViewTime = itemView.findViewById(R.id.tvTime);
            mTextViewName = itemView.findViewById(R.id.tvName);
            mTextViewTag = itemView.findViewById(R.id.tvTag);
        }


    }

}