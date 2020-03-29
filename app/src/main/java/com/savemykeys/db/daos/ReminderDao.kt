package com.savemykeys.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.savemykeys.db.entity.Reminder

@Dao
interface ReminderDao {

    @Insert
    fun insertReminder(reminder: Reminder)

    @Update
    fun updateReminder(reminder: Reminder)

    @Query("SELECT * from reminder_table")
    fun getReminder(): LiveData<List<Reminder>>

    @Delete
    fun deleteReminder(reminder: Reminder)

    @Query("DELETE FROM reminder_table")
    fun deleteAllReminder()

}