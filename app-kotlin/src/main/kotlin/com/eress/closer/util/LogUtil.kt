package com.eress.closer.util

import android.content.Context
import android.util.Log
import android.widget.Toast

object LogUtil {
    fun m(message: String) {
        Log.d("closer", "" + message)
    }

    fun ts(context: Context, message: String) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show()
    }

    fun tl(context: Context, message: String) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show()
    }
}
