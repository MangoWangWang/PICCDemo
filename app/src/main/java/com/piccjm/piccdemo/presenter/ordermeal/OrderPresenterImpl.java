package com.piccjm.piccdemo.presenter.ordermeal;

import com.google.gson.Gson;
import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.http.utils.Callback;
import com.piccjm.piccdemo.http.utils.RetrofitMealOrderUtils;
import com.piccjm.piccdemo.presenter.base.BasePresenter;

import javax.inject.Inject;

import okhttp3.RequestBody;

/**
 * Created by mangowangwang on 2017/11/30.
 */

public class OrderPresenterImpl extends BasePresenter<OrderPresenter.View> implements OrderPresenter.Presenter {

    private RetrofitMealOrderUtils mRetrofitMealOrderUtils;

    private DateOrderBean dateOrder;

    public DateOrderBean getDateOrder() {
        return dateOrder;
    }



    @Inject
    public OrderPresenterImpl(RetrofitMealOrderUtils retrofitMealOrderUtils)
    {
        this.mRetrofitMealOrderUtils = retrofitMealOrderUtils;
    }


    @Override
    public void fetchDateOrderList() {
//        invoke(mRetrofitMealOrderUtils.fetchDateOrderInfo(MainActivity.CardNumber,),new Callback<DateOrderBean>()
//                {
//                    @Override
//                    public void onResponse(DateOrderBean data) {
//                        dateOrder = data;
//                        mLifeSubscription.refresh();
//                    }
//                }
//
//        );
    }

    // 用于fragment中加载调用
    public void fetchData() {
        fetchDateOrderList();
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

}
