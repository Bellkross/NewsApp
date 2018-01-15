package ua.bellkross.android.newsapp;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    public static String URL = "http://content.guardianapis.com/search?q=debates&" +
            "show-tags=contributor&show-fields=thumbnail&" +
            "api-key=57a35d7d-c2ae-4751-b3b3-3ba09c32f21d";
    private static final int NEWS_LOADER_ID = 0;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyView;
    private NewsLoader mLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        mProgressBar = v.findViewById(R.id.progress_bar);
        mRecyclerView = v.findViewById(R.id.news_recycler_view);
        mAdapter = new RecyclerAdapter(new ArrayList<News>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEmptyView = v.findViewById(R.id.empty_view);

        mLoader = (NewsLoader) getActivity().
                getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        mLoader.setUrl(URL);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mLoader.forceLoad();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static Fragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getActivity(), mProgressBar);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        mAdapter.getNews().clear();

        if (data != null && !data.isEmpty()) {
            mAdapter.getNews().addAll(data);
            mAdapter.notifyDataSetChanged();
        } else {
            mProgressBar.setVisibility(View.GONE);

            ConnectivityManager cm =
                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if (isConnected) {
                mEmptyView.setText(R.string.no_news);
            } else {
                mEmptyView.setText(R.string.no_internet);
            }
            mEmptyView.setVisibility(View.VISIBLE);
            return;
        }
        mProgressBar.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.getNews().clear();
        mAdapter.notifyDataSetChanged();
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
            holder.mImageViewThumbnail.
                    setImageDrawable(new BitmapDrawable(getResources(), news.getThumbnailBitmap()));
            holder.mTextViewTitle.setText(news.getTitle());
            holder.mTextViewSectionName.setText(news.getSectionName());
            holder.mTextViewDate.setText(news.getDate());
            holder.setListener(news);
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

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewThumbnail;
        private TextView mTextViewTitle;
        private TextView mTextViewDate;
        private TextView mTextViewSectionName;

        public NewsViewHolder(View view) {
            super(view);
            mImageViewThumbnail = itemView.findViewById(R.id.ivThumbnail);
            mTextViewTitle = itemView.findViewById(R.id.tvTitle);
            mTextViewDate = itemView.findViewById(R.id.tvDate);
            mTextViewSectionName = itemView.findViewById(R.id.tvSectionName);
        }

        public void setListener(final News news) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri newsURI = Uri.parse(news.getNewsUrl());

                    Intent webIntent = new Intent(Intent.ACTION_VIEW, newsURI);

                    startActivity(webIntent);
                }
            });
        }

    }

}