package com.savemykeys.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.MemoryDao
import com.savemykeys.db.entity.Memory

class MemoryRepository(context: Context) {

    private val TAG = "RecordRepository"
    private var memoryDao: MemoryDao? = null

    init {
        memoryDao = AppDatabase(context).memoryDao()
    }

    fun deleteMemory(memory: Memory) {
        Log.d(TAG, "deleteRecord() $memory")
        memoryDao!!.deleteMemory(memory)
    }

    fun deleteAllMemory() {
        Log.d(TAG, "deleteAllRecord()")
        memoryDao!!.deleteAllMemory()
    }

    fun insertMemory(memory: Memory) {
        Log.d(TAG, "insertRecord() $memory")
        memoryDao!!.insertMemory(memory)
    }

    fun updateMemory(memory: Memory) {
        Log.d(TAG, "updateRecord $memory")
        memoryDao!!.updateMemory(memory)
    }

    fun getAllMemory(): LiveData<List<Memory>> {
        Log.d(TAG, "getAllRecords() ${memoryDao!!.getMemory()}")
        return memoryDao!!.getMemory()
    }
}