package com.jef4tech.interviewnotifier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateHome()
    }

    private fun navigateHome() {
        val homeIntent = Intent(this, Home::class.java)
        startActivity(homeIntent)
        finish()
    }
}