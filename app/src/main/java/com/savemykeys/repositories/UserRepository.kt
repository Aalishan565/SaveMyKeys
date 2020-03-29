package com.savemykeys.repositories

import android.content.Context
import android.util.Log
import com.savemykeys.utils.AppSharedPreference

class UserRepository(private val context: Context) {

    private val TAG = "UserRepository"
    private var appSharedPreference: AppSharedPreference = AppSharedPreference.instance

    fun doLogin(pin: String): Boolean {
        Log.d(TAG, "doLogin() $pin")
        return appSharedPreference.getBooleanPref(context, pin)
    }

    fun doSignUp(pin: String) {
        Log.d(TAG, "doSignUp $pin")
        appSharedPreference.saveBooleanPref(context, pin, true)
        appSharedPreference.clearAllPrefs(context)
        appSharedPreference.saveBooleanPref(context, pin, true)

    }

}