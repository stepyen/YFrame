package com.stepyen.yframe.base

import android.view.View
import androidx.databinding.ViewDataBinding
import com.stepyen.xui.widget.actionbar.TitleUtils
import com.stepyen.xui.widget.dialog.MiniLoadingDialog
import com.stepyen.yframe.core.core.fragment.BaseFragment

/**
 * date：2022/11/8
 * author：stepyen
 * description：
 *
 */
abstract class AppBaseFragment<B : ViewDataBinding, VM: AppBaseViewModel>:BaseFragment<B,VM>() {

    protected var mLoadingDialog: MiniLoadingDialog? = null

    override fun initTitleBar(): View? {
        return TitleUtils.initTitleBarDynamic(mContext, "${getTitle()}") { v -> goBack() }
    }

    open fun goBack() {
        activity?.finish()
    }

    override fun showLoadDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = MiniLoadingDialog(mContext)
        }
        mLoadingDialog?.show()
    }

    override fun dismissLoadDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog?.dismiss()
        }
    }

}