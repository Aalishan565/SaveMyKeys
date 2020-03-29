package com.savemykeys.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.savemykeys.R
import com.savemykeys.db.entity.Reminder
import com.savemykeys.repositories.ReminderRepository

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "RecordViewModel"
    private var repository: ReminderRepository = ReminderRepository(application)
    private var recordStatus: MutableLiveData<Int> = MutableLiveData()

    fun deleteReminder(reminder: Reminder) {
        Log.d(TAG, "delete() $reminder")
        repository.deleteReminder(reminder)
    }

    fun deleteAllReminder() {
        Log.d(TAG, "deleteAllRecord()")
        repository.deleteAllReminder()
    }

    fun getAllReminder(): LiveData<List<Reminder>> {
        Log.d(TAG, "getAllRecords()")
        return repository.getAllReminder()
    }

    fun getReminderStatus(): LiveData<Int> {
        return recordStatus
    }

    fun addOrUpdateReminder(
        reminderTitle: String,
        reminderDate: String,
        reminderNote: String,
        insert: Boolean,
        reminderId: Long = 0
    ) {
        Log.d(TAG, "addOrUpdateRecord()")
        when {
            reminderTitle.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() url is blank")
                recordStatus.value = R.string.memoryTitleMustNotBeEmpty
                return
            }
            reminderDate.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() userName is blank")
                recordStatus.value = R.string.memoryMustNotBeEmpty
                return
            }
            reminderNote.isBlank() -> {
                Log.d(TAG, "addOrUpdateRecord() password is blank")
                recordStatus.value = R.string.memoryNoteMustNotBeEmpty
                return
            }
            else -> {
                val reminder: Reminder
                if (insert) {
                    reminder = Reminder(reminderTitle, reminderDate, reminderNote, reminderId)
                    repository.insertReminder(reminder)
                    recordStatus.value = R.string.recordAddedSuccessfully
                } else {
                    reminder = Reminder(reminderTitle, reminderDate, reminderNote, reminderId)
                    repository.updateReminder(reminder)
                    recordStatus.value = R.string.recordUpdatedSuccessfully
                }

            }
        }

    }
}