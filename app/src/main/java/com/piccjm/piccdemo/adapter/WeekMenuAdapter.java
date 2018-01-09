package com.piccjm.piccdemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.bean.MealStyleBean;

import java.util.List;

/**
 * Created by mangowangwang on 2018/1/3.
 */

public class WeekMenuAdapter extends BaseQuickAdapter<MealStyleBean.DayBean,BaseViewHolder> {

    private List<MealStyleBean.DayBean> mList;
    public WeekMenuAdapter(List<MealStyleBean.DayBean> data) {
        super(R.layout.item_menuofday,data);
        this.mList = data;

    }

    @Override
    protected void convert(final BaseViewHolder helper, MealStyleBean.DayBean item) {

        helper.setText(R.id.date_week_menu,item.getDay());

        helper.setText(R.id.breakfast_week_menu,item.getMeal().get(0).getMenuName());

        String LunchString = item.getMeal().get(1).getMenuName();
        String lunchString[] = LunchString.split(",");
        if (lunchString.length == 1)
        {
            helper.setText(R.id.lunch_roulei_week_menu,lunchString[0]);
            helper.setText(R.id.lunch_shucai_week_menu,lunchString[0]);
        }else
        {
            helper.setText(R.id.lunch_roulei_week_menu,lunchString[0]+" "+lunchString[1]);
            helper.setText(R.id.lunch_shucai_week_menu,lunchString[2]+" "+lunchString[3]);
        }


        String DinnerString = item.getMeal().get(2).getMenuName();
        String dinnerString[] = DinnerString.split(",");

        if (dinnerString.length == 1)
        {
            helper.setText(R.id.dinner_roulei_week_menu,dinnerString[0]);
            helper.setText(R.id.dinner_shucai_week_menu,dinnerString[0]);
        }else
        {
            helper.setText(R.id.dinner_roulei_week_menu,dinnerString[0]);
            helper.setText(R.id.dinner_shucai_week_menu,dinnerString[1]);
        }


    }
}
