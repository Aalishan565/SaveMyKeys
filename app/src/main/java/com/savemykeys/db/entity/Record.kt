package com.savemykeys.db.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "record_table")
data class Record(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "recordId") var recordId: Long?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "note") val note: String?
) : Parcelable