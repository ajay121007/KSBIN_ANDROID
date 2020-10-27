package com.example.ks

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.ks.activities.loginsignup.LoginSignUpActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by skycap.
 */
 val CHANNEL_NAME: CharSequence?="KS"
 val CHANNEL_ID: String="com.amit.ks"
 class MyFirebaseInstanceIDService : FirebaseMessagingService() {


    override fun onNewToken(p0: String) {
        Log.i(this.javaClass.simpleName, "$p0: ")
        super.onNewToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.i(this.javaClass.simpleName, "message $p0: ")
        sendNotification(p0.data["title"],p0.data["body"],this)
    }

}
 fun sendNotification(title:String?,messageBody: String?,context: Context) {
    val intent = Intent(context, LoginSignUpActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.putExtra(Intent.EXTRA_TEXT, messageBody)
    val pendingIntent = PendingIntent
        .getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
    notificationBuilder.setSmallIcon(R.drawable.app_icom)
    notificationBuilder.setContentTitle(title)
    notificationBuilder.setContentText(messageBody)
    notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
    notificationBuilder.setAutoCancel(true)
    notificationBuilder.setSound(defaultSoundUri)
    notificationBuilder.setContentIntent(pendingIntent)
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        notificationManager.createNotificationChannel(channel)
    }
    notificationManager.notify(0, notificationBuilder.build())
}