package com.piccjm.piccdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by mangowangwang on 2017/11/22.
 */

public class HomeFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;  // fragment列表
    private List<String> mTitleList;  // 标题列表

    public HomeFragmentPageAdapter(FragmentManager fm,List<Fragment> fragmentList ) {
        super(fm);
        this.mFragmentList = fragmentList;

    }
    public HomeFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList ) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return (mFragmentList == null ? 0 : mFragmentList.size());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        } else {
            return "";
        }
    }
}
