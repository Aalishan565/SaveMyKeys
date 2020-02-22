package com.savemykeys.application

import android.app.Application
import android.util.Log

class SaveMyKeysApplication : Application() {

    private val TAG = "SaveMyKeysApplication"

    override fun onCreate() {
        Log.d(TAG, "onCreate()")
        super.onCreate()
    }
}