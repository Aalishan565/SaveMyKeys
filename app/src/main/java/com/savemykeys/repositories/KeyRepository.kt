package com.savemykeys.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.savemykeys.application.SaveMyKeysApplication
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.KeyDao
import com.savemykeys.db.entity.Key

class KeyRepository(context: Context) {

    private val TAG = "RecordRepository"
    private var databaseInstance: AppDatabase? = null
    private var keyDao: KeyDao? = null

    init {
        databaseInstance =
            (context.applicationContext as SaveMyKeysApplication).getDatabaseInstance()
        keyDao = databaseInstance?.keyDao()
    }

    fun deleteKey(key: Key) {
        Log.d(TAG, "deleteRecord() $key")
        keyDao!!.deleteKey(key)
    }

    fun deleteAllKeys() {
        Log.d(TAG, "deleteAllRecord()")
        keyDao!!.deleteAllKeys()
    }

    fun insertKey(key: Key) {
        Log.d(TAG, "insertRecord() $key")
        keyDao!!.insertKey(key)
    }

    fun updateKey(key: Key) {
        Log.d(TAG, "updateRecord $key")
        keyDao!!.updateKey(key)
    }

    fun getAllKeys(): LiveData<List<Key>> {
        Log.d(TAG, "getAllRecords() ${keyDao!!.getKeys()}")
        return keyDao!!.getKeys()
    }
}