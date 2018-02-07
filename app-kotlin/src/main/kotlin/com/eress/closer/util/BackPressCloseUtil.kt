package com.eress.closer.util

import android.app.Activity

class BackPressCloseUtil(private val activity: Activity) {
    private var backKeyPressedTime: Long = 0

    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            LogUtil.ts(activity.applicationContext, "뒤로 버튼을 한 번 더 터치하면 종료됩니다.")
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish()
        }
    }
}
