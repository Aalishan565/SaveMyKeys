package com.savemykeys.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "memory_table")
data class Memory(

    @ColumnInfo(name = "memory_title") val memoryTitle: String,
    @ColumnInfo(name = "memory_date") val memoryDate: String,
    @ColumnInfo(name = "memory_note") val memoryNote: String?, @PrimaryKey(autoGenerate = true) @ColumnInfo(
        name = "memoryId"
    ) var memoryId: Long = 0
) : Parcelable