package com.savemykeys.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.savemykeys.db.entity.Record
import com.savemykeys.utils.Constants


@Dao
interface RecordDao {
    @Insert
    fun insertRecord(record: Record)
    @Update
    fun updateRecord(record: Record)

    @Query("SELECT * from record_table")
    fun getRecords(): LiveData<List<Record>>

    @Delete
    fun deleteRecord(record: Record)

    @Query("DELETE FROM record_table")
    fun deleteAllRecord()



}