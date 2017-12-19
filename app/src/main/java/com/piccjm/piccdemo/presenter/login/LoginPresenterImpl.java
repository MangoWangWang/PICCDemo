package com.piccjm.piccdemo.presenter.login;

import com.piccjm.piccdemo.http.utils.RetrofitLoginUtils;
import com.piccjm.piccdemo.ui.view.LoginView;
import com.piccjm.piccdemo.ui.view.MyView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mangowangwang on 2017/11/14.
 */

public class LoginPresenterImpl implements LoginPresenter.View,LoginPresenter.Presenter{

    // 用户名,密码
    private String  name, password;

    // 用于请求数据的被观察者
    Observable<String> observable;

    // 提供界面回调的方法
    private LoginView loginView;

    //private DataManager dataManager;

    // observable提供类,同时也是http帮助类
    private RetrofitLoginUtils mRetrofitLoginUtils;

    private String status;
    //private Context mContext;

    // 绑定订阅关系
    private CompositeSubscription mCompositeSubscription;

    @Inject  // 依赖注入提供的构造方法
    public LoginPresenterImpl(RetrofitLoginUtils retrofitLoginUtils)
    {
        this.mRetrofitLoginUtils = retrofitLoginUtils;
        mCompositeSubscription = new CompositeSubscription();
    }


    // 获取数据
    @Override
    public void onGetData() {
        observable = mRetrofitLoginUtils.getLoginStatus(name,password);
    }


    // 绑定视图
    @Override
    public void attachView(MyView view) {
        loginView = (LoginView)view;
    }


    public void setNameAndPassword(String name,String password)
    {
        this.name = name;
        this.password = password;
    }


    // 处理获取数据与绑定视图之间的关系
    public void getLoginStatus() {
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if (status!=null)
                        {
                            loginView.onSuccess(status);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loginView.onError("网络请求错误,请检查连接！");
                    }

                    @Override
                    public void onNext(String s) {
                        status = s;
                    }
                });

        mCompositeSubscription.add(subscription);
    }



    // 解绑订阅关系
    @Override
    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
    }



}
