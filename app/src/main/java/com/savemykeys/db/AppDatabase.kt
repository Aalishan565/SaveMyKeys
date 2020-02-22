package com.savemykeys.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.savemykeys.db.daos.KeyDao
import com.savemykeys.db.entity.Key
import com.savemykeys.utils.Constants

@Database(entities = [Key::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun keyDao(): KeyDao
    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            Constants.APP_DB_NAME
        ).allowMainThreadQueries().build()

    }
}