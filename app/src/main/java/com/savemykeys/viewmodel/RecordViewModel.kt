package com.savemykeys.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.savemykeys.db.entity.Record
import com.savemykeys.repositories.RecordRepository

class RecordViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG ="RecordViewModel"
    private var repository: RecordRepository =
        RecordRepository(application)

    fun insert(record: Record) {
        Log.d(TAG,"insert")
        repository.insertRecord(record)
    }

    fun update(record: Record) {
        Log.d(TAG,"update")
        repository.updateRecord(record)
    }

    fun delete(record: Record) {
        Log.d(TAG,"delete")
        repository.deleteRecord(record)
    }

    fun getAllRecords(): LiveData<List<Record>> {
        Log.d(TAG,"getAllRecords")
        return repository.getAllRecords()
    }

}