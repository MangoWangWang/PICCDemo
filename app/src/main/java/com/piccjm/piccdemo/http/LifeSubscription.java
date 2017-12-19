package com.piccjm.piccdemo.http;

import rx.Subscription;

/**
 * Created by mangowangwang on 2017/11/22.
 * 用于绑定订阅关系的接口
 */

public interface LifeSubscription {
    void bindSubscription(Subscription subscription);
}
