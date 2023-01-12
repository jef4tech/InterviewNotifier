package com.jef4tech.interviewnotifier.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.jef4tech.interviewnotifier.R

/**
 * @author jeffin
 * @date 02/01/23
 */
object Extension {

    // Notification ID.
    private val NOTIFICATION_ID = 0
    private val REQUEST_CODE = 0
    private val FLAGS = 0

    fun isValidInput(input: String): Boolean {
        return !input.isEmpty()
    }
    fun showToast(message: String,context: Context) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }


}