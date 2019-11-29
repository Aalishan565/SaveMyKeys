package com.savemykeys.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.savemykeys.db.entity.Record
import com.savemykeys.utils.Constants


@Dao
interface RecordDao {
    @Insert
    fun insertRecord(record: Record)

    @Query("SELECT * from record_table")
    fun getRecords(): List<Record>

    @Delete
    fun deleteRecord(record: Record)

    @Query("DELETE FROM record_table")
    fun deleteAllRecord()



}