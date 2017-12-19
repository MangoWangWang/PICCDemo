package com.piccjm.piccdemo.dagger.component;

import android.app.Activity;

import com.piccjm.piccdemo.dagger.module.FragmentModule;
import com.piccjm.piccdemo.dagger.scope.FragmentScope;
import com.piccjm.piccdemo.ui.fragment.home.MealFragment;
import com.piccjm.piccdemo.ui.fragment.home.OrderFragment;
import com.piccjm.piccdemo.ui.fragment.slide.PersonInfoFragment;

import dagger.Component;

/**
 * Created by mangowangwang on 2017/10/30.
 */

@FragmentScope  // fragment范围标志 防止编译器报错
// 依赖于appComponent 表明在能够通过appComponent提供所依赖类的实例
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();  // 能够提供activity依赖类
    void inject (MealFragment mealFragment);
    void inject (OrderFragment orderFragment);
    void inject (PersonInfoFragment personInfoFragment);

}
