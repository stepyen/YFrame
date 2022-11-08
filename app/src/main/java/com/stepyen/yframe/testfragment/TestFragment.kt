package com.stepyen.yframe.testfragment

import com.stepyen.yframe.R
import com.stepyen.yframe.base.AppBaseFragment
import com.stepyen.yframe.base.AppBaseViewModel
import com.stepyen.yframe.core.core.fragment.BaseFragment
import com.stepyen.yframe.core.core.viewmodel.BaseViewModel
import com.stepyen.yframe.databinding.FragmentTestBinding

/**
 * date：2022/11/6
 * author：stepyen
 * description：
 *
 */
class TestFragment : AppBaseFragment<FragmentTestBinding, AppBaseViewModel>() {

    companion object{
        fun newInstance(): TestFragment {
            return TestFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_test

    override fun getTitle(): String? {
        return "测试 fragment 页面"
    }


    override fun onInit() {
        onLoad()
    }


    override fun onLoad() {
        showLoading()

        mBinding.testTv.postDelayed(Runnable {
            mBinding.testTv.text = "内容页面"
            showLoadSuccess()
        }, 1000)

    }

    override fun getVMClass(): Class<AppBaseViewModel> = AppBaseViewModel::class.java
}