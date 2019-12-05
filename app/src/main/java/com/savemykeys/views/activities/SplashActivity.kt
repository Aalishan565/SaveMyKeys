package com.savemykeys.views.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.savemykeys.R

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"
    private val SPLASH_TIME_OUT: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, LoginSignUpActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}
