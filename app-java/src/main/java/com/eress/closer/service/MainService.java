package com.eress.closer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PixelFormat;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.eress.closer.R;
import com.eress.closer.util.ApplicationUtil;
import com.eress.closer.util.LogUtil;

public class MainService extends Service {

    private long keyPressedTime = 0;
    private ApplicationUtil context;
    private WindowManager.LayoutParams params;
    private WindowManager window;
    private WifiManager wifi;
    private ImageButton ibNet;
    private int flag = 0;

    private int action = 0;
    private float s_x = 0.0f;
    private float s_y = 0.0f;
    private int p_x = 0;
    private int p_y = 0;
    private int m_x = -1;
    private int m_y = -1;

    public MainService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = (ApplicationUtil) getApplicationContext();
        window = (WindowManager) getSystemService(WINDOW_SERVICE);
        wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        initialize();
    }

    private void initialize() {
        if (wifi.isWifiEnabled()) {
            flag = 1;
        } else {
            flag = 0;
        }

        ibNet = new ImageButton(this);
        if (flag == 0) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0.0f);
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            ibNet.setColorFilter(cf);
        } else {
            ibNet.clearColorFilter();
            ibNet.invalidate();
        }
        ibNet.setTag(flag);
        ibNet.setImageResource(R.drawable.ic_wifi_40dp);
        ibNet.setBackgroundResource(R.drawable.btn_round);

        params = new WindowManager.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.icon_size),
                getResources().getDimensionPixelSize(R.dimen.icon_size),
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.y = getResources().getDimensionPixelSize(R.dimen.icon_size);
        window.addView(ibNet, params);

        ibNet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (System.currentTimeMillis() > keyPressedTime + 2000) {
                            keyPressedTime = System.currentTimeMillis();
                            action = 1;
                            if (m_x == -1) {
                                setMaxPosition();
                            }
                            s_x = event.getRawX();
                            s_y = event.getRawY();
                            p_x = params.x;
                            p_y = params.y;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) (event.getRawX() - s_x);
                        int y = (int) (event.getRawY() - s_y);
                        int m_a = getResources().getDimensionPixelSize(R.dimen.move_area);

                        if (Math.abs(x) > m_a && Math.abs(y) > m_a) {
                            action = 0;
                            params.x = p_x + x;
                            params.y = p_y + y;
                            optimizePosition();
                            if (window != null && ibNet != null) {
                                window.updateViewLayout(ibNet, params);
                                if (event.getRawX() > getResources().getDimensionPixelSize(R.dimen.delete_x)) {
                                    context.flag = 0;
                                    context.onTerminate();
                                    stopService(new Intent(context, MainService.class));
                                    LogUtil.ts(context, "와이파이 제어 서비스 중지!");
                                }
                            }
                        }
                        break;
                    case  MotionEvent.ACTION_UP:
                        if (action == 1) {
                            action = 0;
                            int tag = (int) v.getTag();
                            if (tag == 0) {
                                flag = 1;
                                ibNet.clearColorFilter();
                                ibNet.invalidate();
                                wifi.setWifiEnabled(true);
                                LogUtil.ts(context, "와이파이 켜짐!");
                            } else {
                                flag = 0;
                                ColorMatrix matrix = new ColorMatrix();
                                matrix.setSaturation(0.0f);
                                ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
                                ibNet.setColorFilter(cf);
                                wifi.setWifiEnabled(false);
                                LogUtil.ts(context, "와이파이 꺼짐!");
                            }
                            ibNet.setTag(flag);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void setMaxPosition() {
        DisplayMetrics matrix = new DisplayMetrics();
        window.getDefaultDisplay().getMetrics(matrix);
        m_x = matrix.widthPixels - ibNet.getWidth();
        m_y = matrix.heightPixels - ibNet.getHeight();
    }

    private void optimizePosition() {
        if (params.x > m_x) {
            params.x = m_x;
        }
        if (params.y > m_y) {
            params.y = m_y;
        }
        if (params.x < 0) {
            params.x = 0;
        }
        if (params.y < 0) {
            params.y = 0;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setMaxPosition();
        optimizePosition();
        params.y = getResources().getDimensionPixelSize(R.dimen.icon_size);
    }

    @Override
    public void onDestroy() {
        if (window != null && ibNet != null) {
            window.removeView(ibNet);
            window = null;
            ibNet = null;
        }
        super.onDestroy();
    }
}
