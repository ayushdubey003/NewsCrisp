package com.example.ayush.newscrisp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScienceNews extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final String URL = "https://newsapi.org/v2/top-headlines?category=science";
    private static NewsAdapter mAdapter;
    private static int LOADER_ID = 5;
    private static TextView mEmptyStateTextView;
    private static View Progress;

    public ScienceNews() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview, container, false);
        ArrayList<News> news = new ArrayList<>();
        mAdapter = new NewsAdapter(getActivity(), news);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ScienceImageLoader.boom();
                News currentNews = (News) adapterView.getItemAtPosition(i);
                String s = currentNews.getmArticleURL();
                Log.e("this", s);
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", s);
                startActivity(intent);
            }
        });
        listView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncNewsLoader(URL, getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mAdapter.clear();
        if (news != null) {
            mAdapter.addAll(news);
            new ScienceImageLoader(news, mAdapter, getLoaderManager(), getContext(), getActivity(), 500);
        } else mEmptyStateTextView.setText("No News Items To publish");
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

    private static class AsyncNewsLoader extends AsyncTaskLoader<List<News>> {
        private static String mUrl;

        public AsyncNewsLoader(String url, Context context) {
            super(context);
            mUrl = url;
        }

        @Nullable
        @Override
        public List<News> loadInBackground() {
            if (mUrl == null)
                return null;
            List<News> news = NewsQuery.getData(getContext(), mUrl);
            return news;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}
