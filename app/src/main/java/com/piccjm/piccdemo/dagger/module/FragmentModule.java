package com.piccjm.piccdemo.dagger.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.piccjm.piccdemo.dagger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mangowangwang on 2017/10/30.
 */
@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}