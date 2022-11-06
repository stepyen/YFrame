package com.stepyen.yframe.articlelist

import android.util.Log
import com.stepyen.yframe.IRequestCallBack
import com.stepyen.yframe.api.MainApiService
import com.stepyen.yframe.bean.ArticleListWrap
import com.stepyen.yframe.core.core.model.BaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * date：2022/11/4
 * author：stepyen
 * description：
 *
 */
class ArticleListModel : BaseModel<MainApiService>() {


    override fun getApiServiceClass(): Class<MainApiService>  = MainApiService::class.java

    suspend fun requestArticleList(callback: IRequestCallBack<ArticleListWrap>) {

        apiService?.let {
            it.getArticleList()
            //通过一系列操作符处理数据，如map，如果有必要的话
//                .map {
//                    // ...
//                }
            //在Dispatcher.IO上下文中产生订阅数据
            .flowOn(Dispatchers.IO)
            //捕获异常
            .catch { ex ->
                //处理异常
                Log.d("haha","error occurs:$ex")
            }
            //订阅数据
            .collect {it->
                if (it.isSuccess) {
                    callback.onSucceed(it.data)
                }else{
                    callback.onFail(-1,"数据获取失败")
                }

                Log.d("haha","weather info:$it")
            }
        }


    }


    override fun onDestroy() {

    }

}