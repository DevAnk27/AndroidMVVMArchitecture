package com.ank.androidmvvmarchitecture.utils

import android.text.TextUtils
import android.util.Log
import com.ank.androidmvvmarchitecture.BuildConfig

/**
 * Created by CIS Dev 816 on 9/2/18.
 */
object LogUtils {

    //private static boolean Debugging = true;
    private var Debugging = !BuildConfig.BUILD_TYPE.equals("release", true)

    /**
     * Sets the debug mode.
     */
    fun setDebugging(debugging: Boolean) {
        Debugging = debugging
    }

    /**
     * Logs this message if log level ERROR is enabled.
     */
    fun e(tag: String, vararg msg: Any) {
        if (Debugging or Log.isLoggable(tag, Log.ERROR)) {
            Log.e(tag, TextUtils.join(" ", msg))
        }
    }

    /**
     * Logs this message and [Throwable] if log level ERROR is enabled.
     */
    fun e(tag: String, tr: Throwable, vararg msg: Any) {
        if (Debugging or Log.isLoggable(tag, Log.ERROR)) {
            Log.e(tag, TextUtils.join(" ", msg), tr)
        }
    }


    /**
     * Logs this message if log level WARN is enabled.
     */
    fun w(tag: String, vararg msg: Any) {
        if (Debugging or Log.isLoggable(tag, Log.WARN)) {
            Log.d(tag, TextUtils.join(" ", msg))
        }
    }

    /**
     * Logs this message and [Throwable] if log level WARN is enabled.
     */
    fun w(tag: String, tr: Throwable, vararg msg: Any) {
        if (Debugging or Log.isLoggable(tag, Log.WARN)) {
            Log.d(tag, TextUtils.join(" ", msg), tr)
        }
    }


    /**
     * Logs this message if log level DEBUG is enabled.
     */
    fun d(tag: String, vararg msg: Any) {
        if (Debugging or Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, TextUtils.join(" ", msg))
        }
    }

    /**
     * Logs this message and [Throwable] if log level DEBUG is enabled.
     */
    fun d(tag: String, tr: Throwable, vararg msg: Any) {
        if (Debugging or Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, TextUtils.join(" ", msg), tr)
        }
    }
}