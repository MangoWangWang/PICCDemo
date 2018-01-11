package com.piccjm.piccdemo.ui.fragment.slide;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.adapter.HistoryOrderAdapter;
import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.presenter.slide.HistoryOrderPresentImpl;
import com.piccjm.piccdemo.presenter.slide.HistoryOrderPresenter;
import com.piccjm.piccdemo.ui.fragment.base.BaseFragment;
import com.piccjm.piccdemo.utils.DateUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by mangowangwang on 2018/1/9.
 */

public class HistoryOrderFragment extends BaseFragment<HistoryOrderPresentImpl> implements HistoryOrderPresenter.View {
    @BindView(R.id.rv_history_order)
    RecyclerView rv_history_order;

    // 加载数据
    @Override
    protected void loadData() {
        mPresenter.fetchHistoryOrderInfo();
    }

    // 设置数据成功返回加载布局
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history_order;
    }


    // 加载布局中的一些设置
    @Override
    protected void initView() {
        rv_history_order.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    // 设置依赖注入的绑定
    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    // 数据返回刷新界面的操作

    @Override
    public void refresh(List<DateOrderBean.MealOrderBean> HistoryDateOrders) {


        View headerView = getActivity().getLayoutInflater().inflate(R.layout.history_header_view, null);

        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tv_date = (TextView) headerView.findViewById(R.id.tv_ho_header_date);
        TextView tv_breakfast = (TextView) headerView.findViewById(R.id.tv_ho_header_breakfast);
        TextView tv_lunch = (TextView) headerView.findViewById(R.id.tv_ho_header_lunch);
        TextView tv_dinner = (TextView) headerView.findViewById(R.id.tv_ho_header_dinner);
        tv_date.setText(DateUtil.getCurrentDate());
        int size = 7;
        if (HistoryDateOrders.size() < 7) {
            size = HistoryDateOrders.size();
        }
        for (int position = 0; position < size; position++) {
            if (HistoryDateOrders.get(position).getDate().equals(DateUtil.getCurrentDate())) {

                if (HistoryDateOrders.get(position).getBreakfast() != 0) {
                    tv_breakfast.setVisibility(View.VISIBLE);
                }
                if (HistoryDateOrders.get(position).getLunch() != 0) {
                    tv_lunch.setVisibility(View.VISIBLE);
                }
                if (HistoryDateOrders.get(position).getDinner() != 0) {
                    tv_dinner.setVisibility(View.VISIBLE);
                }
                HistoryDateOrders.remove(position);
                break;
            }
        }
        HistoryOrderAdapter historyOrderAdapter = new HistoryOrderAdapter(HistoryDateOrders);
        historyOrderAdapter.addHeaderView(headerView);
        rv_history_order.setAdapter(historyOrderAdapter);
    }
}
