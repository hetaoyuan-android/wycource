package com.news.today.todayinformation.main.hangzhou.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.news.today.todayinformation.main.hangzhou.view.JiKeFragment;
import com.news.today.todayinformation.main.hangzhou.view.ZhiHuFragment;

import java.util.ArrayList;

public class HangzhouViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<String> titleList = new ArrayList<>();

    public HangzhouViewPagerAdapter(FragmentManager fm) {
        super(fm);
        titleList.add("知乎");
        titleList.add("即刻");
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new ZhiHuFragment();
            case 1:
                return new JiKeFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
