package com.stepyen.yframe.articlelist

import com.stepyen.yframe.R
import com.stepyen.yframe.core.core.activity.BaseActivity
import com.stepyen.yframe.databinding.ActivityArticleListBinding

/**
 * date：2022/11/4
 * author：stepyen
 * description：
 *
 */
class ArticleListActivity : BaseActivity<ActivityArticleListBinding, ArticleListViewModel>() {
    override fun getLayoutId() = R.layout.activity_article_list

    override fun onInit() {

        vm.articleListWrapLD.observe(this) {
            mBinding?.testTv?.text = "文章总数：${it.total}"
        }


        onLoad()
    }


    override fun onLoad() {
        vm.request()
    }

    override fun getVMClass(): Class<ArticleListViewModel> {
        return ArticleListViewModel::class.java
    }

}