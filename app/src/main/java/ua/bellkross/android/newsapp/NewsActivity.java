package ua.bellkross.android.newsapp;

import android.app.Fragment;

public class NewsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return NewsFragment.newInstance();
    }
}
