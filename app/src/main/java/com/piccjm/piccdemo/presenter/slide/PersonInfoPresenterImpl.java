package com.piccjm.piccdemo.presenter.slide;

import com.piccjm.piccdemo.bean.UserBean;
import com.piccjm.piccdemo.http.utils.Callback;
import com.piccjm.piccdemo.http.utils.RetrofitSlideUtils;
import com.piccjm.piccdemo.presenter.base.BasePresenter;
import com.piccjm.piccdemo.ui.activity.MainActivity;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by mangowangwang on 2017/12/12.
 */

public class PersonInfoPresenterImpl extends BasePresenter<PersonInfoPresenter.View> implements PersonInfoPresenter.Presenter {

    private RetrofitSlideUtils mRetrofitSlideUtils;

    private UserBean personInfo;

    public UserBean getPersonInfo() {
        return personInfo;
    }

    @Inject
    public PersonInfoPresenterImpl(RetrofitSlideUtils retrofitSlideUtils) {
        mRetrofitSlideUtils = retrofitSlideUtils;
    }


    @Override
    public void fetchPersonInfo() {
        invoke(mRetrofitSlideUtils.fetchPersonInfo(MainActivity.CardNumber), new Callback<UserBean>() {
                    @Override
                    public void onResponse(UserBean data) {
                        personInfo = data;
                        mLifeSubscription.refresh();
                    }
                }
        );
    }

    @Override
    public void updatePersonInfo(String columnName, String data) {
        invoke(mRetrofitSlideUtils.updatePersonInfo(MainActivity.CardNumber, columnName, data), new Callback<String>() {
                    @Override
                    public void onResponse(String data) {
                        super.onResponse(data);
                        mLifeSubscription.resultOfSexUpdate(data);
                    }

                    @Override
                    public void onErrorResponse() {
                        super.onErrorResponse();
                        mLifeSubscription.resultOfError();
                    }
                }
        );
    }

    @Override
    public void uploadPersonHead(File file) {
        //构建要上传的文件
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image_head", file.getName(), requestFile);

        String descriptionString = "This is a description";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        invoke(mRetrofitSlideUtils.uploadHeadImageToService(description, body), new Callback<String>() {
                    @Override
                    public void onResponse(String data) {
                        super.onResponse(data);
                        mLifeSubscription.resultOfUploadHeadImage(data);

                    }

                    @Override
                    public void onErrorResponse() {
                        super.onErrorResponse();
                        mLifeSubscription.resultOfError();
                    }
                }
        );

    }
}
