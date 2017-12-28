package com.piccjm.piccdemo.ui.fragment.home;

import android.widget.Toast;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.presenter.ordermeal.OrderPresenter;
import com.piccjm.piccdemo.presenter.ordermeal.OrderPresenterImpl;
import com.piccjm.piccdemo.ui.fragment.base.BaseFragment;
import com.piccjm.piccdemo.ui.view.DayGroup;

import java.util.List;

import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by mangowangwang on 2017/11/30.
 */

public class OrderFragment extends BaseFragment<OrderPresenterImpl> implements OrderPresenter.View {
    private DateOrderBean mDateOrderBean;
    private List<MealStyleBean.DayBean> weekBeanList;


    @BindViews({R.id.dg_monday,R.id.dg_tuesday,R.id.dg_wednesday,R.id.dg_thursday,R.id.dg_friday,R.id.dg_saturday,R.id.dg_sunday})
    List<DayGroup> dayGroupList;


    @OnClick(R.id.jsonText)
    public void submitOrder ()
    {
        checkOrderStatus();
        mPresenter.PostDateOrder(mDateOrderBean);

    }

    @Override
    protected void loadData() {

        mPresenter.fetchData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initInject() {

        getFragmentComponent().inject(this);
    }

    @Override
    public void refresh() {

        // 准备数据
        mDateOrderBean = mPresenter.getDateOrder();

        OrderMealFragment orderMealFragment = (OrderMealFragment) getParentFragment();
        MealFragment mealFragment = (MealFragment)orderMealFragment.getmFragments().get(0);
      //  this.weekBeanList =  mealFragment.getWeekBeanList();
//
//        对数据进行分析

        checkIsOrder();

        checkIsOrderEnable();

    }

    private void checkIsOrderEnable() {
        for (int i = 0;i<weekBeanList.size();i++)
        {
            MealStyleBean.DayBean weekBean = weekBeanList.get(i);
            List<MealStyleBean.DayBean.MealBean> mealList =  weekBean.getMeal();
            DayGroup dayGroup = dayGroupList.get(i);
            for (int j = 0 ;j < mealList.size();j++)
            {
                MealStyleBean.DayBean.MealBean mealBean = mealList.get(j);
                if (!mealBean.isIsOrder())
                {
                    if (j==0)
                    {
                        dayGroup.cb_breakfast.setEnabled(false);
                    }else
                    if (j==1)
                    {
                        dayGroup.cb_lunch.setEnabled(false);
                    }
                    if (j==2)
                    {

                        dayGroup.cb_dinner.setEnabled(false);

                    }
                }

            }
        }
    }
    private void checkIsOrder()
    {
        List<DateOrderBean.MealOrderBean> mealOrderList = mDateOrderBean.getMeal_order();
        for (int i = 0;i< mealOrderList.size();i++)
        {
            DateOrderBean.MealOrderBean mealOrderBean = mealOrderList.get(i);
            DayGroup dayGroup = dayGroupList.get(i);
            if (mealOrderBean.getBreakfast() !=0)
            {
                dayGroup.cb_breakfast.setChecked(true);
            }
            if (mealOrderBean.getLunch() !=0)
            {
                dayGroup.cb_lunch.setChecked(true);
            }
            if (mealOrderBean.getDinner() !=0)
            {
                dayGroup.cb_dinner.setChecked(true);
            }

        }

     }

     // 处理Post完json数据返回的结果
     @Override
     public void PostOrderResult(String data)
     {
         Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
     }

     // 用于检验每天的三餐是否被选中
     public void checkOrderStatus()
     {
         List<DateOrderBean.MealOrderBean> mealOrderList = mDateOrderBean.getMeal_order();
         for(int i = 0 ; i< dayGroupList.size() ; i++)
         {
             DayGroup dayGroup = dayGroupList.get(i);
             DateOrderBean.MealOrderBean dayOrderBean = mealOrderList.get(i);

             if (dayGroup.cb_breakfast.isChecked())
             {
                 dayOrderBean.setBreakfast(1);
             }else
             {
                 dayOrderBean.setBreakfast(0);
             }


             if (dayGroup.cb_lunch.isChecked())
             {
                 dayOrderBean.setLunch(1);
             }else
             {
                 dayOrderBean.setLunch(0);
             }


             if (dayGroup.cb_dinner.isChecked())
             {
                 dayOrderBean.setDinner(1);
             }else
             {
                 dayOrderBean.setDinner(0);
             }

         }
     }
}
