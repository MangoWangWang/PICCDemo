package com.piccjm.piccdemo.dagger.module;

import com.piccjm.piccdemo.dagger.qualifier.LoginUrl;
import com.piccjm.piccdemo.dagger.qualifier.OrderMealUrl;
import com.piccjm.piccdemo.dagger.qualifier.SlideUrl;
import com.piccjm.piccdemo.http.service.LoginService;
import com.piccjm.piccdemo.http.service.OrderMealService;
import com.piccjm.piccdemo.http.service.SlideService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by mangowangwang on 2017/11/15.
 */
@Module
public class HttpModule {
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }


    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 提供LoginService
     */
    @Singleton
    @Provides
    @LoginUrl
    Retrofit provideLoginRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, LoginService.HOST);
    }

    @Singleton
    @Provides
    LoginService provideLoginService(@LoginUrl Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }



    /**
     * 提供OrderMealService
     */

    @Singleton
    @Provides
    @OrderMealUrl
    Retrofit provideOrderMealRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, OrderMealService.HOST);
    }

    @Singleton
    @Provides
    OrderMealService provideOrderMealService(@OrderMealUrl Retrofit retrofit) {
        return retrofit.create(OrderMealService.class);
    }


    /**
     * 提供SlideService
     */

    @Singleton
    @Provides
    @SlideUrl
    Retrofit provideSlideRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, SlideService.HOST);
    }

    @Singleton
    @Provides
    SlideService provideSlideService(@SlideUrl Retrofit retrofit) {
        return retrofit.create(SlideService.class);
    }

}
