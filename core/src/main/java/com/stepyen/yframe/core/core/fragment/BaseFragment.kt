package com.stepyen.yframe.core.core.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.stepyen.yframe.core.core.viewmodel.BaseViewModel
import com.stepyen.yframe.core.gloading.Gloading

/**
 * date：2022/10/26
 * author：stepyen
 * description：
 *
 */
abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected var mContext: Context? = null

    protected lateinit var mBinding: B
    private var mHolder: Gloading.Holder? = null

    lateinit var vm: VM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val linearLayout = LinearLayout(mContext).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            orientation = LinearLayout.VERTICAL
        }


        initTitleBar()?.let {
            linearLayout.addView(it)
        }

        if (getLayoutId() != 0) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            mBinding.lifecycleOwner = this
            linearLayout.addView(mBinding.root)
        }

        return linearLayout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInit()
    }

    abstract fun getLayoutId(): Int
    abstract fun getTitle(): String?
    abstract fun onInit()
    abstract fun onLoad()
    abstract fun getVMClass(): Class<VM>

    open fun initTitleBar(): View? {
        return null
    }

    open fun showLoadDialog() {

    }

    open fun dismissLoadDialog() {

    }

    protected fun useEventBus(): Boolean = false


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

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }
}

