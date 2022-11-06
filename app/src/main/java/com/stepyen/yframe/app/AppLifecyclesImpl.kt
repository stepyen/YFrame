package com.stepyen.yframe.app

import android.app.Application
import android.content.Context
import com.stepyen.yframe.GloadingAdapter
import com.stepyen.yframe.core.core.app.IAppLifecycles
import com.stepyen.yframe.core.gloading.Gloading

/**
 * date：2022/11/4
 * author：stepyen
 * description：
 *
 */
class AppLifecyclesImpl:IAppLifecycles {
    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
        Gloading.initDefault(GloadingAdapter())
    }

    override fun onTerminate(application: Application) {
    }
}