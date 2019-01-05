package com.example.ayush.newscrisp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NewsQuery {
    private static final String API_KEY = "75cf7072c2044f5d810e7cc420fb6f2a";
    private static String mArticleUrl;
    private static String mImageUrl;
    private static String mCountry;
    static Bitmap bmp;
    private static Context ActivityContext;
    private static final int LOADER_ID = 8;
    private static int i = 0;
    private static String urlToImage;

    private NewsQuery() {

    }

    public static void getCountry(String country) {
        int ind = country.indexOf("(");
        mCountry = country.substring(ind + 1, country.length() - 1);
    }

    public static List<News> getData(Context context, String url) {
        ActivityContext = context;
        url = url + "&country=" + mCountry + "&" + "apiKey=" + API_KEY;
        Log.e("this", url);
        URL urlobj = getURLObject(url);
        String JSONdata = "";
        try {
            JSONdata = createHTTPRequest(urlobj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<News> data = getList(JSONdata);
        return data;
    }

    private static URL getURLObject(String url) {
        URL Url = null;
        try {
            Url = new URL(url);
        } catch (MalformedURLException e) {
            Log.e("URL.this", e.toString());
        }
        return Url;
    }

    private static String createHTTPRequest(URL urlobj) throws IOException {
        String JSONdata = "";
        if (urlobj == null)
            return JSONdata;
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) urlobj.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(10000);
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                JSONdata = getJSONdata(inputStream);
            } else {
                Log.e("this", "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                inputStream.close();
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return JSONdata;
    }

    private static String getJSONdata(InputStream inputStream) {
        StringBuilder JSONResponse = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            String line = reader.readLine();
            while (line != null) {
                JSONResponse.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONResponse.toString();
    }

    private static List<News> getList(String jsondata) {
        if (jsondata.length() < 1)
            return null;
        List<News> data = new ArrayList<News>();
        try {
            JSONObject object = new JSONObject(jsondata);
            JSONArray article = object.getJSONArray("articles");
            for (int i = 0; i < article.length(); i++) {
                JSONObject contents = article.getJSONObject(i);
                JSONObject source_parent = contents.getJSONObject("source");
                String source = source_parent.getString("name");
                String title = contents.getString("title");
                String author = contents.getString("author");
                if (author == null || author == "null")
                    author = "Not Available";
                else
                    author = "By " + author;
                String url = contents.getString("url");
                urlToImage = contents.getString("urlToImage");
                String publishedAt = contents.getString("publishedAt");
                int in = publishedAt.indexOf('T');
                publishedAt = publishedAt.substring(0, in) + "\n" + publishedAt.substring(in + 1, publishedAt.length() - 1);
                data.add(new News(author, title, source, publishedAt, null, url, urlToImage));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
