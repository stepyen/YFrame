package com.stepyen.yframe.core.core.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.stepyen.yframe.core.core.viewmodel.BaseViewModel
import com.stepyen.yframe.core.gloading.Gloading

/**
 * date：2022/10/26
 * author：stepyen
 * description：
 *
 *
 * 1、刘海屏
 *
 */

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var mBinding: B
    private var mHolder: Gloading.Holder? = null

    lateinit var vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayoutId() != 0) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId())
            mBinding.lifecycleOwner = this
        }

        vm = ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())[getVMClass()]

        vm.stateLD.observe(this) { state ->
            when (state) {
                BaseViewModel.StateType.LOAD -> {
                    showLoading()
                }

                BaseViewModel.StateType.FAIL -> {
                    showLoadFailed()
                }

                BaseViewModel.StateType.NO_NET -> {
                    showNoNet()
                }

                BaseViewModel.StateType.EMPTY -> {
                    showEmpty()
                }
                BaseViewModel.StateType.SUCCEED -> {
                    showLoadSuccess()
                }
            }

        }

        vm.loadDialogLD.observe(this) { state ->
            when (state) {
                BaseViewModel.LoadDialogType.SHOW -> {
                    showLoadDialog()
                }

                BaseViewModel.LoadDialogType.DISMISS -> {
                    dismissLoadDialog()
                }

            }

        }

        configTitleBar()
        onInit()
    }

    abstract fun getLayoutId(): Int
    abstract fun onInit()
    abstract fun onLoad()
    abstract fun getVMClass(): Class<VM>

    private fun configTitleBar() {
        val contentParent = findViewById<ViewGroup>(android.R.id.content)
        val contentView = contentParent.getChildAt(0) ?: return

        contentParent.removeAllViews()

        val linearLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            orientation = LinearLayout.VERTICAL
        }

        // 添加标题栏
        initTitleBar()?.let {
            linearLayout.addView(it)
        }

        // 添加内容布局
        contentView?.let {
            linearLayout.addView(it)
        }

        contentParent.addView(linearLayout)
    }

    open fun initTitleBar(): View? {
        return null
    }


    open fun showLoadDialog() {

    }

    open fun dismissLoadDialog() {

    }

    open fun useEventBus(): Boolean = false


    open fun initGloadingHolder() {

        if (mHolder == null) {
            mHolder = Gloading.getDefault().wrap(mBinding.root).withRetry(Runnable { onLoad() })
        }

    }

    open fun showLoading() {
        initGloadingHolder()
        mHolder?.showLoading()
    }

    open fun showLoadSuccess() {
        initGloadingHolder()
        mHolder?.showLoadSuccess()
    }

    open fun showLoadFailed() {
        initGloadingHolder()
        mHolder?.showLoadFailed()
    }

    open fun showEmpty() {
        initGloadingHolder()
        mHolder?.showEmpty()
    }

    open fun showNoNet() {
        initGloadingHolder()
        mHolder?.showNoNet()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}