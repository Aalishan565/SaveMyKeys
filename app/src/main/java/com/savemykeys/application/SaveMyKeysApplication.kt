package com.savemykeys.application

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.savemykeys.db.AppDatabase
import com.savemykeys.utils.Constants

class SaveMyKeysApplication : Application() {
    private val TAG = "SaveMyKeysApplication"
    private var databaseInstance: AppDatabase? = null

    override fun onCreate() {
        Log.d(TAG, "onCreate()")
        super.onCreate()
    }

    fun getDatabaseInstance(): AppDatabase {
        if (null == databaseInstance) {
            Log.d(TAG, "getDatabaseInstance()")
            databaseInstance = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, Constants.APP_DB_NAME
            ).allowMainThreadQueries().build()
        }
        return databaseInstance as AppDatabase
    }
}