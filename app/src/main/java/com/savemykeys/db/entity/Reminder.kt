package com.savemykeys.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "reminder_table")
data class Reminder(

    @ColumnInfo(name = "reminder_title") val reminderTitle: String,
    @ColumnInfo(name = "reminder_date") val reminderDate: String,
    @ColumnInfo(name = "reminder_note") val reminderNote: String?,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(
        name = "reminderId"
    ) var reminderId: Long = 0
) : Parcelable