package com.example.ayush.newscrisp;

import android.graphics.Bitmap;

public class News {
    private String mAuthor;
    private String mTitle;
    private String mSource;
    private String mDate;
    private Bitmap mImgId;
    private String mArticleURL;
    private String mImgURL;

    public News(String author, String title, String source, String date, Bitmap ImgId, String articleURL, String ImgURL) {
        mAuthor = author;
        mTitle = title;
        mSource = source;
        mDate = date;
        mImgId = ImgId;
        mArticleURL = articleURL;
        mImgURL = ImgURL;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSource() {
        return mSource;
    }

    public Bitmap getmImgId() {
        return mImgId;
    }

    public String getmArticleURL() {
        return mArticleURL;
    }

    public String getmImgURL() {
        return mImgURL;
    }
}
