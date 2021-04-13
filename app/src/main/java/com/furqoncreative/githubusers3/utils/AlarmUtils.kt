package com.furqoncreative.githubusers3.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.furqoncreative.githubusers3.R
import com.furqoncreative.githubusers3.service.NotificationService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun setRepeatingAlarm(context: Context, time: String) {
    if (isDateInvalid(time, TIME_FORMAT)) return

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationService::class.java)
    intent.putExtra(EXTRA_TYPE, TYPE_REPEATING)

    val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
    calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
    calendar.set(Calendar.SECOND, 0)

    val pendingIntent =
        PendingIntent.getBroadcast(context, ID_NOTIF, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
    Toast.makeText(context, context.resources.getString(R.string.daily_reminder_actived), Toast.LENGTH_SHORT).show()
}

fun cancelAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationService::class.java)
    val pendingIntent =
        PendingIntent.getBroadcast(context, ID_NOTIF, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    pendingIntent.cancel()
    alarmManager.cancel(pendingIntent)

    Toast.makeText(context, context.resources.getString(R.string.daily_reminder_deacatived), Toast.LENGTH_SHORT).show()
}

private fun isDateInvalid(date: String, format: String): Boolean {
    return try {
        val df = SimpleDateFormat(format, Locale.getDefault())
        df.isLenient = false
        df.parse(date)
        false
    } catch (e: ParseException) {
        true
    }
}
