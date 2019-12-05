package com.savemykeys.utils

import android.content.Context
import android.util.Log
import androidx.core.content.edit

class AppSharedPreference {

    private val TAG = "AppSharedPreference"

    companion object {
        private var appSharedPreference: AppSharedPreference? = null
        val instance: AppSharedPreference
            get() {
                if (appSharedPreference == null)
                    appSharedPreference = AppSharedPreference()
                return appSharedPreference!!
            }
    }

    fun saveStringPref(context: Context, key: String, value: String) {
        Log.d(TAG, "saveStringPref() key: $key value: $value")
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit(true) { putString(key, value) }
    }

    fun getStringPref(context: Context, key: String): String? {
        Log.d(TAG, "getStringPref() key: $key")
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun saveBooleanPref(context: Context, key: String, value: Boolean) {
        Log.d(TAG, "saveBooleanPref() key: $key value: $value")
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit(true) { putBoolean(key, value) }
    }

    fun clearAllPrefs(context: Context) {
        Log.d(TAG, "clearAllPrefs()")
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit(true) { clear() }
    }

    fun getBooleanPref(context: Context, key: String): Boolean {
        Log.d(TAG, "getBooleanPref() key: $key")
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

}
