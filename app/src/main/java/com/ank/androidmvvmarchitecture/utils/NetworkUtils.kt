package com.ank.androidmvvmarchitecture.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build



object NetworkUtils {
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    /** * Retrieves the net.hostname system property * @param defValue the value to be returned if the hostname could * not be resolved  */
    fun getHostName(defValue: String): String {
        try {
            val getString = Build::class.java!!.getDeclaredMethod("getString", String::class.java)
            getString.setAccessible(true)
            return getString.invoke(null, "net.hostname").toString()
        } catch (ex: Exception) {
            return defValue
        }
    }
}