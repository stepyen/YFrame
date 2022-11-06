package com.stepyen.yframe.core.core.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.stepyen.yframe.core.core.AppManager;
import com.stepyen.yframe.core.core.EventBusManager;
import com.stepyen.yframe.core.util.YFrameUtils;


/**
 * ================================================
 * {@link Application.ActivityLifecycleCallbacks} 默认实现类
 * 通过 {@link ActivityDelegate} 管理 {@link Activity}
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    AppManager mAppManager;
    Context mContext;
    public ActivityLifecycle() {
        mAppManager = AppManager.getAppManager();
        mContext = YFrameUtils.getAppContext();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //如果 intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理
        boolean isNotAdd = false;
        if (activity.getIntent() != null)
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);

        if (!isNotAdd)
            mAppManager.addActivity(activity);

        //配置ActivityDelegate
        if (activity instanceof IActivity) {

            //如果要使用 EventBus 请将此方法返回 true
            if (((IActivity) activity).isUseEventBus()){
                //注册到事件主线
                EventBusManager.getInstance().register(activity);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mAppManager.setCurrentActivity(activity);


    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (mAppManager.getCurrentActivity() == activity) {
            mAppManager.setCurrentActivity(null);
        }

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mAppManager.removeActivity(activity);

        //如果要使用 EventBus 请将此方法返回 true
        if (activity instanceof IActivity) {
            IActivity iActivity = (IActivity) activity;
            if (iActivity != null && iActivity.isUseEventBus())
                EventBusManager.getInstance().unregister(activity);
        }

    }
}
