package com.savemykeys.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.ReminderDao
import com.savemykeys.db.entity.Reminder

class ReminderRepository(context: Context) {

    private val TAG = "RecordRepository"
    private var reminderDao: ReminderDao? = null

    init {
        reminderDao = AppDatabase(context).reminderDao()
    }

    fun deleteReminder(reminder: Reminder) {
        Log.d(TAG, "deleteRecord() $reminder")
        reminderDao!!.deleteReminder(reminder)
    }

    fun deleteAllReminder() {
        Log.d(TAG, "deleteAllRecord()")
        reminderDao!!.deleteAllReminder()
    }

    fun insertReminder(reminder: Reminder) {
        Log.d(TAG, "insertRecord() $reminder")
        reminderDao!!.insertReminder(reminder)
    }

    fun updateReminder(reminder: Reminder) {
        Log.d(TAG, "updateRecord $reminder")
        reminderDao!!.updateReminder(reminder)
    }

    fun getAllReminder(): LiveData<List<Reminder>> {
        Log.d(TAG, "getAllRecords() ${reminderDao!!.getReminder()}")
        return reminderDao!!.getReminder()
    }
}