package com.piccjm.piccdemo.presenter.slide;

import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.http.LifeSubscription;

import java.util.List;

/**
 * Created by mangowangwang on 2018/1/3.
 */

public interface WeekMenuPresenter {

    interface View extends LifeSubscription {
        void showRecyclerView(List<MealStyleBean.DayBean> list);
    }

    interface Presenter{
        void fetchThisWeekMenuInfo();
        void fetchNextWeekMenuInfo();
    }
}
