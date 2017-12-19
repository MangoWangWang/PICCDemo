package com.piccjm.piccdemo.dagger.module;


import com.piccjm.piccdemo.app.App;
import com.piccjm.piccdemo.http.service.LoginService;
import com.piccjm.piccdemo.http.service.OrderMealService;
import com.piccjm.piccdemo.http.service.SlideService;
import com.piccjm.piccdemo.http.utils.RetrofitLoginUtils;
import com.piccjm.piccdemo.http.utils.RetrofitMealOrderUtils;
import com.piccjm.piccdemo.http.utils.RetrofitSlideUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mangowangwang on 2017/10/17.
 */
@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }
//
    @Provides
    @Singleton
    RetrofitLoginUtils provideRetrofitZhiHuUtils(LoginService LoginApiService) {
        return new RetrofitLoginUtils(LoginApiService);
    }

    @Provides
    @Singleton
    RetrofitMealOrderUtils provideRetrofitOrderUtils(OrderMealService OrderApiService) {
        return new RetrofitMealOrderUtils(OrderApiService);
    }

    @Provides
    @Singleton
    RetrofitSlideUtils provideRetrofitSlideUtils(SlideService SlideApiService ) {
        return new RetrofitSlideUtils(SlideApiService);
    }

}
