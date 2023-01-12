package com.jef4tech.interviewnotifier.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import com.jef4tech.interviewnotifier.AlarmReceiver
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * @author jeffin
 * @date 12/01/23
 */
class AndroidAlarmScheduler(
  private val context: Context
) {
  @RequiresApi(VERSION_CODES.O) val time = LocalDateTime.now()
    .plusSeconds(40)
  @RequiresApi(VERSION_CODES.M)
  private val alarmManager = context.getSystemService(AlarmManager::class.java)
  @RequiresApi(VERSION_CODES.O) fun schedule() {
    val intent = Intent(context, AlarmReceiver::class.java).apply {
      putExtra("EXTRA_MESSAGE", "item.message")
    }
    alarmManager.setExactAndAllowWhileIdle(
      AlarmManager.RTC_WAKEUP,
      time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
      PendingIntent.getBroadcast(
        context,
        time.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
      )
    )
  }
}