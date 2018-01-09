package com.piccjm.piccdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.app.App;
import com.piccjm.piccdemo.bean.other.UserInfo;
import com.piccjm.piccdemo.bean.other.UserManage;
import com.piccjm.piccdemo.dagger.component.ActivityComponent;
import com.piccjm.piccdemo.dagger.component.DaggerActivityComponent;
import com.piccjm.piccdemo.dagger.module.ActivityModule;
import com.piccjm.piccdemo.presenter.login.LoginPresenterImpl;
import com.piccjm.piccdemo.ui.view.LoginView;

import javax.inject.Inject;

public class WelcomeActivity extends AppCompatActivity {

    private static final int GO_MAIN = 0;//去主页
    private static final int GO_LOGIN = 1;//去登录页
    private static final int GO_lOGIN_NO_INTERNET = 2;

    // 依赖注入presenter
    @Inject
    LoginPresenterImpl loginPresenter;


    UserInfo locationUserInfo;



    // 用于回调接口,代理模式
    private LoginView loginView = new LoginView() {
        @Override
        public void onSuccess(String status) {
            int result = Integer.parseInt(status);
            if(result == 1) // 返回1,表示密码正确,登录成功
            {
                // 保存密码到SharedPreferences
                mHandler.sendEmptyMessageDelayed(GO_MAIN, 2000);

            }else if (result == 0) // 返回0 ,表示密码或账户错误
            {

                mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);

            }

        }

        @Override
        public void onError(String result) {
            mHandler.sendEmptyMessageDelayed(GO_lOGIN_NO_INTERNET, 2000);


        }
    };



    /**
     * 跳转判断
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_MAIN://去主页

                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    intent.putExtra("CardNumber",locationUserInfo.getUserName());
                    startActivity(intent);
                    finish();
                    Toast.makeText(WelcomeActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    break;
                case GO_LOGIN://去登录页
                    Intent intent2 = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                    Toast.makeText(WelcomeActivity.this, "账户或密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case GO_lOGIN_NO_INTERNET :
                    Intent intent3 = new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(intent3);
                    finish();
                    Toast.makeText(WelcomeActivity.this, "网络连接错误,请检查网络", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 去除标题
        setContentView(R.layout.activity_welcome);
        // 进行依赖注入
        getActivityComponent().inject(this);

        loginPresenter.attachView(loginView);



        if (UserManage.getInstance().hasUserInfo(this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            locationUserInfo = UserManage.getInstance().getUserInfo(this);

            loginPresenter.setNameAndPassword(locationUserInfo.getUserName().toString(),locationUserInfo.getPassword().toString());
            // 获取数据
            loginPresenter.onGetData();
            // 处理逻辑
            loginPresenter.getLoginStatus();

        } else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
        }
    }


    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())  // 依赖于appComponent 实现单例  提供全局context 提供http帮助类
                .activityModule(getActivityModule())  // module为ActivityModule
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}




