package com.stepyen.yframe.testfragment

import android.view.View
import com.stepyen.yframe.R
import com.stepyen.yframe.core.core.activity.BaseActivity
import com.stepyen.yframe.core.core.viewmodel.BaseViewModel
import com.stepyen.yframe.databinding.ActivityTestFragmentBinding

/**
 * date：2022/11/6
 * author：stepyen
 * description：
 *
 */
class TestFragmentActivity : BaseActivity<ActivityTestFragmentBinding, BaseViewModel>() {

    override fun initTitleBar(): View? = null

    override fun getLayoutId(): Int  = R.layout.activity_test_fragment

    override fun onInit() {

        supportFragmentManager
            .beginTransaction()
            .replace(mBinding.contentFL.id, TestFragment.newInstance())
            .commit()

    }

    override fun onLoad() {
    }

    override fun getVMClass(): Class<BaseViewModel> = BaseViewModel::class.java
}