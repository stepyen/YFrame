package com.stepyen.yframe.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stepyen.xui.widget.actionbar.TitleBar
import com.stepyen.yframe.R
import com.stepyen.yframe.StateActivity
import com.stepyen.yframe.articlelist.ArticleListActivity
import com.stepyen.yframe.articlelist.ArticleListViewModel
import com.stepyen.yframe.base.AppBaseActivity
import com.stepyen.yframe.databinding.ActivityArticleListBinding
import com.stepyen.yframe.databinding.ActivityMainBinding
import com.stepyen.yframe.testfragment.TestFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppBaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun onInit() {
        mBinding.stateBtn.setOnClickListener {
            startActivity(Intent(this, StateActivity::class.java))
        }
        mBinding.articleListBtn.setOnClickListener {
            startActivity(Intent(this, ArticleListActivity::class.java))
        }
        mBinding.testFragmentBtn.setOnClickListener {
            startActivity(Intent(this, TestFragmentActivity::class.java))
        }
    }

    override fun initTitleBar(): View? {
        return (super.initTitleBar() as TitleBar).apply {
            setLeftImageDrawable(null)
        }
    }


    override fun onLoad() {
    }

    override fun getVMClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }
}