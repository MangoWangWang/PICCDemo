package com.piccjm.piccdemo.dagger.module;

import android.app.Activity;

import com.piccjm.piccdemo.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mangowangwang on 2017/11/15.
 */
@Module
public class ActivityModule {
    public Activity mActivity;
    public ActivityModule (Activity activity)
    {
        this.mActivity = activity;
    }

    @Provides  // 提供实例的方法上注解，用于告诉 Dagger 这是一个用于注入的实例
    @ActivityScope
    public Activity getActivity()
    {
        return mActivity;
    }
}
