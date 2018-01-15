package ua.bellkross.android.newsapp;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class News {

    private String mTitle;
    private String mSectionName;
    private String mNewsUrl;
    private Bitmap mThumbnailBitmap;
    @Nullable
    private String mDate;

    public News(String title, String sectionName, String newsUrl, Bitmap thumbnailBitmap, String date) {
        mTitle = title;
        mSectionName = sectionName;
        mNewsUrl = newsUrl;
        mThumbnailBitmap = thumbnailBitmap;
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public void setSectionName(String sectionName) {
        mSectionName = sectionName;
    }

    public String getNewsUrl() {
        return mNewsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        mNewsUrl = newsUrl;
    }

    public Bitmap getThumbnailBitmap() {
        return mThumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
        mThumbnailBitmap = thumbnailBitmap;
    }

    @Nullable
    public String getDate() {
        return mDate;
    }

    public void setDate(@Nullable String date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return "News{" +
                "mTitle='" + mTitle + '\'' +
                ", mSectionName='" + mSectionName + '\'' +
                ", mNewsUrl='" + mNewsUrl + '\'' +
                ", mThumbnailBitmap=" + mThumbnailBitmap +
                ", mDate='" + mDate + '\'' +
                '}';
    }
}
