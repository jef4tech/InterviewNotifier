package com.jef4tech.interviewnotifier

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import androidx.core.app.NotificationCompat

/**
 * @author jeffin
 * @date 12/01/23
 */
class AlarmReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
    println("Alarm triggered: $message")
    val myToast = Toast.makeText(context,"toast message with gravity",Toast.LENGTH_LONG)
    myToast.setGravity(Gravity.LEFT,200,200)
    myToast.show()
  }
}