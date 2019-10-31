package com.savemykeys.views.utils

import android.content.Context
import android.content.SharedPreferences

class AppPreference(context: Context) {

    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        this.sharedPreferences =context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        this.editor = sharedPreferences.edit()
    }

    fun getSharedPref(): SharedPreferences {
        return sharedPreferences
    }

    fun getSharedPrefEditor(): SharedPreferences.Editor {
        return editor
    }
}