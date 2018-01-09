package com.piccjm.piccdemo.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.piccjm.piccdemo.app.App;
import com.piccjm.piccdemo.dagger.component.DaggerFragmentComponent;
import com.piccjm.piccdemo.dagger.component.FragmentComponent;
import com.piccjm.piccdemo.dagger.module.FragmentModule;
import com.piccjm.piccdemo.http.LifeSubscription;
import com.piccjm.piccdemo.http.Stateful;
import com.piccjm.piccdemo.presenter.base.BasePresenter;
import com.piccjm.piccdemo.ui.view.LoadingPage;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mangowangwang on 2017/11/22.
 * 加载过程 : 1.先执行setUserVisibleHint  判断是否在可见,不可见不缓存
 * 2.执行onVisible() 实现依赖注入,实例化该fragment的Presenter
 * 2.1 根据inject注解调用XXpresenter的构造方法
 * 2.2 根据参数RetrofitXXUtils mRetrofitXXUtils 到Appmodule中中寻找提供的provide
 * 2.3 根据provide方法的参数(xxService xxApiService) 到Httpmodule中寻找提供的provide
 * 2.4 根据provide方法的参数(@xxUrl Retrofit retrofit) 到Httpmodule中寻找提供的provide
 * 2.5 根据provide方法的参数(Retrofit.Builder builder, OkHttpClient client)到Httpmodule中寻找提供的provide
 * 2.6 调用httpModule中的createRetrofit()提供一个retrofit实例
 * 2.7 调用httpModule中的provideXXService()返回一个XXService
 * 2.8 调用AppModule中的provideRetrofitXXUtils提供2.2中请求的mRetrofitXXUtils
 * 2.9 最后调用构造函数完成mPresenter的实例化 (完美)
 * 3.动用xxPresenter的setLifeSubscription将basefragment实现的LifeSubscription接口传递进去(用于在httpUtil实现订阅操作的同时回调basefragment中的方法实现从而拿到绑定的实例,在basefragment中进行解绑操作)
 * 4.调用loadBaseData方法,返回return
 * 5.调用onCreateView方法 ,创建一个loadingPage的匿名子类,实现其中的抽象方法.
 * 5.1 isPrepared = true;
 * 5.2 调用loadBaseData();进行第一次的数据请求操作
 * 5.2.1 回调XXfragment中实现的loadate方法
 * 5.2.2 调用mPresent中实现的XXPresenter.Presenter接口的fetchData()方法,去请求网络数据
 * 5.2.3 调用XXPresenterImpl中的fetchXXDate()
 * 5.2.4 调用BasePresenter中定义的invoke方法 ,参数mRetrofitXXUtils.fetchXXInfo() 提供observable , new Callback<XXBean>()提供observe对获取的数据进行进一步的出来
 * 5.2.5 调用httpUtil中的invoke方法,进行订阅操作(真正网络请求操作过程),其中mLifeSubscription由之前的 mPresenter.setLifeSubscription(this)传入,其实真正指向的地址是一个XXfragment的实例(有点利用向上转型的概念),
 * 5.2.6 lifecycle instanceof Stateful 判断是否为特定的类别,由于lifecycle的真正指向的地址为实现了stateful接口的fragment,所以判断为true
 * 5.2.7 target = (Stateful) lifecycle;取得fragment设置当前网络状态的接口
 * 5.2.8 callback.setTarget(target); 设置到callback对象上,方便后面回调设置网络状态
 * 5.2.9 !NetworkUtils.isConnected() 判断网络是否连接
 * 5.2.10 observable.subscribe(callback); 订阅的同时在io线程里面进行网络请求
 * 5.2.11 回调接口bindSubscription(subscription);获得订阅事件对象
 * 5.2.12 到callback对象中,当请求出现error时,!NetworkUtils.isAvailableByPing() 判断网络是否联通 设置状态 否则的话就是请求地址为空,即为没有资源
 * 5.2.13 请求成功的话,调用onNext方法,设置状态为成功,调用实例callback的重写的onResponse的方法进行数据处理
 * 5.2.13.1 设置状态成功,调用setState(int state)中的showPage方法
 * 5.2.13.2 根据状态为成功,加载一个contentView,layout布局从xxfragment重写的getLayoutId中获得
 * 5.2.13.2 调用basefragment中匿名loadingpage对象的initView方法,进行界面的绑定和实例化
 * 5.2.14 最后回调接口xxpresenter中的refresh接口,进行界面的刷新
 * 5.3 返回Loadingpage页面,状态为加载中(执行速度比5.2要快,网络请求为耗时操作)
 */




public abstract class BaseFragment <P extends BasePresenter> extends Fragment implements LifeSubscription,Stateful {

    @Inject
    protected P mPresenter;  // 参数类型化(交换机)

    public LoadingPage mLoadingPage;  // 加载页面

    private boolean mIsVisible = false;     // fragment是否显示了

    private boolean isPrepared = false;  // 是否准备好

    private boolean isFirst = true; // 只加载一次界面


    protected View contentView;

    private Unbinder bind; // 解绑工具

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 采用匿名类的方式 ,实例化mLoadingPage,因为LoadingPage是抽象类型,必须重写抽象方法
        if (mLoadingPage == null) {
            mLoadingPage = new LoadingPage(getContext()) {
                @Override
                protected void initView() {
                    if (isFirst) {
                        // this.contentView 返回的的加载状态视图(成功,失败) 根据状态
                        // BaseFragment.this.contentView 原BaseFragment中的contentView
                        BaseFragment.this.contentView = this.contentView;

                        // 进行界面的绑定
                        bind = ButterKnife.bind(BaseFragment.this, contentView);

                        // 进行界面初始化
                        BaseFragment.this.initView();

                        isFirst = false;
                    }
                }

                @Override
                protected void loadData() {
                    BaseFragment.this.loadBaseData();
                }

                @Override
                protected int getLayoutId() {
                    return BaseFragment.this.getLayoutId();
                }
            };
        }
        isPrepared = true;
        loadBaseData(); // 第一次加载基础数据
        return mLoadingPage;  // 返回加载中的状态页面(这里只会返回加载中的页面)
    }



    /**
     * 在这里实现Fragment数据的缓加载.
     * 实现显示时加载数据,不显示时不缓存数据
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            //fragment可见
            mIsVisible = true;
            onVisible();  // 第一次可见时,实现dagger依赖注入的操作
        } else {
            //fragment不可见
            mIsVisible = false;
            onInvisible();
        }
    }

    @Override
    public void setState(int state) {
        mLoadingPage.state = state;
        mLoadingPage.showPage();  // 根据网络请求的结果改变改变展示的页面
    }

    protected void onInvisible() {
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void onVisible() {
        // 只有第一次才进行依赖注入绑定
        if (isFirst) {
            initInject();
            mPresenter.setLifeSubscription(this);
        }
        loadBaseData();//根据获取的数据来调用showView()切换界面
    }


    public void loadBaseData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        loadData();  // 1.加载数据 ->具体及执行
    }

    /**
     * 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected abstract void loadData();

    /**
     * 2
     * 网络请求成功在去加载布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 3
     * 子类关于View的操作(如setAdapter)都必须在这里面，会因为页面状态不为成功，而binding还没创建就引用而导致空指针。
     * loadData()和initView只执行一次，如果有一些请求需要二次的不要放到loadData()里面。
     */
    protected abstract void initView();

    /**
     * dagger2注入
     */
    protected abstract void initInject();

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }


    private CompositeSubscription mCompositeSubscription;

    //用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏。
    @Override
    public void bindSubscription(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (bind != null) {
            bind.unbind();
        }
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

}
