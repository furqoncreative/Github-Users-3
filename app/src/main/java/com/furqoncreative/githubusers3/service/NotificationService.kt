package com.furqoncreative.githubusers3.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.furqoncreative.githubusers3.R
import com.furqoncreative.githubusers3.ui.main.MainActivity
import com.furqoncreative.githubusers3.utils.ID_NOTIF

class NotificationService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val title = context.resources.getString(R.string.daily_reminder_title)
            val message = context.resources.getString(R.string.daily_reminder_message)
            showAlarmNotification(context, title, message, ID_NOTIF)
        }
    }


    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int,
    ) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mChannel: NotificationChannel

        //handle service always active after reboot
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isInteractive) {
            val wl = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
                "AppName:tag"
            )
            wl.acquire(10000)
            val wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AppName:tag")
            wl_cpu.acquire(10000)
        }

        //result intent
        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.putExtra("MenuFragment", "MenuFragment")
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //notification sound
        val alarmSound = Uri.parse(
            "android.resource://"
                    + context.packageName + "/" + R.raw.short_message_tone
        )

        //set vibrate
        val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)

        //notification builder
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(resultPendingIntent)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLights(NotificationCompat.DEFAULT_LIGHTS, 100, 100)
            .setSound(alarmSound)
            .setAutoCancel(true)


        //notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            mChannel.lightColor = Color.GRAY
            mChannel.enableLights(true)
            mChannel.description = " CHANNEL_SIREN_DESCRIPTION"
            val audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            mChannel.setSound(alarmSound, audioAttributes)
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(mChannel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)

    }
}