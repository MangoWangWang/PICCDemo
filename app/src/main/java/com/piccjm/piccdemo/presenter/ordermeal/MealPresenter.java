package com.piccjm.piccdemo.presenter.ordermeal;

import com.piccjm.piccdemo.http.LifeSubscription;

/**
 * Created by mangowangwang on 2017/11/24.
 */

public interface MealPresenter {

    // 用于View的数据刷新 和进行生命周期的绑定
    interface View extends LifeSubscription {
        void refresh();
        // void bindSubscription(Subscription subscription);
    }

    interface Presenter{
        void fetchMealStyleList();
    }
}
