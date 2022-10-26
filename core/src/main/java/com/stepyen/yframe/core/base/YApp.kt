package com.stepyen.yframe.core.base

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.stepyen.xutil.XUtil
import com.stepyen.yframe.core.gloading.Gloading

/**
 * <pre>
 * ◢◤◢◤
 * 　　　　　◢████◤
 * 　　　⊙███████◤
 * 　●████████◤
 * 　　▼　　～◥███◤
 * 　　▲▃▃◢███　●　　●　　●　　●　　●　　●　　●　　●　　●　       ◢◤
 * 　　　　　　███／█　／█　／█　／█　／█　／█　／█　／█　／█　　　◢◤
 * 　　　　　　████████████████████████████████████████████◤
 *
 *
</pre> *
 */
class YApp : MultiDexApplication(), ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}

    companion object {
        private lateinit var instance: YApp
        fun get(): YApp {
            return instance
        }

        operator fun get(context: Context?): YApp {
            try {
                if (instance == null && context != null) {
                    instance = context.applicationContext as YApp
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        XUtil.init(this)

    }
}