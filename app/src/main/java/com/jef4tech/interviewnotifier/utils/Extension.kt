package com.jef4tech.interviewnotifier.utils

import android.content.Context
import android.widget.Toast

/**
 * @author jeffin
 * @date 02/01/23
 */
object Extension {
    fun isValidInput(input: String): Boolean {
        return !input.isEmpty()
    }
    fun showToast(message: String,context: Context) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}