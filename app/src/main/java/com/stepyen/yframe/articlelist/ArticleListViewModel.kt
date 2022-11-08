package com.stepyen.yframe.articlelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stepyen.yframe.IRequestCallBack
import com.stepyen.yframe.base.AppBaseViewModel
import com.stepyen.yframe.bean.ArticleListWrap
import com.stepyen.yframe.core.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

/**
 * date：2022/11/4
 * author：stepyen
 * description：
 *
 */
class ArticleListViewModel : AppBaseViewModel() {
    val model = ArticleListModel()

    val articleListWrapLD: MutableLiveData<ArticleListWrap> = MutableLiveData<ArticleListWrap>()

    fun request() {
      stateLD.value = StateType.LOAD
        viewModelScope.launch {

            model.requestArticleList(object : IRequestCallBack<ArticleListWrap> {
                override fun onSucceed(data: ArticleListWrap) {
                    stateLD.value = StateType.SUCCEED
                    articleListWrapLD.value = data
                }

                override fun onFail(code: Int, msg: String) {
                    stateLD.value = StateType.FAIL
                }
            })

        }
    }
}