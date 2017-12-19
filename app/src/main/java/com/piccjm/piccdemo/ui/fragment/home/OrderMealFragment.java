package com.piccjm.piccdemo.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.adapter.HomeFragmentPageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mangowangwang on 2017/11/22.
 */

public class OrderMealFragment extends Fragment {
    @BindView(R.id.tab_order) // 音乐图标下的
    TabLayout tabOrder;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;

    private ArrayList<String> mTitleList = new ArrayList<>(2);

    public ArrayList<Fragment> getmFragments() {
        return mFragments;
    }


    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    private HomeFragmentPageAdapter myAdapter;
    private View inflate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflate == null) {
            inflate = inflater.inflate(R.layout.fragment_meal_order,null); // 加载布局文件
            ButterKnife.bind(this, inflate); // 实例化fragment控件
        }
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 准备数据
        initFragmentList();
        // 创建适配器
        myAdapter = new HomeFragmentPageAdapter(getChildFragmentManager(), mFragments, mTitleList);
        // 设置适配器和刷新界面
        vpOrder.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        // 设置tab模式 fiexd 固定模式 scrollable是可以横行滚动
        tabOrder.setTabMode(TabLayout.MODE_FIXED);
        tabOrder.setupWithViewPager(vpOrder);
    }

    private void initFragmentList() {
        if (mTitleList.size() != 0) {
            return;
        }
        mTitleList.add("菜单");
        mTitleList.add("订餐");
        mFragments.add(new MealFragment());
        mFragments.add(new OrderFragment());
    }
}
