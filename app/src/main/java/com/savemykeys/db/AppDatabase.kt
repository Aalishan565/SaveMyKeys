package com.savemykeys.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.db.entity.Record

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}