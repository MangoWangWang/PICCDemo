package com.piccjm.piccdemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.utils.DateUtil;

import java.util.List;

/**
 * Created by mangowangwang on 2018/1/10.
 */

public class HistoryOrderAdapter extends BaseQuickAdapter<DateOrderBean.MealOrderBean, BaseViewHolder> {
    private List<DateOrderBean.MealOrderBean> mList;

    public HistoryOrderAdapter(List<DateOrderBean.MealOrderBean> data) {
        super(R.layout.item_history_order, data);
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, DateOrderBean.MealOrderBean item) {
        helper.setText(R.id.tv_ho_dayOfWeek, DateUtil.getDayOfWeekFromDate(item.getDate()));
        helper.setText(R.id.tv_ho_date, item.getDate());
        // 表示没有点餐,图标消失
        if (item.getBreakfast() == 0) {
            helper.setVisible(R.id.tv_ho_breakfast, false);
        }
        if (item.getLunch() == 0) {
            helper.setVisible(R.id.tv_ho_lunch, false);
        }
        if (item.getDinner() == 0) {
            helper.setVisible(R.id.tv_ho_dinner, false);
        }
    }


}
