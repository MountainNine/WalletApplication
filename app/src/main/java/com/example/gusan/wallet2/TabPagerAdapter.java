package com.example.gusan.wallet2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by gusan on 2017. 1. 3..
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount)
    {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                DayTabFragment dayTabFragment = new DayTabFragment();
                return dayTabFragment;
            case 1:
                WeekTabFragment weekTabFragment = new WeekTabFragment();
                return weekTabFragment;
            case 2:
                MonthTabFragment monthTabFragment = new MonthTabFragment();
                return monthTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return tabCount;
    }
}
