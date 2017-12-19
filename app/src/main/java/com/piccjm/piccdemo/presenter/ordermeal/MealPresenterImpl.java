package com.piccjm.piccdemo.presenter.ordermeal;

import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.http.utils.Callback;
import com.piccjm.piccdemo.http.utils.RetrofitMealOrderUtils;
import com.piccjm.piccdemo.presenter.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mangowangwang on 2017/11/24.
 */

public class MealPresenterImpl extends BasePresenter<MealPresenter.View> implements MealPresenter.Presenter {


    private RetrofitMealOrderUtils mRetrofitMealOrderUtils;

    private List<MealStyleBean.WeekBean> weekList;


    public List<MealStyleBean.WeekBean> getWeekList() {
        return weekList;
    }



    @Inject
    public MealPresenterImpl(RetrofitMealOrderUtils retrofitOrderMealUtils)
    {
        this.mRetrofitMealOrderUtils = retrofitOrderMealUtils;
    }


    @Override
    public void fetchMealStyleList() {
        invoke(mRetrofitMealOrderUtils.fetchMealStyleInfo(),new Callback<MealStyleBean>()
                {
                    @Override
                    public void onResponse(MealStyleBean data) {
                        weekList = data.getWeek();
                        mLifeSubscription.refresh();
                    }
                }

        );
    }

    // 用于fragment中加载调用
    public void fetchData() {
        fetchMealStyleList();
    }


}
