package com.stepyen.yframe.core.core.app

import android.app.Application
import android.content.Context
import com.stepyen.yframe.core.util.Preconditions

/**
 *
 */
open class BaseApplication : Application(), IApp {

    private var mAppDelegate: IAppLifecycles? = null

    /**
     * 这里会在 [BaseApplication.onCreate] 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param context
     */
    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        if (mAppDelegate == null) mAppDelegate = AppDelegate(context)
        mAppDelegate?.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate?.onCreate(this)
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate?.onTerminate(this)
    }

    /**
     * 将 [IAppComponent] 返回出去, 供其它地方使用, [IAppComponent] 接口中声明的方法所返回的实例, 在 [.getAppComponent] 拿到对象后都可以直接使用
     *
     * @return AppComponent
     */
    override fun getAppComponent(): IAppComponent {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate::class.java.name)
        Preconditions.checkState(mAppDelegate is IApp, "%s must be implements %s", mAppDelegate?.javaClass?.name, IApp::class.java.name)
        return (mAppDelegate as IApp?)!!.appComponent
    }
}