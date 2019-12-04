package com.savemykeys.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.savemykeys.R
import com.savemykeys.application.SaveMyKeysApplication
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.db.entity.Record

class RecordRepository(context: Context) {
    private var databaseInstance: AppDatabase? = null
    private var recordDao: RecordDao? = null

    init {
        databaseInstance =
            (context.applicationContext as SaveMyKeysApplication).getDatabaseInstance();
        recordDao = databaseInstance?.recordDao()
    }

    fun deleteRecord(record: Record) {
        recordDao!!.deleteRecord(record)
    }

    fun insertRecord(record: Record) {
        recordDao!!.insertRecord(record)
    }

    fun updateRecord(record: Record) {
        recordDao!!.updateRecord(record)
    }

    fun getAllRecords(): LiveData<List<Record>> {
        return recordDao!!.getRecords()
    }

}