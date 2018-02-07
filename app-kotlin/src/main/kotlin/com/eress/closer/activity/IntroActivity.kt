package com.eress.closer.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window

import com.eress.closer.R
import com.eress.closer.util.ApplicationUtil
import com.eress.closer.util.LogUtil

class IntroActivity : Activity() {

    private var context: ApplicationUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        context = applicationContext as ApplicationUtil
        loadMain()
    }

    private fun loadMain() {
        if (context!!.intro === 0) {
            context!!.intro = 1
            val runnable = Runnable {
                Intent(context, MainActivity::class.java).let {
                    startActivity(it)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }
            Handler().postDelayed(runnable, 3000)
            LogUtil.tl(context!!, "로딩 중입니다.")
        }
    }

    override fun onBackPressed() {
        LogUtil.ts(context!!, "현재 상태에서는 뒤로가기가 되지 않습니다.")
        LogUtil.m("현재 상태에서는 뒤로가기가 되지 않습니다.")
    }
}
