package com.eress.closer.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;

import com.eress.closer.service.MainService;
import com.eress.closer.util.ApplicationUtil;
import com.eress.closer.util.LogUtil;

public class CloseActivity extends Activity {

    private static final int REQUEST_OVERLAY_PERMISSION = 1;
    private ApplicationUtil context;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (ApplicationUtil) getApplicationContext();
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
        flag = context.flag;
        if (flag == 0) {
            flag = 1;
            startService(new Intent(context, MainService.class));
            LogUtil.ts(context, "와이파이 제어 서비스 시작!");
        } else {
            flag = 0;
            stopService(new Intent(context, MainService.class));
            LogUtil.ts(context, "와이파이 제어 서비스 중지!");
        }
        context.flag = flag;
        finish();
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
}
