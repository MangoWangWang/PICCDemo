package com.piccjm.piccdemo.presenter.slide;

import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.http.LifeSubscription;

import java.util.List;

/**
 * Created by mangowangwang on 2018/1/9.
 */

public interface HistoryOrderPresenter {
    // 用于View的数据刷新 和进行生命周期的绑定
    interface View extends LifeSubscription {
        void refresh(List<DateOrderBean.MealOrderBean> HistoryDateOrders);
        // void bindSubscription(Subscription subscription);
    }

    interface Presenter{
        void fetchHistoryOrderInfo();
    }
}
