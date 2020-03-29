package com.savemykeys.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.savemykeys.R
import com.savemykeys.db.entity.Key
import com.savemykeys.repositories.KeyRepository

class KeyViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "RecordViewModel"
    private var repository: KeyRepository = KeyRepository(application)
    private var recordStatus: MutableLiveData<Int> = MutableLiveData()

    fun deleteKey(key: Key) {
        Log.d(TAG, "delete() $key")
        repository.deleteKey(key)
    }

    fun deleteAllKeys() {
        Log.d(TAG, "deleteAllRecord()")
        repository.deleteAllKeys()
    }

    fun getAllKeys(): LiveData<List<Key>> {
        Log.d(TAG, "getAllRecords()")
        return repository.getAllKeys()
    }

    fun getKeyStatus(): LiveData<Int> {
        return recordStatus
    }

    fun addOrUpdateKey(
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
                val key: Key
                if (insert) {
                    key = Key(url, userName, password, note)
                    repository.insertKey(key)
                    recordStatus.value = R.string.recordAddedSuccessfully
                } else {
                    key = Key(url, userName, password, note, recordId)
                    repository.updateKey(key)
                    recordStatus.value = R.string.recordUpdatedSuccessfully
                }

            }
        }

    }
}