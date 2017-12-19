package com.piccjm.piccdemo.ui.view;

/**
 * Created by mangowangwang on 2017/11/14.
 */

public interface LoginView  extends MyView {

    void onSuccess(String status); // 成功请求返回的状态
    void onError(String result);  // 失败请求返回的结果
}
