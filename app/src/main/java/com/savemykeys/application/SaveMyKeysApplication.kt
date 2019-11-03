package com.savemykeys.application

import android.app.Application
import androidx.room.Room
import com.savemykeys.db.AppDatabase
import com.savemykeys.utils.Constants

class SaveMyKeysApplication : Application() {
    private var databaseInstance: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
    }

    fun getDatabaseInstance(): AppDatabase {
        if (null == databaseInstance) {
            databaseInstance = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, Constants.APP_DB_NAME
            ).allowMainThreadQueries().build();
        }
        return databaseInstance as AppDatabase
    }
}