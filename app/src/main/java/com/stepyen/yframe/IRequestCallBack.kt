package com.stepyen.yframe

/**
 * date：2022/11/5
 * author：stepyen
 * description：
 *
 */
interface IRequestCallBack<T> {

    fun onSucceed(data: T)

    fun onFail(code:Int,msg:String)

}
