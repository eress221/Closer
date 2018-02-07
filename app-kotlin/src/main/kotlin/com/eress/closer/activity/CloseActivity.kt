package com.eress.closer.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings

import com.eress.closer.service.MainService
import com.eress.closer.util.ApplicationUtil
import com.eress.closer.util.LogUtil

class CloseActivity : Activity() {

    private val REQUEST_OVERLAY_PERMISSION = 1
    private var context: ApplicationUtil? = null
    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext as ApplicationUtil
        initialize()
    }

    private fun initialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context!!)) {
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName)).let {
                startActivityForResult(it, REQUEST_OVERLAY_PERMISSION)
            }
        } else {
            startOverlay()
        }
    }

    private fun startOverlay() {
        flag = context!!.flag
        if (flag == 0) {
            flag = 1
            Intent(context, MainService::class.java).let {
                startService(it)
                LogUtil.ts(context!!, "와이파이 제어 서비스 시작!")
            }
        } else {
            flag = 0
            Intent(context, MainService::class.java).let {
                stopService(it)
                LogUtil.ts(context!!, "와이파이 제어 서비스 중지!")
            }
        }
        context!!.flag = flag
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context!!)) {
                LogUtil.ts(context!!, "권한이 부여되지 않음!")
                finish()
            } else {
                startOverlay()
            }
        }
    }
}
