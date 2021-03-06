package com.piccjm.piccdemo.http.utils;

import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.bean.UserBean;
import com.piccjm.piccdemo.http.service.SlideService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by mangowangwang on 2017/12/12.
 */

public class RetrofitSlideUtils {

    private SlideService mslideService;

    public RetrofitSlideUtils(SlideService slideService)
    {
        this.mslideService = slideService;
    }

    public Observable<UserBean> fetchPersonInfo(String hrCode)
    {
        return mslideService.getPersonInfoBean(hrCode);
    }

    public Observable<String> updatePersonInfo(String hrCode,String columnName,String data)
    {
        return mslideService.updatePerSonInfo(hrCode,columnName,data);
    }

    public Observable<MealStyleBean> fetchThisWeekMenuInfo()
    {
        return mslideService.getMealStyleBean("this");
    }
    public Observable<MealStyleBean> fetchNextWeekMenuInfo()
    {
        return mslideService.getMealStyleBean("next");
    }

    public Observable<String> uploadHeadImageToService(RequestBody description, MultipartBody.Part file)
    {
        return mslideService.upload(description, file);
    }

    public Observable<DateOrderBean> fetchHistoryOrderInfo(String hrCode)
    {
        return mslideService.getHistoryOrderInfoBean(hrCode);
    }


}
