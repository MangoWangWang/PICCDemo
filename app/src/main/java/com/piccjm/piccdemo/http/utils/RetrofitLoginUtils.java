package com.piccjm.piccdemo.http.utils;

import com.piccjm.piccdemo.http.service.LoginService;

import rx.Observable;

/**
 * Created by mangowangwang on 2017/11/15.
 * 用于对loginService的进一步封装
 * 提供直接返回observable的接口
 */

public class RetrofitLoginUtils {
    // 实际运行中由dagger2框架的依赖注入提供实例
    private LoginService mLoginService;

    public RetrofitLoginUtils(LoginService LoginService) {
        this.mLoginService = LoginService;
    }

    // 返回observable
    public Observable<String> getLoginStatus(String cardNumber ,String password) {
        return mLoginService.getLoginStatus(cardNumber,password);
    }

}
