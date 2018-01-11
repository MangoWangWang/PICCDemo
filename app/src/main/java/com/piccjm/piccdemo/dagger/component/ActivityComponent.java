package com.piccjm.piccdemo.dagger.component;

import android.app.Activity;

import com.piccjm.piccdemo.dagger.module.ActivityModule;
import com.piccjm.piccdemo.dagger.scope.ActivityScope;
import com.piccjm.piccdemo.ui.activity.LoginActivity;
import com.piccjm.piccdemo.ui.activity.MainActivity;
import com.piccjm.piccdemo.ui.activity.WelcomeActivity;
import com.piccjm.piccdemo.ui.activity.slide.HistoryOrderActivity;
import com.piccjm.piccdemo.ui.activity.slide.PersonInfoActivity;

import dagger.Component;

/**
 * Created by mangowangwang on 2017/11/15.
 */
// 用于标志component的范围,防止编译器报错
@ActivityScope
// 用来注解一个接口，在编译的时候会生成
// Dagger+文件名 的新Java文件。Component可以理解为注射器，它是连接被注入的类与需要被注入的类之间的桥梁。
// 依赖于appComponent 表明在能够通过appComponent提供所依赖类的实例
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();  // 提供activity实例
    void inject(LoginActivity loginActivity);  // 对activity进行绑定
    void inject(MainActivity mainActivity);
    void inject(PersonInfoActivity personInfoActivity);
    void inject(WelcomeActivity welcomeActivity);
    void inject(HistoryOrderActivity historyOrderActivity);

}
