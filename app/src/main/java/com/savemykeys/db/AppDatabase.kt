package com.savemykeys.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.savemykeys.db.daos.KeyDao
import com.savemykeys.db.entity.Key

@Database(entities = [Key::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun keyDao(): KeyDao
}