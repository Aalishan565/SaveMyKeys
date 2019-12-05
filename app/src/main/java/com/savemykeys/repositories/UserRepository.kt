package com.savemykeys.repositories

import android.content.Context
import android.util.Log
import com.savemykeys.utils.AppSharedPreference

class UserRepository(val context: Context) {
    private val TAG = "UserRepository"
    var appSharedPreference: AppSharedPreference = AppSharedPreference.instance
    fun doLogin(pin: String): Boolean {
        Log.d(TAG, "doLogin")
        return appSharedPreference.getBooleanPref(context, pin)
    }

    fun doSignUp(pin: String) {
        Log.d(TAG, "doSignUp")
        appSharedPreference.saveBooleanPref(context, pin, true)
        appSharedPreference.clearAllPrefs(context)
        appSharedPreference.saveBooleanPref(context, pin, true)

    }

}