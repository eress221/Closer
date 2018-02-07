package com.eress.closer.util;

import android.app.Activity;

public class BackPressCloseUtil {
    private long backKeyPressedTime = 0;
    private Activity activity;

    public BackPressCloseUtil(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            LogUtil.ts(activity.getApplicationContext(), "뒤로 버튼을 한 번 더 터치하면 종료됩니다.");
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
        }
    }
}
