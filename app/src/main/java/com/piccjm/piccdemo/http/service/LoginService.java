package com.piccjm.piccdemo.http.service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mangowangwang on 2017/11/13.
 */

public interface LoginService {
    // 本地请求地址的baseUrl
    String HOST = "http://10.0.10.187:8080/";

    // 阿里云服务器地址IP地址
    //String HOST = "http://120.79.62.147:8080/PiccLife/";


    //http://localhost:8080/servlet/GetDataServlet

    /**
     * 登录请求
     * 使用get方法
     * @Query 作为get方法的参数
     */
    @GET("servlet/LoginServlet")
    Observable<String> getLoginStatus(@Query("cardNumber") String cardNumber, @Query("password") String password);


}
