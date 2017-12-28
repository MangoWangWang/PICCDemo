package com.piccjm.piccdemo.http.utils;


import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.http.service.OrderMealService;

import okhttp3.RequestBody;
import rx.Observable;


/**
 * Created by mangowangwang on 2017/11/24.
 */

public class RetrofitMealOrderUtils {
    private OrderMealService mOrderMealService;

    public RetrofitMealOrderUtils(OrderMealService orderMealService)
    {
        this.mOrderMealService = orderMealService;
    }


    public Observable<MealStyleBean> fetchMealStyleInfo()
    {
        return mOrderMealService.getMealStyleBean();
    }

    public Observable<DateOrderBean> fetchDateOrderInfo(String hrCode,String date)
    {
        return mOrderMealService.getDateOrderBean(hrCode,date);
    }

    public Observable<String> PutDateOrderInfo(RequestBody requestBody)
    {
        return mOrderMealService.postDateOrderBean(requestBody);
    }




}
