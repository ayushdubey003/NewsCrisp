package com.example.ayush.newscrisp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, ArrayList<News> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitems, parent, false);
        News currentNews = getItem(position);
        TextView source = (TextView) convertView.findViewById(R.id.source);
        source.setText(currentNews.getmSource());
        TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText(currentNews.getmAuthor());
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(currentNews.getmTitle());
        TextView date = (TextView) convertView.findViewById(R.id.date_);
        date.setText(currentNews.getmDate());
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        img.setImageBitmap(currentNews.getmImgId());
        return convertView;
    }
}
