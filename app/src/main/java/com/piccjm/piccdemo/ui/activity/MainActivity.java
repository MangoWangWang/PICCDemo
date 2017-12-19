package com.piccjm.piccdemo.ui.activity;
// picc,jm88

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.adapter.HomeFragmentPageAdapter;
import com.piccjm.piccdemo.app.App;
import com.piccjm.piccdemo.dagger.component.ActivityComponent;
import com.piccjm.piccdemo.dagger.component.DaggerActivityComponent;
import com.piccjm.piccdemo.dagger.module.ActivityModule;
import com.piccjm.piccdemo.ui.activity.base.BaseActivity;
import com.piccjm.piccdemo.ui.activity.slide.PersonInfoActivity;
import com.piccjm.piccdemo.ui.fragment.gank.GankFragment;
import com.piccjm.piccdemo.ui.fragment.home.OrderMealFragment;
import com.piccjm.piccdemo.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static String CardNumber;

    @BindView(R.id.nav_gif)
    ImageView navGifView;

    // 首页顶部工具栏最左边的View
    @BindView(R.id.fl_title_menu)
    FrameLayout nvMenu;

    // 主布局最外面的侧滑布局
    @BindView(R.id.dl_layout_main)
    DrawerLayout dlLayout;

    // 顶部工具栏
    @BindView(R.id.toolBar_main)
    Toolbar tbToolbar;

    @OnClick(R.id.fl_personInfo)
    public void StartPersonInfo() {
        Intent intent = new Intent(this,PersonInfoActivity.class);
        intent.putExtra("hrCode", CardNumber);
        startActivity(intent);
    }

    // 顶部三个控制图标的集合(RadioGroup)
    @BindView(R.id.rg_home_viewpager_contorl_main)
    RadioGroup rgHomeViewpagerContorl;

    // 用于存放fragment的ViewPager
    @BindView(R.id.vp_content_main)
    ViewPager vpContent;

    // 给title_menu添加监听方法
    @OnClick(R.id.fl_title_menu)
    public void flTitleMenu() {
        dlLayout.openDrawer(GravityCompat.START);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 调用BaseActivity的onCreate方法
        // 1.去除标题
        // 2.setContentView,通过调用getLayoutId方法
        // 3.实例化一个侧滑速度跟踪对象,用于判断是否是滑动切换页面
        // 4.将activity添加到activityList中,统一管理
        super.onCreate(savedInstanceState);

        // ButterKnife实例化控件
        ButterKnife.bind(this);
        // 调用BastActivity的setToolBar设置toolbar
        setToolBar(tbToolbar,"");

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(false);//不显示返回键
        supportActionBar.setDisplayShowTitleEnabled(false);//去除默认标题

        // 实现dagger2的依赖注入(进行绑定)
        getActivityComponent().inject(this);

        Intent intent = getIntent();
        CardNumber = intent.getStringExtra("CardNumber");
        initView();
    }

    private void initView() {
        // 加载背景动图
        GlideUtils.loadBenDi(getApplicationContext(),R.mipmap.backgroundsss,navGifView);
        TextView userName=  (TextView)findViewById(R.id.nav_userName);
        userName.setText(CardNumber);

        // 切换fragment的监听方法
        rgHomeViewpagerContorl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_home_pager_main:
                        vpContent.setCurrentItem(0);// 设置当前页面
//                        vpContent.setCurrentItem(0,false);// false去掉viewpager切换页面的动画
                        break;
                    case R.id.rb_music_pager_main:
                        vpContent.setCurrentItem(1);
                        break;
                    case R.id.rb_friends_pager_main:
                        vpContent.setCurrentItem(2);
                        break;
                }
            }
        });
        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new OrderMealFragment());
        mFragmentList.add(new GankFragment());
        vpContent.setAdapter(new HomeFragmentPageAdapter(getSupportFragmentManager(), mFragmentList));
        vpContent.setCurrentItem(1);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rgHomeViewpagerContorl.check(R.id.rb_home_pager_main);
                        break;
                    case 1:
                        rgHomeViewpagerContorl.check(R.id.rb_music_pager_main);
                        break;
                    case 2:
                        rgHomeViewpagerContorl.check(R.id.rb_friends_pager_main);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (dlLayout.isDrawerOpen(GravityCompat.START)) {
            dlLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onUserInteraction();
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }




}

