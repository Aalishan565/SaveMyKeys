package com.savemykeys.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.savemykeys.db.entity.Record
import com.savemykeys.repositories.RecordRepository

class RecordViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: RecordRepository =
        RecordRepository(application)

    fun insert(record: Record) {
        repository.insertRecord(record)
    }

    fun update(record: Record) {
        repository.updateRecord(record)
    }

    fun delete(record: Record) {
        repository.deleteRecord(record)
    }

    fun getAllRecords(): LiveData<List<Record>> {
        return repository.getAllRecords()
    }

}