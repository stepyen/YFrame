package com.stepyen.yframe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stepyen.yframe.articlelist.ArticleListActivity
import com.stepyen.yframe.testfragment.TestFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stateBtn.setOnClickListener {
            startActivity(Intent(this,StateActivity::class.java))
        }
        articleListBtn.setOnClickListener {
            startActivity(Intent(this, ArticleListActivity::class.java))
        }
        testFragmentBtn.setOnClickListener {
            startActivity(Intent(this, TestFragmentActivity::class.java))
        }
    }
}