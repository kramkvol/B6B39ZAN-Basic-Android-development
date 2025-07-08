package cz.cvut.fel.zan_kramkvol.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

fun scheduleSimpleReminder(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val triggerTime = System.currentTimeMillis() + 5_000L

    alarmManager.set(
        AlarmManager.RTC_WAKEUP,
        triggerTime,
        pendingIntent
    )
}
