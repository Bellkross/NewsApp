package ua.bellkross.android.newsapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class NewsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return NewsFragment.newInstance();
    }
}
