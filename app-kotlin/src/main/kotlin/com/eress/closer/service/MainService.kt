package com.eress.closer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.PixelFormat
import android.net.wifi.WifiManager
import android.os.IBinder
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageButton
import com.eress.closer.R

import com.eress.closer.util.ApplicationUtil
import com.eress.closer.util.LogUtil

class MainService : Service() {

    private var keyPressedTime: Long = 0
    private var context: ApplicationUtil? = null
    private var params: WindowManager.LayoutParams? = null
    private var window: WindowManager? = null
    private var wifi: WifiManager? = null
    private var ibNet: ImageButton? = null
    private var flag = 0

    private var action = 0
    private var s_x = 0.0f
    private var s_y = 0.0f
    private var p_x = 0
    private var p_y = 0
    private var m_x = -1
    private var m_y = -1

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext as ApplicationUtil
        window = getSystemService(WINDOW_SERVICE) as WindowManager
        wifi = context!!.getSystemService(Context.WIFI_SERVICE) as WifiManager
        initialize()
    }

    private fun initialize() {
        flag = if (wifi!!.isWifiEnabled) {
            1
        } else {
            0
        }

        ibNet = ImageButton(this)
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
        ibNet!!.setImageResource(R.drawable.ic_wifi_40dp)
        ibNet!!.setBackgroundResource(R.drawable.btn_round)

        params = WindowManager.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.icon_size),
                resources.getDimensionPixelSize(R.dimen.icon_size),
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)
        params!!.gravity = Gravity.LEFT or Gravity.TOP
        params!!.y = resources.getDimensionPixelSize(R.dimen.icon_size)
        window!!.addView(ibNet, params)

        ibNet!!.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> if (System.currentTimeMillis() > keyPressedTime + 2000) {
                    keyPressedTime = System.currentTimeMillis()
                    action = 1
                    if (m_x == -1) {
                        setMaxPosition()
                    }
                    s_x = event.rawX
                    s_y = event.rawY
                    p_x = params!!.x
                    p_y = params!!.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val x = (event.rawX - s_x).toInt()
                    val y = (event.rawY - s_y).toInt()
                    val m_a = resources.getDimensionPixelSize(R.dimen.move_area)

                    if (Math.abs(x) > m_a && Math.abs(y) > m_a) {
                        action = 0
                        params!!.x = p_x + x
                        params!!.y = p_y + y
                        optimizePosition()
                        if (window != null && ibNet != null) {
                            window!!.updateViewLayout(ibNet, params)
                            if (event.rawX > resources.getDimensionPixelSize(R.dimen.delete_x)) {
                                context!!.flag = 0
                                context!!.onTerminate()
                                Intent(context, MainService::class.java).let {
                                    stopService(it)
                                    LogUtil.ts(context!!, "와이파이 제어 서비스 중지!")
                                }
                            }
                        }
                    }
                }
                MotionEvent.ACTION_UP -> if (action == 1) {
                    action = 0
                    val tag = v.tag
                    if (tag == 0) {
                        flag = 1
                        ibNet!!.clearColorFilter()
                        ibNet!!.invalidate()
                        wifi!!.isWifiEnabled = true
                        LogUtil.ts(context!!, "와이파이 켜짐!")
                    } else {
                        flag = 0
                        val matrix = ColorMatrix()
                        matrix.setSaturation(0.0f)
                        val cf = ColorMatrixColorFilter(matrix)
                        ibNet!!.colorFilter = cf
                        wifi!!.isWifiEnabled = false
                        LogUtil.ts(context!!, "와이파이 꺼짐!")
                    }
                    ibNet!!.tag = flag
                }
            }
            true
        }
    }

    private fun setMaxPosition() {
        val matrix = DisplayMetrics()
        window!!.defaultDisplay.getMetrics(matrix)
        m_x = matrix.widthPixels - ibNet!!.width
        m_y = matrix.heightPixels - ibNet!!.height
    }

    private fun optimizePosition() {
        if (params!!.x > m_x) {
            params!!.x = m_x
        }
        if (params!!.y > m_y) {
            params!!.y = m_y
        }
        if (params!!.x < 0) {
            params!!.x = 0
        }
        if (params!!.y < 0) {
            params!!.y = 0
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        setMaxPosition()
        optimizePosition()
        params!!.y = resources.getDimensionPixelSize(R.dimen.icon_size)
    }

    override fun onDestroy() {
        if (window != null && ibNet != null) {
            window!!.removeView(ibNet)
            window = null
            ibNet = null
        }
        super.onDestroy()
    }
}
