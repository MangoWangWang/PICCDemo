package com.piccjm.piccdemo.ui.activity.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;

import com.piccjm.piccdemo.app.App;
import com.piccjm.piccdemo.dagger.component.ActivityComponent;
import com.piccjm.piccdemo.dagger.component.DaggerActivityComponent;
import com.piccjm.piccdemo.dagger.module.ActivityModule;
import com.piccjm.piccdemo.http.LifeSubscription;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mangowangwang on 2017/11/22.
 * 基本activity所带的功能
 * 1.getLayoutId() 取得布局文件的id
 * 2.setToolBar() 设置toolbar的样式和中间标题
 * 3.bindSubscription() 用于获取订阅关系
 * 4.onDestroy() 解绑防止内存泄漏和从activityList中移除
 * 5.killAll() 用于退出程序
 * 6.touchFinish() 用于关闭当前activity
 * 7.getActivityComponent() 获取ActivityComponent,实现依赖注入
 * 8.dispatchTouchEvent() 判断手势是否为关闭activity
 */

public abstract class BaseActivity extends AppCompatActivity implements LifeSubscription {

    // 管理所有运行的activity
    // 不能被继承的 LinkedList双向列表,列表中的每个节点都包含了对前一个和后一个元素的引用.
    public final static List<AppCompatActivity> mActivities  = new LinkedList<AppCompatActivity>();

    public static BaseActivity activity;

    // 用于判断侧滑是否关闭activity的依据
    // 以下变量用于从左边滑动到右边关闭的变量   类似ios自带的关闭效果
    private int endX;
    private int startX;
    private int deltaX; // 水平方向增量X
    private int endY;
    private int startY;
    private int deltaY; // 垂直方向增量Y

    private View decorView; // 最上层的View
    private VelocityTracker mVelocityTracker; // 速度追踪
    private boolean isClose = true;

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }
    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }

    protected abstract int getLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 去除标题
        setContentView(getLayoutId());
        mVelocityTracker = mVelocityTracker.obtain(); // obtain()方法来获得VelocityTracker类的一个实例对象

        // 使用addMovement(MotionEvent)函数将当前的移动事件传递给VelocityTracker对象
        // 使用computeCurrentVelocity  (int units)函数来计算当前的速度，使用 getXVelocity  ()、 getYVelocity  ()函数来获得当前的速度
        decorView = getWindow().getDecorView(); // 获得最顶层的View

        // 同步代码,保证同一时间只要一个线程在调用
        synchronized (mActivities) {
            mActivities.add(this);
        }

    }

    /**
     * 子类可以直接用
     * 设置toolbar
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title); // 设置标题
        setSupportActionBar(toolbar); // 设置toolbar
        toolbar.setTitleTextColor(Color.WHITE); // 设置字体颜色
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //显示toolbar的返回按钮
        getSupportActionBar().setDisplayShowHomeEnabled(true); // 显示左上角图标
        // 设置监听方法
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 添加回退栈管理,返回上一个activity
                onBackPressed();
            }
        });
    }

    // 用于解除统一订阅的集合Subscription,防止内存泄漏
    private CompositeSubscription mCompositeSubscription;


    /**
     * 用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏。
     * 添加到mCompositeSubscription中
     */
    @Override
    public void bindSubscription(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }


    /**
     * 解除订阅,防止内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 强制关闭所有activity并杀死当前线程
     */
    public void killAll() {
        // 复制了一份mActivities 集合Å
        List<AppCompatActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (AppCompatActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    /**
     * 下面的用于侧滑关闭Activity
     */
    public void touchFinish() {
        super.finish();
        //overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
    }


    /**
     * 关闭activity时执行这个动画
     *  从左到右消失
     * @param deltaX
     */
    public void closeAnimator(int deltaX) {
        if (isClose) {
            ValueAnimator animator = ValueAnimator.ofInt(deltaX, decorView.getWidth());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
//                decorView.layout(value, 0, value + decorView.getWidth(), decorView.getHeight());
                    decorView.scrollTo(-value, 0);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator arg0) {

                }

                @Override
                public void onAnimationEnd(Animator arg0) {
                    if (isClose) {
                        touchFinish();
                    }
                }

                @Override
                public void onAnimationCancel(Animator arg0) {

                }
            });
            animator.setDuration(300);
            animator.start();
        } else {
            ValueAnimator animator = ValueAnimator.ofInt(deltaX, 0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
//                decorView.layout(value, 0, value + decorView.getWidth(), decorView.getHeight());
                    decorView.scrollTo(-value, 0);
                }
            });
            animator.setDuration(300);
            animator.start();
        }
    }


    /**
     * 用于判断手势动作是否是侧滑关闭activity
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                if (startX < getWindow().getDecorView().getWidth() / 32) {
                    return true;
                } else {
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_MOVE:
                endX = (int) ev.getRawX();
                endY = (int) ev.getRawY();
                deltaX = endX - startX;
                deltaY = endY - startY;
                if (deltaX > deltaY && startX < getWindow().getDecorView().getWidth() / 32) {
                    decorView.scrollTo(-deltaX, 0);
                    decorView.getBackground().setColorFilter((Integer) evaluateColor((float) deltaX / (float) decorView.getWidth(), Color.BLACK, Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
                    return true;
                } else {
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (-25 < xVelocity && xVelocity <= 50 && deltaX > decorView.getWidth() / 3 && startX < getWindow().getDecorView().getWidth() / 32
                        || xVelocity > 50 && startX < getWindow().getDecorView().getWidth() / 32) {
                    isClose = true;
                    closeAnimator(deltaX);
                    return true;
                } else {
                    if (deltaX > 0 && startX < getWindow().getDecorView().getWidth() / 32) {
                        isClose = false;
                        closeAnimator(deltaX);
                        return true;
                    } else {
                        if (startX < getWindow().getDecorView().getWidth() / 32) {
                            decorView.scrollTo(0, 0);
                        }
                        return super.dispatchTouchEvent(ev);
                    }
                }
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.clear();
                mVelocityTracker.recycle();
                return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 颜色变化过度
     * @param fraction 小部分
     * @param startValue
     * @param endValue
     * @return
     */
    public Object evaluateColor(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;  // 0xff的二进制代码为1111 1111
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24 |
                (startR + (int) (fraction * (endR - startR))) << 16 |
                (startG + (int) (fraction * (endG - startG))) << 8 |
                (startB + (int) (fraction * (endB - startB)));
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

}
