package ua.bellkross.android.newsapp;


import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

public class News {

    private String mTitle;
    private String mSectionName;
    private Drawable mThumbnail;
    private String mNewsUrl;
    @Nullable
    private String mDate;
    @Nullable
    private String mAuthor;

    public News(String title, String sectionName, Drawable thumbnail, String newsUrl, String date, String author) {
        mTitle = title;
        mSectionName = sectionName;
        mThumbnail = thumbnail;
        mNewsUrl = newsUrl;
        mDate = date;
        mAuthor = author;
    }

    public News(String title, String sectionName, Drawable thumbnail, String newsUrl, String date) {
        mTitle = title;
        mSectionName = sectionName;
        mThumbnail = thumbnail;
        mNewsUrl = newsUrl;
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

    public Drawable getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Drawable thumbnail) {
        mThumbnail = thumbnail;
    }

    @Nullable
    public String getDate() {
        return mDate;
    }

    public void setDate(@Nullable String date) {
        mDate = date;
    }

    @Nullable
    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(@Nullable String author) {
        mAuthor = author;
    }

    @Override
    public String toString() {
        return "News{" +
                "mTitle='" + mTitle + '\'' +
                ", mSectionName='" + mSectionName + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                '}';
    }
}
