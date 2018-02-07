package com.eress.closer.activity

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageButton

import com.eress.closer.R
import com.eress.closer.service.MainService
import com.eress.closer.util.ApplicationUtil
import com.eress.closer.util.BackPressCloseUtil
import com.eress.closer.util.LogUtil

class MainActivity : AppCompatActivity() {

    private val REQUEST_OVERLAY_PERMISSION = 1
    private var keyPressedTime: Long = 0
    private var context: ApplicationUtil? = null
    private var backPressClose: BackPressCloseUtil? = null
    private var ibNet: ImageButton? = null
    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = applicationContext as ApplicationUtil
        backPressClose = BackPressCloseUtil(this)
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
        ibNet = findViewById(R.id.ib_net) as ImageButton
        flag = context!!.flag
        if (flag == 0) {
            val matrix = ColorMatrix()
            matrix.setSaturation(0.0f)
            val cf = ColorMatrixColorFilter(matrix)
            ibNet!!.colorFilter = cf
        } else {
            ibNet!!.clearColorFilter()
            ibNet!!.invalidate()
        }
        ibNet!!.tag = flag
        ibNet!!.visibility = View.VISIBLE
        ibNet!!.setOnClickListener { v ->
            if (System.currentTimeMillis() > keyPressedTime + 2000) {
                keyPressedTime = System.currentTimeMillis()
                val tag = v.tag
                if (tag == 0) {
                    flag = 1
                    ibNet!!.clearColorFilter()
                    ibNet!!.invalidate()
                    Intent(context, MainService::class.java).let {
                        startService(it)
                        LogUtil.ts(context!!, "와이파이 제어 서비스 시작!")
                    }
                } else {
                    flag = 0
                    val matrix = ColorMatrix()
                    matrix.setSaturation(0.0f)
                    val cf = ColorMatrixColorFilter(matrix)
                    ibNet!!.colorFilter = cf
                    Intent(context, MainService::class.java).let {
                        stopService(it)
                        LogUtil.ts(context!!, "와이파이 제어 서비스 중지!")
                    }
                }
                context!!.flag = flag
                ibNet!!.tag = flag
            }
        }
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

    override fun onBackPressed() {
        backPressClose!!.onBackPressed()
    }

    override fun onDestroy() {
        context!!.intro = 0
        super.onDestroy()
    }
}
