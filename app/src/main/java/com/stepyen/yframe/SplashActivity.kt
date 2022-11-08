package com.stepyen.yframe

import android.content.Intent
import com.stepyen.xui.activity.BaseSplashActivity
import com.stepyen.yframe.main.MainActivity

/**
 * date：2022/10/28
 * author：stepyen
 * description：
 *
 */
class SplashActivity: BaseSplashActivity() {
    override fun onCreateActivity() {
        startSplash(true)
    }

    override fun onSplashFinished() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()


    }



}