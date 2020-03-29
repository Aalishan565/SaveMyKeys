package com.savemykeys.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "record_table")
data class Key(

    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "note") val note: String?, @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "recordId") var recordId: Long = 0
) : Parcelable