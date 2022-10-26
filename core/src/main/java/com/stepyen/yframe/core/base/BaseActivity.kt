package com.stepyen.yframe.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * date：2022/10/26
 * author：stepyen
 * description：
 *
 *
 * 1、刘海屏
 *
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: B

    //    lateinit var userViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this

//        userViewModel =
//            ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory()).get()

        initData()
        initView()
    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
    abstract fun initView()

    fun useEventBus(): Boolean = false


}