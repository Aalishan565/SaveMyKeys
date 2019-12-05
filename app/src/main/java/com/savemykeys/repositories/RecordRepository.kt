package com.savemykeys.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.savemykeys.R
import com.savemykeys.application.SaveMyKeysApplication
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.db.entity.Record

class RecordRepository(context: Context) {

    private val TAG = "RecordRepository"
    private var databaseInstance: AppDatabase? = null
    private var recordDao: RecordDao? = null

    init {
        databaseInstance =
            (context.applicationContext as SaveMyKeysApplication).getDatabaseInstance();
        recordDao = databaseInstance?.recordDao()
    }

    fun deleteRecord(record: Record) {
        Log.d(TAG, "deleteRecord() $record")
        recordDao!!.deleteRecord(record)
    }

    fun deleteAllRecord() {
        Log.d(TAG, "deleteAllRecord()")
        recordDao!!.deleteAllRecord()
    }

    fun insertRecord(record: Record) {
        Log.d(TAG, "insertRecord() $record")
        recordDao!!.insertRecord(record)
    }

    fun updateRecord(record: Record) {
        Log.d(TAG, "updateRecord $record")
        recordDao!!.updateRecord(record)
    }

    fun getAllRecords(): LiveData<List<Record>> {
        Log.d(TAG, "getAllRecords() ${recordDao!!.getRecords()}")
        return recordDao!!.getRecords()
    }
}