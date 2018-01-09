package com.piccjm.piccdemo.ui.fragment.slide;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.adapter.WeekMenuAdapter;
import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.presenter.slide.WeekMenuPresenter;
import com.piccjm.piccdemo.presenter.slide.WeekMenuPresenterImpl;
import com.piccjm.piccdemo.ui.activity.slide.WeekMenuActivity;
import com.piccjm.piccdemo.ui.fragment.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by mangowangwang on 2018/1/3.
 */

public class WeekMenuFragment extends BaseFragment<WeekMenuPresenterImpl> implements WeekMenuPresenter.View {

    @BindView(R.id.rv_week_menu)
    RecyclerView rvWeekMenu;

    private static boolean isTishWeek = true;


    @Override
    protected void loadData() {

        WeekMenuActivity mWeekMenuActivity = (WeekMenuActivity) getActivity();
        if (isTishWeek) {//懒加载在可见的时候加载，会让非静态变量最终都是同一个值所以只能用静态变量。
            mPresenter.fetchThisWeekMenuInfo();
            isTishWeek = false;
        } else {
            mPresenter.fetchNextWeekMenuInfo();
            isTishWeek = true;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_week_menu;
    }

    @Override
    protected void initView() {
        rvWeekMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showRecyclerView(List<MealStyleBean.DayBean> list) {
        WeekMenuAdapter weekMenuAdapter = new WeekMenuAdapter(list);
        rvWeekMenu.setAdapter(weekMenuAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // 保证进入时候是加载本周的数据
        isTishWeek = true;
    }
}
