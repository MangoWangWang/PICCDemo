package com.piccjm.piccdemo.ui.activity.slide;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.ui.activity.base.BaseActivity;
import com.piccjm.piccdemo.ui.fragment.slide.PersonInfoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mangowangwang on 2017/12/11.
 */

public class PersonInfoActivity extends BaseActivity {


    private PersonInfoFragment mPersonInfoFragment;

    // 顶部工具栏
    @BindView(R.id.toolBar_personInfo)
    Toolbar tbToolbar;
    @BindView(R.id.fl_personInfo)
    FrameLayout personInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 调用BaseActivity的onCreate方法
        // 1.去除标题
        // 2.setContentView,通过调用getLayoutId方法
        // 3.实例化一个侧滑速度跟踪对象,用于判断是否是滑动切换页面
        // 4.将activity添加到activityList中,统一管理
        super.onCreate(savedInstanceState);

        // ButterKnife实例化控件
        ButterKnife.bind(this);
        // 调用BastActivity的setToolBar设置toolbar
        setToolBar(tbToolbar,"个人设置");

        //ActionBar supportActionBar = getSupportActionBar();

        // 实现dagger2的依赖注入(进行绑定)
        getActivityComponent().inject(this);
        initFragment();

    }


    //显示第一个fragment
    private void initFragment(){
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if(mPersonInfoFragment == null){
            mPersonInfoFragment = new PersonInfoFragment();

            transaction.replace(R.id.fl_personInfo, mPersonInfoFragment);
            mPersonInfoFragment.setUserVisibleHint(true);
        }
        //隐藏所有fragment
        //hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(mPersonInfoFragment);

        //第二种方式(replace)，初始化fragment
//        if(f1 == null){
//            f1 = new MyFragment("消息");
//        }
//        transaction.replace(R.id.main_frame_layout, f1);

        //提交事务
        transaction.commit();
    }


}
