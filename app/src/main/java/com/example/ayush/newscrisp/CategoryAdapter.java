package com.example.ayush.newscrisp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0)
            return new BusinessNews();
        else if(i==1)
            return new EntertainmentNews();
        else if(i==2)
            return new GeneralNews();
        else if(i==3)
            return new HealthNews();
        else if(i==4)
            return new ScienceNews();
        else if(i==5)
            return new SportsNews();
        else
            return new TechnologyNews();
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return mContext.getString(R.string.business);
        else if(position==1)
            return mContext.getString(R.string.entertainment);
        else if(position==2)
            return mContext.getString(R.string.general);
        else if(position==3)
            return mContext.getString(R.string.health);
        else if(position==4)
            return mContext.getString(R.string.science);
        else if(position==5)
            return mContext.getString(R.string.sports);
        else
            return mContext.getString(R.string.technology);
    }
}
