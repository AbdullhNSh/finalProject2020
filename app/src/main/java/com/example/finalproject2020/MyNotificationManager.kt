package com.example.finalproject2020

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class MyNotificationManager(var context: Context) {
   /* val NOTIFICATION_ID = 1000
    val CHANNEL_ID = "2000"
    fun showNotification(id: Int, title: String, message: String, intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val nBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        var notification = nBuilder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT).build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "first channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setShowBadge(true)
            channel.enableVibration(true)
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(id, notification)
    }

    fun getNotificationId(): Int {
        return NOTIFICATION_ID
    }

    fun getNotification(context: Context, intent: Intent): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val nBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            var notification = nBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle("Forground Service")
                .setContentText("Foreground service is running")
                .setAutoCancel(true)
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT).build()
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID,
                "first channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setShowBadge(true)
            channel.enableVibration(true)
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
            return notification
        } else {
            val pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val nBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            var notification = nBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle("Forground Service")
                .setContentText("Forground Service is running")
                .setAutoCancel(true)
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT).build()
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            return notification
        }

    }*/
}