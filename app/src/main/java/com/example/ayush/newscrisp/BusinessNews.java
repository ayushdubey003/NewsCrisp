package com.example.ayush.newscrisp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class BusinessNews extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {
    private static NewsAdapter mAdapter;
    private static int LOADER_ID = 1;
    private static final String URL = "https://newsapi.org/v2/top-headlines?category=business";
    private static TextView mEmptyStateTextView;
    private static View Progress;

    public BusinessNews() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview, container, false);
        Log.e("HHH", "HERE look");
        ArrayList<News> news = new ArrayList<>();
        mAdapter = new NewsAdapter(getActivity(), news);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BusinessImageLoader.boom();
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


    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncNewsLoader(URL, getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        mAdapter.clear();
        //Progress = getActivity().findViewById(R.id.progress);
        //Progress.setVisibility(View.GONE);
        if (news != null) {
            mAdapter.addAll(news);
            new BusinessImageLoader(news, mAdapter, getLoaderManager(), getContext(), getActivity(), 100);
        } else mEmptyStateTextView.setText("No News Items To publish");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
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
