package com.piccjm.piccdemo.dagger.component;


import com.piccjm.piccdemo.app.App;
import com.piccjm.piccdemo.dagger.module.AppModule;
import com.piccjm.piccdemo.dagger.module.HttpModule;
import com.piccjm.piccdemo.http.utils.RetrofitLoginUtils;
import com.piccjm.piccdemo.http.utils.RetrofitMealOrderUtils;
import com.piccjm.piccdemo.http.utils.RetrofitSlideUtils;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mangowangwang on 2017/10/17.
 */

@Singleton  // 单例scope 标识范围,防止编译器报错
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    App getContext();  // 提供App的单例Context
    /**
     * 提供http的帮助类
     * 更换链接的请求，需要添加如AppModule的provideRetrofitZhiHuUtils()方法 命名规则provideRetrofitXXXUtils()
     * HttpModule的provideZhiHuRetrofit()和provideZhihuService() 命名规则provideXXXService
     * 还有以下方法 命名规则retrofitXXXUtils  命名规则怎么开心怎么来。
     * @return
     */
    RetrofitLoginUtils retrofitLoginUtils();  // 提供目标类所依赖的http的帮助类
    RetrofitMealOrderUtils retrofitOrderMealUtils();  //  提供目标类所依赖的http的帮助类
    RetrofitSlideUtils retrofitSlideUtils();   //  提供目标类所依赖的http的帮助类

}
