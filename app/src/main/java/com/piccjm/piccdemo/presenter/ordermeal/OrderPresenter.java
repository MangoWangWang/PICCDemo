package com.piccjm.piccdemo.presenter.ordermeal;

import com.piccjm.piccdemo.http.LifeSubscription;

/**
 * Created by mangowangwang on 2017/11/30.
 */

public interface OrderPresenter {

    // 用于View的数据刷新 和进行生命周期的绑定
    interface View extends LifeSubscription {
        void refresh();
        // void bindSubscription(Subscription subscription);
        void PostOrderResult(String data);
    }

    interface Presenter{
        void fetchDateOrderList();
    }
}

