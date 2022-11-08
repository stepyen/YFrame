package com.stepyen.yframe.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.stepyen.xui.widget.actionbar.TitleUtils
import com.stepyen.xui.widget.dialog.MiniLoadingDialog
import com.stepyen.yframe.R
import com.stepyen.yframe.core.core.activity.BaseActivity

/**
 * date：2022/11/8
 * author：stepyen
 * description：
 *
 */

abstract class AppBaseActivity<B : ViewDataBinding, VM : AppBaseViewModel> : BaseActivity<B, VM>() {
    protected var mLoadingDialog: MiniLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .barColor(R.color.public_white)
            .statusBarDarkFont(true)
            .fitsSystemWindows(true)
            .init()
        super.onCreate(savedInstanceState)

    }

    override fun initTitleBar(): View? {
        return TitleUtils.initTitleBarDynamic(this, "${title}") { v -> goBack() }
    }

    open fun goBack() {
        finish()
    }

    override fun showLoadDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = MiniLoadingDialog(this)
        }
        mLoadingDialog?.show()
    }

    override fun dismissLoadDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog?.dismiss()
        }
    }


}