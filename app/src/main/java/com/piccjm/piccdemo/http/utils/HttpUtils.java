package com.piccjm.piccdemo.http.utils;

import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.piccjm.piccdemo.app.AppConstants;
import com.piccjm.piccdemo.http.LifeSubscription;
import com.piccjm.piccdemo.http.Stateful;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mangowangwang on 2017/10/27.
 * 1.invoken
 */

public class HttpUtils {

    public static <T> void invoke(LifeSubscription lifecycle, Observable<T> observable, Callback<T> callback) {
        Stateful target = null;
        // 判断lifecycle是否为stateful的实例
        // 获得之前的网络状态
        if (lifecycle instanceof Stateful) {
            target = (Stateful) lifecycle;
            callback.setTarget(target);
        }
        /**
         * 先判断网络连接状态和网络是否可用，放在回调那里好呢，还是放这里每次请求都去判断下网络是否可用好呢？
         * 如果放在请求前面太耗时了，如果放回掉提示的速度慢，要10秒钟请求超时后才提示。
         * 最后采取的方法是判断网络是否连接放在外面，网络是否可用放在回掉。
         */
        // 如果现在网络断开,修改activity或fragment中的网络状态
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShortToast("网络连接已断开");
            if (target != null) {
                target.setState(AppConstants.STATE_ERROR);
            }
            return;
        }

        // 实现网络网络请求
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
        // 调用具体类型的fragment重写方法进行绑定
        // @Override
       //  public void bindSubscription(Subscription subscription)
        lifecycle.bindSubscription(subscription);

    }
}
