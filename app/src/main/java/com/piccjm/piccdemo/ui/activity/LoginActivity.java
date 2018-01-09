package com.piccjm.piccdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.app.App;
import com.piccjm.piccdemo.bean.other.UserManage;
import com.piccjm.piccdemo.dagger.component.ActivityComponent;
import com.piccjm.piccdemo.dagger.component.DaggerActivityComponent;
import com.piccjm.piccdemo.dagger.module.ActivityModule;
import com.piccjm.piccdemo.presenter.login.LoginPresenterImpl;
import com.piccjm.piccdemo.ui.view.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity  {

    // @BindView ButterKnife框架的注释

    @BindView(R.id.user_login)
    EditText user_login_edit;

    @BindView(R.id.passWard_login)
    EditText password_login_edit;

    private Unbinder unbinder;

    // 依赖注入presenter
    @Inject
    LoginPresenterImpl loginPresenter;

    // 用于回调接口,代理模式
    private LoginView loginView = new LoginView() {
        @Override
        public void onSuccess(String status) {
            int result = Integer.parseInt(status);
            if(result == 1) // 返回1,表示密码正确,登录成功
            {
                // 保存密码到SharedPreferences
                saveUserInfo();
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("CardNumber", user_login_edit.getText().toString());
                startActivity(intent);
                finish();
            }else if (result == 0) // 返回0 ,表示密码或账户错误
            {
                Toast.makeText(LoginActivity.this, "账户或密码错误", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(String result) {
            // TODO: 2017/11/14 网络出现错误的处理
            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//            intent.putExtra("hrCode", user_login_edit.getText().toString());
//            startActivity(intent);
        }
    };

    @OnClick(R.id.button_login)
    public void loginClick(){
        // 传递参数
        loginPresenter.setNameAndPassword(user_login_edit.getText().toString(),password_login_edit.getText().toString());
        // 获取数据
        loginPresenter.onGetData();
        // 处理逻辑
        loginPresenter.getLoginStatus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 去除标题
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
       //  mCompositeSubscription = new CompositeSubscription();
       //  initRetrofit();
        //loginPresenter = new LoginPresenterImpl(this);
        // 进行依赖注入
        getActivityComponent().inject(this);
        // presenter绑定界面
        loginPresenter.attachView(loginView);  // 绑定指定的View(回调activity中方法)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        loginPresenter.onDestroy();  // 解绑订阅关系,防止内存泄漏
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

    protected void saveUserInfo()
    {
        String userName = user_login_edit.getText().toString();
        String userPwd = password_login_edit.getText().toString();
        UserManage.getInstance().saveUserInfo(LoginActivity.this, userName, userPwd);
    }




}
