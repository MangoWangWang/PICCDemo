package com.piccjm.piccdemo.presenter.slide;

import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.http.utils.Callback;
import com.piccjm.piccdemo.http.utils.RetrofitSlideUtils;
import com.piccjm.piccdemo.presenter.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by mangowangwang on 2018/1/3.
 */

public class WeekMenuPresenterImpl extends BasePresenter<WeekMenuPresenter.View> implements WeekMenuPresenter.Presenter {

    private RetrofitSlideUtils mRetrofitSlideUtils;

    @Inject
    public WeekMenuPresenterImpl(RetrofitSlideUtils mRetrofitSlideUtils) {
        this.mRetrofitSlideUtils = mRetrofitSlideUtils;
    }

    @Override
    public void fetchThisWeekMenuInfo() {
        invoke(mRetrofitSlideUtils.fetchThisWeekMenuInfo(), new Callback<MealStyleBean>()
                {
                    @Override
                    public void onResponse(MealStyleBean data) {
                        mLifeSubscription.showRecyclerView(data.getWeek());
                    }
                }
        );
    }

    @Override
    public void fetchNextWeekMenuInfo() {
        invoke(mRetrofitSlideUtils.fetchNextWeekMenuInfo(), new Callback<MealStyleBean>()
                {
                    @Override
                    public void onResponse(MealStyleBean data) {
                        mLifeSubscription.showRecyclerView(data.getWeek());
                    }
                }
        );
    }
}
