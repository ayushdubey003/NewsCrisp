package com.example.ayush.newscrisp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class BusinessImageLoader implements LoaderManager.LoaderCallbacks<List<News>> {
    private static NewsAdapter mAdapter;
    private static Context mContext;
    private static List<News> mNews = new ArrayList<News>();
    private static ArrayList<String> mUrl = new ArrayList<String>();
    private static FragmentActivity mActivity;
    private static LoaderManager mLoaderManager;
    private static int mID = 100;

    public static void boom() {
        if (mLoaderManager != null)
            mLoaderManager.destroyLoader(mID);
    }

    public BusinessImageLoader(List<News> news, NewsAdapter adapter, LoaderManager loaderManager, Context context, FragmentActivity activity, int id) {
        mContext = context;
        mAdapter = adapter;
        mLoaderManager = loaderManager;
        mID = id;
        boolean z = false;
        if (mUrl.isEmpty())
            z = true;
        for (int i = 0; i < news.size(); i++) {
            if (i >= mNews.size()) {
                mNews.add(i, news.get(i));
                mUrl.add(i, mNews.get(i).getmImgURL());
            } else {
                mNews.set(i, news.get(i));
                mUrl.set(i, mNews.get(i).getmImgURL());
            }
        }
        mActivity = activity;
        loaderManager.initLoader(id, null, this);
    }

    private static void display(List<News> newsArrayList) {
        mAdapter.clear();
        mAdapter.addAll(newsArrayList);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new ImageAsyncTask(mUrl, mContext, mAdapter);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        mAdapter.clear();
        mAdapter.addAll(news);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        mAdapter.clear();
    }

    private static class ImageAsyncTask extends AsyncTaskLoader<List<News>> {
        private static ArrayList<String> mURL = new ArrayList<String>();
        private static Context mCONTEXT;
        private static int mI;
        private static NewsAdapter mADAPTER;

        public ImageAsyncTask(ArrayList<String> mUrl, Context mContext, NewsAdapter adapter) {
            super(mContext);
            mURL = mUrl;
            mCONTEXT = mContext;
            mADAPTER = adapter;
        }

        @Nullable
        @Override
        public List<News> loadInBackground() {
            Bitmap bmp = null;
            Log.e("tis", "here");
            Log.e("tis", Integer.toString(mURL.size()));
            for (int i = 0; i < mURL.size(); i++) {
                try {
                    InputStream is = (InputStream) new URL(mURL.get(i)).getContent();
                    try {
                        bmp = BitmapFactory.decodeStream(is);
                        bmp = Bitmap.createScaledBitmap(bmp, 300, 225, true);
                    } finally {
                        is.close();
                    }
                } catch (Exception e) {
                    bmp = BitmapFactory.decodeResource(mCONTEXT.getResources(), R.drawable.nothumbnail);
                    bmp = Bitmap.createScaledBitmap(bmp, 300, 225, true);
                }
                if (mNews.get(i).getmImgId() == null)
                    mNews.set(i, new News(mNews.get(i).getmAuthor(), mNews.get(i).getmTitle(), mNews.get(i).getmSource(), mNews.get(i).getmDate(), bmp, mNews.get(i).getmArticleURL(), mNews.get(i).getmImgURL() ));
                mI = i;
                Log.e("this", "Image " + i + " loaded! ");
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mADAPTER.clear();
                        mADAPTER.addAll(mNews);
                    }
                });
            }
            return mNews;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}