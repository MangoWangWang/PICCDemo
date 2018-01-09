package com.piccjm.piccdemo.ui.activity.slide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.adapter.HomeFragmentPageAdapter;
import com.piccjm.piccdemo.ui.activity.base.BaseActivity;
import com.piccjm.piccdemo.ui.fragment.slide.WeekMenuFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mangowangwang on 2018/1/3.
 */

public class WeekMenuActivity extends BaseActivity {

    @BindView(R.id.toolbar_week_menu)
    Toolbar toolbarWeekMenu;
    @BindView(R.id.tab_week_menu)
    TabLayout tabWeekMenu;
    @BindView(R.id.vp_week_menu)
    ViewPager vpWeekMenu;


    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    private HomeFragmentPageAdapter myAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_week_menu;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolBar(toolbarWeekMenu,"菜单预览");
        initFragmentList();
        myAdapter = new HomeFragmentPageAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vpWeekMenu.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        tabWeekMenu.setTabMode(TabLayout.MODE_FIXED);
        tabWeekMenu.setupWithViewPager(vpWeekMenu);
    }


    private void initFragmentList() {
        if (mTitleList.size() != 0) {
            return;
        }
        mTitleList.add("本周菜单");
        mTitleList.add("下周菜单");
        mFragments.add(new WeekMenuFragment());
        mFragments.add(new WeekMenuFragment());
    }
}
