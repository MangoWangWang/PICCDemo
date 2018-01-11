package com.piccjm.piccdemo.presenter.slide;

import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.http.utils.Callback;
import com.piccjm.piccdemo.http.utils.RetrofitSlideUtils;
import com.piccjm.piccdemo.presenter.base.BasePresenter;
import com.piccjm.piccdemo.ui.activity.MainActivity;

import javax.inject.Inject;

/**
 * Created by mangowangwang on 2018/1/9.
 */

public class HistoryOrderPresentImpl extends BasePresenter<HistoryOrderPresenter.View> implements HistoryOrderPresenter.Presenter {
    private RetrofitSlideUtils mRetrofitSlideUtils;
    private DateOrderBean historyOrder , todayOrder;


    @Inject
    public HistoryOrderPresentImpl(RetrofitSlideUtils retrofitSlideUtils) {
        mRetrofitSlideUtils = retrofitSlideUtils;
    }


    @Override
    public void fetchHistoryOrderInfo() {
        invoke(mRetrofitSlideUtils.fetchHistoryOrderInfo(MainActivity.CardNumber), new Callback<DateOrderBean>() {
                    @Override
                    public void onResponse(DateOrderBean data) {
                        super.onResponse(data);
                        mLifeSubscription.refresh(data.getMeal_order());
                    }
                }

        );

    }
}
