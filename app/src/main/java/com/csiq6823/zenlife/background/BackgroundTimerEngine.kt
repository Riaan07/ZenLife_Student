package com.csiq6823.zenlife.background

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class GoalMonitorReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val totalMinutes = intent.getIntExtra("TOTAL_SCROLL_MINUTES", 0)
        if (totalMinutes >= 40) {
            Toast.makeText(context, "🚨 GOAL ALERT: You have exceeded 40 minutes of Doom Scrolling today! Take a break!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "📢 REMINDER: Keep up the workspace balance! Stay mindful.", Toast.LENGTH_LONG).show()
        }
    }
}

object BackgroundTimerEngine {
    fun scheduleGoalCheck(context: Context, delaySeconds: Long, currentScrollMinutes: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, GoalMonitorReceiver::class.java).apply {
            putExtra("TOTAL_SCROLL_MINUTES", currentScrollMinutes)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, 101, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = System.currentTimeMillis() + (delaySeconds * 1000)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }
}