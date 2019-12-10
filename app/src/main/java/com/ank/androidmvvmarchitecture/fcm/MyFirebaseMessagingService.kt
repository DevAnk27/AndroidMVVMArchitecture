package com.ank.androidmvvmarchitecture.fcm/*
package com.ank.androidmvvmarchitecture.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import android.util.Log
import androidmvvm.R
import androidmvvm.ui.splash.SplashActivity
import androidmvvm.utils.LogUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


*/
/**
 * Created by CIS Dev 816 on 22/8/18.
 *//*

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFMService"
    val NOTIFICATION_CHANNEL_ID = "10002"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        try {
            val map = remoteMessage?.data
            Log.e("Notification", "notif:" + remoteMessage?.notification + "\ndata:" + remoteMessage!!.data["gcm_msg"].toString())
            createNotification(remoteMessage.data["body"].toString(), remoteMessage?.data["title"].toString())
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }

    */
/**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     *//*

    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: " + token!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token)
    }

    private fun sendNotification(body: String, title: String*/
/*RemoteMessage.Notification notification*//*
) {
        try {
            val bundle = Bundle()
            val intent = Intent(this, SplashActivity::class.java)

            intent.putExtra("BUNDLE", bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val pendingIntent = PendingIntent.getActivity(this, 0 */
/* Request code *//*
, intent,
                    PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(0 */
/* ID of notification *//*
, notificationBuilder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createNotification(body: String, title: String) {
        val intent = Intent(this, SplashActivity::class.java)
        val bundle = Bundle()
        intent.putExtra("BUNDLE", bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pendingIntent = PendingIntent.getActivity(this, 0 */
/* Request code *//*
, intent, PendingIntent.FLAG_ONE_SHOT)

        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        val mBuilder = NotificationCompat.Builder(applicationContext)
        mBuilder.setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(pendingIntent)
                .setLargeIcon(icon)
                .setColor(resources.getColor(R.color.colorPrimary))
                .setLights(resources.getColor(R.color.colorPrimary), 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_notification)

        val mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, getString(R.string.app_name), importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = resources.getColor(R.color.colorPrimary)
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            //assert(mNotificationManager != null)
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        val rand = Random()
        val id = rand.nextInt(1000)
        mNotificationManager.notify(id */
/* Request Code *//*
, mBuilder.build())
    }
}*/
