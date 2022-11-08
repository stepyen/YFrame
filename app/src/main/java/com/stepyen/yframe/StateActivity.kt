package com.stepyen.yframe

import com.stepyen.yframe.base.AppBaseActivity
import com.stepyen.yframe.base.AppBaseViewModel
import com.stepyen.yframe.core.core.activity.BaseActivity
import com.stepyen.yframe.core.core.viewmodel.BaseViewModel
import com.stepyen.yframe.databinding.ActivityStateBinding

/**
 * date：2022/10/28
 * author：stepyen
 * description：
 *
 */
class StateActivity : AppBaseActivity<ActivityStateBinding, AppBaseViewModel>() {
    override fun getLayoutId() = R.layout.activity_state

    override fun onInit() {
        mBinding.requestBtn.setOnClickListener {
            showLoadDialog()
            mBinding.requestBtn.postDelayed(Runnable {
                dismissLoadDialog()
            },1000)
        }

        onLoad()
    }


    override fun onLoad() {
        showLoading()

        mBinding.succeedTv.postDelayed(Runnable {
            showLoadSuccess()
        }, 1000)

    }

    override fun getVMClass(): Class<AppBaseViewModel> {
        return AppBaseViewModel::class.java
    }


}