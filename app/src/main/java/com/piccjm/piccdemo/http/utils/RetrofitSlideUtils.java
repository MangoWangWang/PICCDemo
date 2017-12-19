package com.piccjm.piccdemo.http.utils;

import com.piccjm.piccdemo.bean.UserBean;
import com.piccjm.piccdemo.http.service.SlideService;

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

}
