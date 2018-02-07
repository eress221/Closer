package com.eress.closer.activity;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.eress.closer.R;
import com.eress.closer.service.MainService;
import com.eress.closer.util.ApplicationUtil;
import com.eress.closer.util.BackPressCloseUtil;
import com.eress.closer.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_OVERLAY_PERMISSION = 1;
    private long keyPressedTime = 0;
    private ApplicationUtil context;
    private BackPressCloseUtil backPressClose;
    private ImageButton ibNet;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = (ApplicationUtil) getApplicationContext();
        backPressClose = new BackPressCloseUtil(this);
        initialize();
    }

    private void initialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())),
                    REQUEST_OVERLAY_PERMISSION);
        } else {
            startOverlay();
        }
    }

    private void startOverlay() {
        ibNet = (ImageButton) findViewById(R.id.ib_net);
        flag = context.flag;
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
        ibNet.setVisibility(View.VISIBLE);
        ibNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() > keyPressedTime + 2000) {
                    keyPressedTime = System.currentTimeMillis();
                    int tag = (int) v.getTag();
                    if (tag == 0) {
                        flag = 1;
                        ibNet.clearColorFilter();
                        ibNet.invalidate();
                        startService(new Intent(context, MainService.class));
                        LogUtil.ts(context, "와이파이 제어 서비스 시작!");
                    } else {
                        flag = 0;
                        ColorMatrix matrix = new ColorMatrix();
                        matrix.setSaturation(0.0f);
                        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
                        ibNet.setColorFilter(cf);
                        stopService(new Intent(context, MainService.class));
                        LogUtil.ts(context, "와이파이 제어 서비스 중지!");
                    }
                    context.flag = flag;
                    ibNet.setTag(flag);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                LogUtil.ts(context, "권한이 부여되지 않음!");
                finish();
            } else {
                startOverlay();
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        backPressClose.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        context.intro = 0;
        super.onDestroy();
    }
}
