package com.piccjm.piccdemo.presenter.slide;

import com.piccjm.piccdemo.http.LifeSubscription;

/**
 * Created by mangowangwang on 2017/12/12.
 */

public interface PersonInfoPresenter {

    // 用于View的数据刷新 和进行生命周期的绑定
    interface View extends LifeSubscription {
        void refresh();
        void resultOfSexUpdate(String result);
        void resultOfError();
        // void bindSubscription(Subscription subscription);
    }

    interface Presenter{
        void fetchPersonInfo();
        void updatePersonInfo(String columnName,String data);
    }
}
