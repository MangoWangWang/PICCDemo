package com.piccjm.piccdemo.http.service;

import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.bean.MealStyleBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mangowangwang on 2017/11/24.
 */

public interface OrderMealService {

    // 本地请求地址的baseUrl
    String HOST = "http://10.0.10.187:8080/";

    // 阿里云服务器地址IP地址
    //String HOST = "http://120.79.62.147:8080/PiccLife/";

    // 请求菜单
    @GET("servlet/GetMealStyleServlet")
    Observable<MealStyleBean> getMealStyleBean();

    // 请求已订阅的对象
    @GET("servlet/GetDateOrderServlet")
    Observable<DateOrderBean> getDateOrderBean(@Query("card_number") String hrCode,@Query("date") String date);


    // 用于上传订单json到服务器中
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("servlet/PostDateOrderServlet")
    Observable<String> postDateOrderBean(@Body RequestBody requestBody);
}
