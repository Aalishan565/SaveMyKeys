package com.savemykeys.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.savemykeys.R
import com.savemykeys.db.entity.Record
import com.savemykeys.repositories.RecordRepository

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "RecordViewModel"
    private var repository: RecordRepository = RecordRepository(application)
    private var recordStatus: MutableLiveData<Int> = MutableLiveData()


    fun delete(record: Record) {
        Log.d(TAG, "delete() $record")
        repository.deleteRecord(record)
    }

    fun deleteAllRecord() {
        Log.d(TAG, "deleteAllRecord()")
        repository.deleteAllRecord()
    }

    fun getAllRecords(): LiveData<List<Record>> {
        Log.d(TAG, "getAllRecords()")
        return repository.getAllRecords()
    }

    fun getRecordStatus(): LiveData<Int> {
        return recordStatus
    }

    fun addOrUpdateRecord(
        url: String,
        userName: String,
        password: String,
        note: String,
        insert: Boolean,
        recordId: Long = 0
    ) {
        Log.d(TAG, "addOrUpdateRecord()")
        when {
            url.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() url is blank")
                recordStatus.value = R.string.urlMustNotBeEmpty
                return
            }
            userName.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() userName is blank")
                recordStatus.value = R.string.userNameMustNotBeEmpty
                return
            }
            password.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() password is blank")
                recordStatus.value = R.string.passwordMustNotBeEmpty
                return
            }
            else -> {
                var record: Record
                if (insert) {
                    record = Record(url, userName, password, note)
                    repository.insertRecord(record)
                    recordStatus.value = R.string.recordAddedSuccessfully
                } else {
                    record = Record(url, userName, password, note, recordId)
                    repository.updateRecord(record)
                    recordStatus.value = R.string.recordUpdatedSuccessfully
                }

            }
        }

    }
}