package com.stepyen.yframe.core.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.stepyen.yframe.core.core.EventBusManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * ================================================
 * {@link FragmentManager.FragmentLifecycleCallbacks} 默认实现类
 * 通过 {@link IFragmentDelegate} 管理 {@link Fragment}
 */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    public FragmentLifecycle() {
    }

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment fragment, Context context) {
        if (fragment instanceof IFragment) {
            IFragment iFragment = (IFragment) fragment;
            if (iFragment.isUseEventBus())//如果要使用eventbus请将此方法返回true
                EventBusManager.getInstance().register(fragment);//注册到事件主线
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {

    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {

    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {

    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {

    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {

    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {

    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment fragment) {

        if (fragment instanceof IFragment) {
            IFragment iFragment = (IFragment) fragment;
            if (iFragment.isUseEventBus())//如果要使用eventbus请将此方法返回true
                EventBusManager.getInstance().unregister(fragment);//注册到事件主线
        }

    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {

    }



}
