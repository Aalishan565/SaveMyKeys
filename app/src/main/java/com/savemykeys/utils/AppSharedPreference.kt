package com.savemykeys.utils

import android.content.Context
import androidx.core.content.edit


class AppSharedPreference {
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
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit(true) { putString(key, value) }
    }

    fun getStringPref(context: Context, key: String): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun saveBooleanPref(context: Context, key: String, value: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit(true) { putBoolean(key, value) }

        /* val editor = sharedpreferences.edit()
         editor.putBoolean(key, value)
         editor.commit()*/
    }


    fun clearAllPrefs(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit(true) { clear() }
    }

    fun getBooleanPref(context: Context, key: String): Boolean {
        val sharedpreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedpreferences.getBoolean(key, false)
    }

}
