package com.savemykeys.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.savemykeys.R
import com.savemykeys.db.entity.Memory
import com.savemykeys.repositories.MemoryRepository

class MemoryViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "RecordViewModel"
    private var repository: MemoryRepository = MemoryRepository(application)
    private var recordStatus: MutableLiveData<Int> = MutableLiveData()

    fun deleteMemory(memory: Memory) {
        Log.d(TAG, "delete() $memory")
        repository.deleteMemory(memory)
    }

    fun deleteAllMemory() {
        Log.d(TAG, "deleteAllRecord()")
        repository.deleteAllMemory()
    }

    fun getAllMemory(): LiveData<List<Memory>> {
        Log.d(TAG, "getAllMemory()")
        return repository.getAllMemory()
    }

    fun getMemoryStatus(): LiveData<Int> {
        return recordStatus
    }

    fun addOrUpdateMemory(
        memoryTitle: String,
        memoryDate: String,
        memoryNote: String,
        insert: Boolean,
        memoryId: Long = 0
    ) {
        Log.d(TAG, "addOrUpdateRecord()")
        when {
            memoryTitle.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() url is blank")
                recordStatus.value = R.string.memoryTitleMustNotBeEmpty
                return
            }
            memoryDate.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() userName is blank")
                recordStatus.value = R.string.memoryMustNotBeEmpty
                return
            }
            memoryNote.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() password is blank")
                recordStatus.value = R.string.memoryNoteMustNotBeEmpty
                return
            }
            else -> {
                val memory: Memory
                if (insert) {
                    memory = Memory(memoryTitle, memoryDate, memoryNote, memoryId)
                    repository.insertMemory(memory)
                    recordStatus.value = R.string.recordAddedSuccessfully
                } else {
                    memory = Memory(memoryTitle, memoryDate, memoryNote, memoryId)
                    repository.updateMemory(memory)
                    recordStatus.value = R.string.recordUpdatedSuccessfully
                }

            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel","onCleared()")
    }

}