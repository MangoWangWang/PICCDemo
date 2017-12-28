package com.piccjm.piccdemo.presenter.ordermeal;

import com.google.gson.Gson;
import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.http.utils.Callback;
import com.piccjm.piccdemo.http.utils.RetrofitMealOrderUtils;
import com.piccjm.piccdemo.presenter.base.BasePresenter;
import com.piccjm.piccdemo.ui.activity.MainActivity;

import java.util.List;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * Created by mangowangwang on 2017/11/24.
 */

public class MealPresenterImpl extends BasePresenter<MealPresenter.View> implements MealPresenter.Presenter {


    private RetrofitMealOrderUtils mRetrofitMealOrderUtils;

    public List<MealStyleBean.DayBean> getDayOfWeekList() {
        return dayOfWeekList;
    }

    private List<MealStyleBean.DayBean> dayOfWeekList;


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
                        dayOfWeekList = data.getWeek();
                        mLifeSubscription.refresh();
                    }
                }

        );
    }

    // 用于fragment中加载调用
    public void fetchData() {
        fetchMealStyleList();
    }


    public void PostDateOrder(DateOrderBean dateOrderBean)
    {

        Gson gson = new Gson();
        String DateOrderString =  gson.toJson(dateOrderBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),DateOrderString);
        invoke(mRetrofitMealOrderUtils.PutDateOrderInfo(body),new Callback<String>(){

            @Override
            public void onResponse(String data) {
                mLifeSubscription.PostOrderResult(data);
            }
        });

    }

    public void getDateOrder(String date)
    {
        invoke(mRetrofitMealOrderUtils.fetchDateOrderInfo(MainActivity.CardNumber,date),new Callback<DateOrderBean>() {
            @Override
            public void onResponse(DateOrderBean data) {
                mLifeSubscription.setSelectedCheckBoxAndRadioButton(data);
            }
        });


    }


}
