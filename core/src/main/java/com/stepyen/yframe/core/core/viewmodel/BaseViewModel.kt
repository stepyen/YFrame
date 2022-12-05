package com.stepyen.yframe.core.core.viewmodel

import androidx.annotation.IntDef
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * date：2022/11/5
 * author：stepyen
 * description：
 *
 */
open class BaseViewModel : ViewModel() {

    /**
     * 视图状态
     */
    @IntDef(
        StateType.LOAD,
        StateType.FAIL,
        StateType.NO_NET,
        StateType.EMPTY,
        StateType.SUCCEED,
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class StateType {
        companion object {
            const val LOAD = 0
            const val FAIL = 1
            const val NO_NET = 2
            const val EMPTY = 3
            const val SUCCEED = 4
        }
    }

    /**
     * 加载对话框状态
     */
    @IntDef(
        LoadDialogType.SHOW,
        LoadDialogType.DISMISS
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class LoadDialogType {
        companion object {
            const val SHOW = 0
            const val DISMISS = 1
        }
    }


    val stateLD: MutableLiveData<Int> = object : MutableLiveData<Int>() {
        override fun postValue(@StateType value: Int?) {
            super.postValue(value)
        }

        override fun setValue(@StateType value: Int?) {
            super.setValue(value)
        }
    }

    val loadDialogLD: MutableLiveData<Int> = object : MutableLiveData<Int>() {
        override fun postValue(@LoadDialogType value: Int?) {
            super.postValue(value)
        }

        override fun setValue(@LoadDialogType value: Int?) {
            super.setValue(value)
        }
    }


}