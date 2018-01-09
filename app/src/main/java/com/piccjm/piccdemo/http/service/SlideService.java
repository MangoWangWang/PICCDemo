package com.piccjm.piccdemo.http.service;

import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.bean.UserBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mangowangwang on 2017/12/12.
 */

public interface SlideService {

    // 本地服务器请求地址的baseUrl
    //String HOST = "http://10.0.10.187:8080/";

    // 阿里云服务器地址IP地址
    String HOST = "http://120.79.62.147:8080/PiccLife/";

    @GET("servlet/GetPersonInfoServlet")
    Observable<UserBean> getPersonInfoBean(@Query("cardNumber") String cardNumber);

    @GET("servlet/UpdatePersonInfoServlet")
    Observable<String> updatePerSonInfo(@Query("cardNumber")String cardNumber,@Query("columnName")String columnName,@Query("data")String data);

    // 请求菜单
    @GET("servlet/GetMealStyleServlet")
    Observable<MealStyleBean> getMealStyleBean(@Query("week") String week);

    @Multipart
    @POST("servlet/UploadImageServlet")
    Observable<String> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
}
