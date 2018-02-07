package com.eress.closer.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogUtil {
    public static void m(String message) {
        Log.d("closer", ""+ message);
    }

    public static void ts(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

    public static void tl(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}
