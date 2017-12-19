package com.piccjm.piccdemo.presenter.login;

import com.piccjm.piccdemo.ui.view.MyView;

/**
 * Created by mangowangwang on 2017/11/14.
 */

public interface LoginPresenter {

    // 赋予界面的绑定界面的能力
    interface View{
        void attachView(MyView view);
    }

    // 赋予获取数据的能力
    interface Presenter
    {
        void onGetData();  // 获取数据的能力
        void onDestroy();  // 解除订阅关系,防止内存泄漏
    }


}
