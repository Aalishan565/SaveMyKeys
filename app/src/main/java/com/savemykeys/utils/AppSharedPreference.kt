package com.savemykeys.utils

import android.content.Context


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
        val sharedpreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getStringPref(context: Context, key: String): String? {
        val sharedpreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedpreferences.getString(key, null)
    }

    fun saveBooleanPref(context: Context, key: String, value: Boolean) {
        val sharedpreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }


    fun clearAllPrefs(context: Context) {
        val sharedpreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.clear()
        editor.commit()
    }

    fun getBooleanPref(context: Context, key: String): Boolean {
        val sharedpreferences =
            context.getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedpreferences.getBoolean(key, false)
    }


}
